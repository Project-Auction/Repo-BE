package auctionbe.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Rank {
    @Id
    @Column(name = "rank_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rankId;

    private String rankName;

    @OneToMany(mappedBy = "rank")
    @JsonBackReference
    private List<User> users;

    public Rank() {}

    public Long getRankId() {
        return rankId;
    }

    public void setRankId(Long rankId) {
        this.rankId = rankId;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
