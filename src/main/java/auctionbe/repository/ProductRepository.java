package auctionbe.repository;

import auctionbe.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product , String> {
    @Query("select p from Product p where p.nameProduct = ?1")
    Product findProductByName(String productName);

    /* Find product with approval*/
    @Query("select p from Product p where p.approvalStatus = ?1")
    List<Product> findProductByApprovalStatus(boolean status);

    /* Find product with user id */
    @Query(value = "select * from products inner join users on users.user_id = products.user_id where products.user_id = ?1" , nativeQuery = true)
    List<Product> findProductByUserId(String userId);
}
