package auctionbe.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String productId;

    private String codeProduct;

    private String nameProduct;

    private Double initialPrice;

    private Double finalPrice;

    private Double incrementPrice;

    private String productDescription;

    private String startDate;

    private String endDate;

    private String remainingTime;

    private LocalDateTime createdDay;

    private Boolean flagDelete;

    private Boolean biddingStatus;

    private Boolean approvalStatus;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category_id" , nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product")
    @JsonBackReference
    private List<ProductTransaction> productTransactions;

    @OneToMany(mappedBy = "product")
    private List<ImageProduct> imageProduct;

    @OneToOne(mappedBy = "product")
    @JsonBackReference
    private Invoice invoice;

    public Product() {}
}
