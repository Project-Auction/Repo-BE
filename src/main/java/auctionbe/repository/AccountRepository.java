package auctionbe.repository;

import auctionbe.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account , String> {
    @Query(value = "select a from Account a where a.email = ?1")
    Account findAccountByEmail(String email);

    @Query(value = "select a from Account a where a.token = ?1")
    Account findAccountByToken(String token);
}
