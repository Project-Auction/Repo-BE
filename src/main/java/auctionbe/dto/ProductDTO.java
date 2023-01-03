package auctionbe.dto;

import auctionbe.models.Category;
import com.fasterxml.jackson.annotation.JsonBackReference;

public class ProductDTO {
    private String nameProduct;
    private double initialPrice;
    private double finalPrice;
    private double incrementPrice;
    private String productDescription;
    private String startDate;

    private String endDate;
    private boolean biddingStatus;

    private boolean approvalStatus;

    private String accountId;
    @JsonBackReference("productReference")
    private Category category;

    public ProductDTO(String nameProduct, double initialPrice, double finalPrice, double incrementPrice, String productDescription, String startDate, String endDate,
                      boolean biddingStatus, boolean approvalStatus ,String accountId , Category category) {
        this.nameProduct = nameProduct;
        this.initialPrice = initialPrice;
        this.finalPrice = finalPrice;
        this.incrementPrice = incrementPrice;
        this.productDescription = productDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.biddingStatus = biddingStatus;
        this.approvalStatus = approvalStatus;
        this.accountId = accountId;
        this.category = category;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public boolean isBiddingStatus() {
        return biddingStatus;
    }

    public boolean isApprovalStatus() {
        return approvalStatus;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public double getIncrementPrice() {
        return incrementPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public boolean getBiddingStatus() {
        return biddingStatus;
    }

    public boolean getApprovalStatus() {
        return approvalStatus;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public void setInitialPrice(double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public void setIncrementPrice(double incrementPrice) {
        this.incrementPrice = incrementPrice;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setBiddingStatus(boolean biddingStatus) {
        this.biddingStatus = biddingStatus;
    }

    public void setApprovalStatus(boolean approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
