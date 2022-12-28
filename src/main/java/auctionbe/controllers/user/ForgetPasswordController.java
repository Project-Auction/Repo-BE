package auctionbe.controllers.user;

import auctionbe.models.Account;
import auctionbe.models.ApiError;
import auctionbe.service.AccountService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/auth")
public class ForgetPasswordController {
    @Autowired
    private JavaMailSender javaMailSender;

    private ApiError apiError = new ApiError();

    @Autowired
    private AccountService accountService;

    /* Send to email*/
    private ResponseEntity<?> sendEmail(String email, String linkResetPassword) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, "UTF-8");

        /* Set headers */
        message.addHeader("Content-type", "text/HTML; charset=UTF-8");
        message.addHeader("format", "flowed");
        message.addHeader("Content-Transfer-Encoding", "8bit");

        /* Create form */
        messageHelper.setFrom("gridshopvn@gmail.com", "GridShop - Auction System");
        messageHelper.setTo(email);

        /* Get user by email */
        Account account = null;

        try {
            account = accountService.findAccountByEmail(email);
        } catch (Exception ex) {
            apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong! Please try again", ex.getMessage());
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }

        if (account == null) {
            apiError = new ApiError(HttpStatus.NOT_FOUND, "Email doesn't existing!");
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }

        String subject = "Here's the link to reset your password";
        String content = "<body style=\"padding: 0;margin: 0;\">" +
                "    <div style=\"width: 600px;" +
                "    display: flex;" +
                "    justify-content: center;" +
                "    margin: auto;" +
                "    flex-direction: column;\">" +
                "        <div style=\"padding: 20px;border: 1px solid #dadada;\">" +
                "            <p style=\"font-size: 16px;color: #000;\">Greetings, \"" + account.getUser().getName() + "\"</p>" +
                "            <p style=\"font-size: 16px;color: #000;\">We received a request to reset your password.<br>Click the button" +
                "                below to setup a new password</p>" +
                "" +
                "            <a href=\"" + linkResetPassword + "\" style=\"padding: 8px;display: inline-block;cursor: pointer; border-radius: 3px;" +
                "            font-size: 15px;text-decoration: none;background-color: #0167f3;color: #fff;font-weight: 500;\">Change" +
                "                my password</a>" +
                "" +
                "            <p>This link will expire after 10 minutes. If you didn't request a password reset , ignore this email and continue using your current password." +
                "            <br>" +
                "            Thank you for using our service.<br>" +
                "            <br>If you have any question, Please contact us immediately at <a href=\"mailto:gridshopvn@gmail.com@gmail.com\">gridshopvn@gmail.com</a>" +
                "            </p>" +
                "            <p>Thanks you.</p>" +
                "            <p>Grid Shop Team.</p>" +
                "        </div>" +
                "    </div>" +
                "</body>";

        messageHelper.setSubject(subject);
        messageHelper.setText(content, true);
        javaMailSender.send(message);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /* Process send to mail */
    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
    public ResponseEntity<?> processForgotPassword(@RequestParam String email, HttpServletResponse response) {
        String message = "";
        String emailConfirm = email.trim();

        /* Create token */
        String token = RandomString.make(45);

        try {
            accountService.setPasswordByToken(token, emailConfirm);

            String linkResetPassword = "http://localhost:3000/reset-password/" + token;
            sendEmail(emailConfirm, linkResetPassword);
            message = "We have sent a reset password link to your email. Please check.";
        } catch (UsernameNotFoundException ex) {
            apiError = new ApiError(HttpStatus.NOT_FOUND, "Error: We cannot find your account registered:" + email);
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        } catch (Exception ex) {
            ex.printStackTrace();
            apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong! Please try again", ex.getMessage());
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /* Handle reset password */
    @RequestMapping(value = "/reset-password", method = RequestMethod.PATCH)
    public ResponseEntity<?> handleResetPassword(@RequestParam(value = "token") String resetToken, @RequestParam(value = "password") String password) {
        String token = resetToken;
        String newPassword = password;

        /* Get account by token */
        Account account = null;

        try {
            account = accountService.findAccountByToken(token);

            if (account == null) {
                throw new AccountNotFoundException("Token doesn't existing!");
            }

            // Check password
            accountService.checkPasswordUsed(token, newPassword);

            accountService.updatePassword(account, newPassword);
        } catch (AccountNotFoundException ex) {
            apiError = new ApiError(HttpStatus.REQUEST_TIMEOUT, ex.getMessage());
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        } catch (IllegalArgumentException ex) {
            apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        } catch (Exception ex) {
            ex.printStackTrace();
            apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong! Please try again");
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /* Get the password of the previously used user by resetting the pwd token*/
//    @RequestMapping(value = "/reset-password/check-password", method = RequestMethod.GET)
//    public ResponseEntity<?> checkPasswordUsedBefore(@RequestParam(value = "token") String token, @RequestParam(value = "password") String newPassword) {
//        Account account = accountService.checkPasswordUsed(token, newPassword);
//
//        if (account == null) {
//            apiError = new ApiError(HttpStatus.BAD_REQUEST, "You used this password recently. Please choose a different one.");
//            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
//        }
//
//        return new ResponseEntity<>(account, HttpStatus.OK);
//    }

    /* Check token existing */
    @RequestMapping(value = "/check-token-password/{token}", method = RequestMethod.GET)
    public ResponseEntity<?> checkTokenPassword(@PathVariable String token) {
        Account account = null;
        try {
            account = accountService.findAccountByToken(token);
        } catch (Exception ex) {
            ex.printStackTrace();
            apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong! Please try again");
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }

        if (account == null) {
            apiError = new ApiError(HttpStatus.REQUEST_TIMEOUT, "Token expired");
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
