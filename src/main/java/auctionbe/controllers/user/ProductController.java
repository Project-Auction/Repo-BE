package auctionbe.controllers.user;


import auctionbe.models.Product;
import auctionbe.models.User;
import auctionbe.service.AccountService;
import auctionbe.service.ProductService;
import auctionbe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/auth")
//@RequestMapping(value = "/api/user/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    /* Post product */
    @PostMapping(value = "/post-product/{userId}", consumes = {"application/json"} , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> handlePostProduct(@RequestBody Product product, @PathVariable String userId) {
        try {
            User user = userService.findById(userId);

            product.setUser(user);

            productService.save(product);

            return new ResponseEntity<>("Created successfully!", HttpStatus.OK);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating product: " + ex.getMessage());
        }
    }
}
