package auctionbe.controllers.user;

import auctionbe.models.ApiError;
import auctionbe.models.Category;
import auctionbe.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/request-common")
@CrossOrigin(origins = "*")
public class CommonController {
    @Autowired
    private CategoryService categoryService;

    ApiError apiError = new ApiError();

    /* Get categories */
    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCategories() {
        try {
            List<Category> categories = categoryService.findAll();

            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception ex) {
            apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
            return new ResponseEntity<>(apiError, apiError.getHttpStatus());
        }
    }
}
