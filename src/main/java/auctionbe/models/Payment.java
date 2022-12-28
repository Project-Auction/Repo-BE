package auctionbe.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Data
public class Payment {
    @Id
    @Column(name = "payment_id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String paymentId;

    @NotEmpty
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[a-z,A-Z ,ỳọáầảấờễàạằệếýộậốũứĩõúữịỗìềểẩớặòùồợãụủíỹắẫựỉỏừỷởóéửỵẳẹèẽổẵẻỡơôưăêâđ]{1,}$")
    private String fullNameReceiver;

    @NotEmpty
    @Size(min = 3, max = 200)
    private String addressReceiver;

    @NotEmpty
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email is invalid")
    private String emailReceiver;

    @NotEmpty
    @Pattern(regexp = "^[0-9 -]{1,}$")
    private String phoneReceiver;

    private double feeService;

    private String descriptionReceiver;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "payment")
    @JsonBackReference
    private Invoice invoice;

    @ManyToOne(targetEntity = PaymentMethod.class)
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;

    @ManyToOne(targetEntity = Transport.class)
    @JoinColumn(name = "transport_id", nullable = false)
    private Transport transport;

    public Payment() {}

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getFullNameReceiver() {
        return fullNameReceiver;
    }

    public void setFullNameReceiver(String fullNameReceiver) {
        this.fullNameReceiver = fullNameReceiver;
    }

    public String getAddressReceiver() {
        return addressReceiver;
    }

    public void setAddressReceiver(String addressReceiver) {
        this.addressReceiver = addressReceiver;
    }

    public String getEmailReceiver() {
        return emailReceiver;
    }

    public void setEmailReceiver(String emailReceiver) {
        this.emailReceiver = emailReceiver;
    }

    public String getPhoneReceiver() {
        return phoneReceiver;
    }

    public void setPhoneReceiver(String phoneReceiver) {
        this.phoneReceiver = phoneReceiver;
    }

    public double getFeeService() {
        return feeService;
    }

    public void setFeeService(double feeService) {
        this.feeService = feeService;
    }

    public String getDescriptionReceiver() {
        return descriptionReceiver;
    }

    public void setDescriptionReceiver(String descriptionReceiver) {
        this.descriptionReceiver = descriptionReceiver;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }
}
