package auctionbe.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class PaymentMethod {
    @Id
    @Column(name = "payment_method_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namePaymentMethod;

    @OneToMany(mappedBy = "paymentMethod")
    @JsonBackReference
    private List<Payment> payments;

    public PaymentMethod() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamePaymentMethod() {
        return namePaymentMethod;
    }

    public void setNamePaymentMethod(String namePaymentMethod) {
        this.namePaymentMethod = namePaymentMethod;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}

