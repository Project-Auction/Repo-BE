package auctionbe.service;

import auctionbe.models.Product;
import auctionbe.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    /* Create product */
    public void save(Product product) {
        productRepository.save(product);
    }

    /* Find by name */
    public List<Product> findByName(String name) {
        List<Product> products = productRepository.findByNameContaining(name);
        if (products.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product doesn't existing");
        }
        return products;
    }

    /* Find by category */
    public List<Product> findByCategory(Long id) {
        List<Product> products = productRepository.findByCategory(id);

        if (products.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product doesn't existing");
        }
        return products;
    }

    /* Find by name and category */
    public List<Product> findByNameAndCategory(String name, Long id) {
        List<Product> products = productRepository.findByNameAndCategory(name, id);

        if (products.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product doesn't existing");
        }
        return products;
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
    public List<Product> findProductByUserId(String userId) {
        List<Product> products = productRepository.findProductByUserId(userId);
        if (products.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product doesn't existing");
        }
        return products;
    }

    /* Find all */
    public List<Product> findAll() {
        List<Product> products = productRepository.findAll();

        if (products.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is empty!");
        }
        return products;
    }
}
