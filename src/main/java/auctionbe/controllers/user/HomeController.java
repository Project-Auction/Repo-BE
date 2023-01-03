package auctionbe.controllers.user;

import auctionbe.models.*;
import auctionbe.service.CategoryService;
import auctionbe.service.ProductService;
import auctionbe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/home")
@CrossOrigin(origins = "*")
public class HomeController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    private ApiError apiError = new ApiError();

    /* Get products */
    @GetMapping(value = "/products")
    public ResponseEntity<?> getAllProducts() {
        try {
            List<Product> products = productService.findAll();

            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception ex) {
            apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }
    }

    /* Get categories */
    @GetMapping(value = "/categories")
    public ResponseEntity<?> getAllCategories() {
        try {
            List<Category> categories = categoryService.findAll();

            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception ex) {
            apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }
    }

    /* Get categories by id */
    @GetMapping(value = "/categories/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long categoryId) {
        try {
            Category category = categoryService.findById(categoryId);

            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (Exception ex) {
            apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }
    }

    /* Find user by id */
    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<?> handleFindUserById(@PathVariable String userId) {
        try {
            User user = userService.findById(userId);

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception ex) {
            apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }
    }
}
