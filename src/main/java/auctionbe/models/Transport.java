package auctionbe.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transport_id")
    private Long id;

    private double transportFee;

    private String transportName;

    @OneToMany(mappedBy = "transport")
    @JsonBackReference(value = "transport_payment")
    private List<Payment> payments;

    public Transport() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTransportFee() {
        return transportFee;
    }

    public void setTransportFee(double transportFee) {
        this.transportFee = transportFee;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
