package auctionbe.repository;

import auctionbe.models.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RankRepository extends JpaRepository<Rank , Long> {
    @Query(value = "select * from `ranks` where `ranks`.rank_name = ?1" ,  nativeQuery=true)
    Rank findByName(String name);
}
