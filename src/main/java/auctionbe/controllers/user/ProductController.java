package auctionbe.controllers.user;


import auctionbe.models.ApiError;
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
import org.springframework.web.server.ResponseStatusException;

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

    ApiError apiError;

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
        } catch (ResponseStatusException ex) {
            apiError = new ApiError(ex.getStatus(), ex.getReason());
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        } catch (Exception ex) {
            apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }
    }

    /* Find products posted with name and category */
    @GetMapping("/product/search")
    public ResponseEntity<? extends Object> searchProducts(@RequestParam(name = "q") String productName, @RequestParam(name = "categoryId", required = false) Long categoryId) {
        try {
            List<Product> products;
            if (!productName.isEmpty() && categoryId != 0) {
                products = productService.findByNameAndCategory(productName, categoryId);
            } else if (!productName.isEmpty()) {
                products = productService.findByName(productName);
            } else if (categoryId != 0) {
                products = productService.findByCategory(categoryId);
            } else {
                products = productService.findAll();
            }

            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            apiError = new ApiError(ex.getStatus(), ex.getReason());
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        } catch (Exception ex) {
            apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }
    }
}
