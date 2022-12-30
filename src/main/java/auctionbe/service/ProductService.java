package auctionbe.service;

import auctionbe.models.Product;
import auctionbe.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    /* Find all */
    public List<Product> findAll() throws Exception {
        List<Product> products = productRepository.findAll();

        if (products.size() == 0) {
            throw new Exception("Product is empty!");
        }
        return products;
    }
}
