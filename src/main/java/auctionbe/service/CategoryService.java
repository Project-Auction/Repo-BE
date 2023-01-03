package auctionbe.service;

import auctionbe.models.Category;
import auctionbe.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() throws Exception {
        List<Category> categories = categoryRepository.findAll();

        if(categories.size() == 0) {
            throw new Exception("Categories is empty");
        }

        return categories;
    }

    public Category findById(Long id)  throws Exception{
        Optional<Category> category = categoryRepository.findById(id);
        if(!category.isPresent()) {
            throw new Exception("Category doesn't existing");
        }
        return category.get();
    }
}
