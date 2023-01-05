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

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/user")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    /* Post product */
    @PostMapping(value = "/product/post-product/{userId}", consumes = {"application/json"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> handlePostProduct(@RequestBody Product product, @PathVariable String userId) {
        try {
            User user = userService.findById(userId);

            product.setUser(user);
            product.setCreatedDay(LocalDateTime.now());

            productService.save(product);

            return new ResponseEntity<>("Created successfully!", HttpStatus.OK);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating product: " + ex.getMessage());
        }
    }

    /* Product posted */
    @GetMapping(value = "/product/products-posted/{userId}")
    public ResponseEntity<?> handleGetProductsPosted(@PathVariable String userId, HttpServletResponse res) {
        try {
            List<Product> products = productService.findProductByUserId(userId);

            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getMessage());
        }
    }
}
