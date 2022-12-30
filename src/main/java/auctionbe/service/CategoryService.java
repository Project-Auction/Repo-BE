package auctionbe.service;

import auctionbe.models.Category;
import auctionbe.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
}
