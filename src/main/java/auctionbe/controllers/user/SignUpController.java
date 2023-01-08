package auctionbe.controllers.user;

import auctionbe.dto.UserRegisterDTO;
import auctionbe.models.*;
import auctionbe.service.*;
import auctionbe.utils.EncryptPasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/auth")
public class SignUpController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private RankService rankService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private RoleService roleService;

    ApiError apiError = new ApiError();

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public ResponseEntity<?> handleSignUp(@Valid @RequestBody UserRegisterDTO reqBody, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.add(error.getField() + ": " + error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Account userExisting = null;
        Account accountSaved = new Account();
        User userSaved = new User();
        /* Get rank */
        Rank rank = rankService.findByName("BROZER");

        try {
            userExisting = accountService.findAccountByEmail(reqBody.getEmail());
        } catch (Exception ex) {
            apiError = new ApiError(HttpStatus.BAD_REQUEST, "Something went wrong. Please try again!");
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }

        if (userExisting != null) {
            apiError = new ApiError(HttpStatus.BAD_REQUEST, "User already existing!");
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }

        try {
            String hashPassword = EncryptPasswordUtils.EncryptPasswordUtils(reqBody.getPassword());
            reqBody.setPassword(hashPassword);

        } catch (Exception ex) {
            apiError = new ApiError(HttpStatus.BAD_REQUEST, "Something went wrong. Please try again!");
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }

        try {
            userSaved.setRank(rank);
            userSaved.setPhoneNumber(reqBody.getPhoneNumber());
            userSaved.setIdentityNumber(reqBody.getIdentityNumber());
            userSaved.setName(reqBody.getFullName());
            userSaved.setDateOfBirth(reqBody.getDateOfBirth());

            /* Set account */
            accountSaved.setEmail(reqBody.getEmail());
            accountSaved.setPassword(reqBody.getPassword());
            accountSaved.setIsBlocked(false);
            accountSaved.setLastLogin(String.valueOf(LocalDate.now()));
            userSaved.setAccount(accountSaved);

            /* Save province */
            Province province = new Province();
            if (reqBody.getCity() != null || reqBody.getWard() != null || reqBody.getDistrict() != null) {
                province.setCity(reqBody.getCity());
                province.setDistrict(reqBody.getDistrict());
                province.setWard(reqBody.getWard());
                Province provinceSaved = provinceService.save(province);
                userSaved.setProvince(provinceSaved);
            }

            /* Initial authorization */
            Set<Role> roles = new HashSet<>();
            Role role = roleService.findByNameRole("ROLE_MEMBER");
            roles.add(role);
            accountSaved.setRoles(roles);

            accountService.save(accountSaved);
            userService.save(userSaved);
        } catch (Exception ex) {
            apiError = new ApiError(HttpStatus.BAD_REQUEST, "Credential invalid, Please check your input again");
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
