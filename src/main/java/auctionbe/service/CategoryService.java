package auctionbe.service;

import auctionbe.models.Category;
import auctionbe.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        List<Category> categories = categoryRepository.findAll();

        if (categories.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categories is empty");
        }

        return categories;
    }

    public Category findById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category doesn't existing");
        }
        return category.get();
    }
}
