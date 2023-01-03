package auctionbe.controllers;

import auctionbe.jwt.JwtTokenUtil;
import auctionbe.models.Account;
import auctionbe.models.ApiError;
import auctionbe.payload.JwtRequest;
import auctionbe.payload.JwtResponse;
import auctionbe.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/authenticate")
@CrossOrigin(origins = "*")
public class SecurityController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    ApiError apiError = new ApiError();

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> handleLogin(@Valid @RequestBody JwtRequest authenticateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            apiError = new ApiError(HttpStatus.FORBIDDEN, "Missing some data, Please check your inputs!");
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }
        try {
            authenticate(authenticateRequest.getEmail(), authenticateRequest.getPassword());
        } catch (Exception ex) {
            apiError = new ApiError(HttpStatus.UNAUTHORIZED, "Email or password is invalid, Please check your inputs!");
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }

        /* Load UserDetail with username and password from DB */
        final String token;
        Account accountLoggedIn;

        try {
            final UserDetails userDetails = accountService.loadUserByUsername(authenticateRequest.getEmail());

            token = jwtTokenUtil.generateToken(userDetails);
            accountLoggedIn = accountService.findAccountByEmail(authenticateRequest.getEmail());
        } catch (Exception ex) {
            apiError = new ApiError(HttpStatus.UNAUTHORIZED, "Login Failed!", ex.getMessage());
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }
        return ResponseEntity.ok(new JwtResponse(token , accountLoggedIn.getUser().getId() , accountLoggedIn.getEmail(), accountLoggedIn.getUser().getName() , accountLoggedIn.getRoles()));
    }

    public void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
