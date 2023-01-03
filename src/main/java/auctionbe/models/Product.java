package auctionbe.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
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
    private String nameProduct;
    private double initialPrice;
    private double finalPrice;
    private double incrementPrice;
    private String productDescription;
    private Date startDate;

    private Date endDate;

    private String remainingTime;

    private LocalDateTime createdDay;

    private boolean biddingStatus;

    private boolean approvalStatus;
    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product")
    @JsonBackReference
    private List<ProductTransaction> productTransactions;

    @OneToOne(mappedBy = "product")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public Product() {
    }

    public Product(String nameProduct, double initialPrice, double finalPrice, double incrementPrice, String productDescription, Date startDate, Date endDate, boolean biddingStatus, boolean approvalStatus, User user, Category category) {
        this.nameProduct = nameProduct;
        this.initialPrice = initialPrice;
        this.finalPrice = finalPrice;
        this.incrementPrice = incrementPrice;
        this.productDescription = productDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.biddingStatus = biddingStatus;
        this.approvalStatus = approvalStatus;
        this.category = category;
        this.user = user;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public Double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(Double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Double getIncrementPrice() {
        return incrementPrice;
    }

    public void setIncrementPrice(Double incrementPrice) {
        this.incrementPrice = incrementPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(String remainingTime) {
        this.remainingTime = remainingTime;
    }

    public LocalDateTime getCreatedDay() {
        return createdDay;
    }

    public void setCreatedDay(LocalDateTime createdDay) {
        this.createdDay = createdDay;
    }

    public Boolean getBiddingStatus() {
        return biddingStatus;
    }

    public void setBiddingStatus(Boolean biddingStatus) {
        this.biddingStatus = biddingStatus;
    }

    public Boolean getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Boolean approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<ProductTransaction> getProductTransactions() {
        return productTransactions;
    }

    public void setProductTransactions(List<ProductTransaction> productTransactions) {
        this.productTransactions = productTransactions;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
