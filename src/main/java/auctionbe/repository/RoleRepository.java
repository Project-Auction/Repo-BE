package auctionbe.repository;

import auctionbe.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role , Long> {
    @Query(value = "select * from role where role.name = ?1" ,  nativeQuery=true)
    Role findByNameRole(String name);
}
