package auctionbe.repository;

import auctionbe.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product , String> {
    @Query("select p from Product p where p.nameProduct = ?1")
    Product findProductByName(String productName);
}
