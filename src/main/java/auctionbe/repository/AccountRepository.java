package auctionbe.repository;

import auctionbe.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account , String> {
    @Query(value = "select * from accounts where accounts.email = ?1" , nativeQuery=true)
    Account findAccountByEmail(String email);
}
