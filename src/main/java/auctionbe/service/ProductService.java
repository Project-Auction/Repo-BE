package auctionbe.service;

import auctionbe.models.Product;
import auctionbe.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    /* Create product */
    public void save(Product product) throws Exception {
        productRepository.save(product);
    }

    /* Find by name */
    public Product findByName(String name) throws Exception {
        Product product = productRepository.findProductByName(name);
        if(product == null) {
            throw new Exception("Product existing");
        }
        return product;
    }

    /* Update remaining time */
    @Scheduled(fixedRate = 1000)
    public void updateRemainingTime() throws Exception {
        List<Product> products = findAll();

        for (Product product : products) {
            long startTime = product.getStartDate().getTime();
            long endTime = product.getEndDate().getTime();
            long remainingTime = startTime - endTime;

            product.setRemainingTime(String.valueOf(remainingTime));
            productRepository.save(product);
        }
    }

    /* Find product with user id */
    public List<Product> findProductByUserId(String userId) throws Exception {
        List<Product> products = productRepository.findProductByUserId(userId);
        if(products.size() == 0) {
            throw new Exception("Product is empty");
        }
        return products;
    }

    /* Find all */
    public List<Product> findAll() throws Exception {
        List<Product> products = productRepository.findAll();

        if (products.size() == 0) {
            throw new Exception("Product is empty!");
        }
        return products;
    }
}
