package auctionbe.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name")
    private String name;

    @ManyToMany(mappedBy = "roles" ,fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    private Set<Account> accounts;

    public Role() {}
}
