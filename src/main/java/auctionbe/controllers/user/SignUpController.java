package auctionbe.controllers.user;

import auctionbe.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class SignUpController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/sign-up" , method = RequestMethod.POST)
    public ResponseEntity<?> handleSignUp() {
        return ResponseEntity.ok("hi");
    }
}
