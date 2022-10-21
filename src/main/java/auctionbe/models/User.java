package auctionbe.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(generator= "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "user_name")
    @NotEmpty(message = "Username not be empty")
    @Pattern(regexp = "^[A-Za-zỳọáầảấờễàạằệếýộậốũứĩõúữịỗìềểẩớặòùồợãụủíỹắẫựỉỏừỷởóéửỵẳẹèẽổẵẻỡơôưăêâđ' ]+$", message = "Username is invalid")
    private String name;

    @Column(name = "date_of_birth")
    @NotEmpty(message = "Ngày sinh không được để trống")
    public String dateOfBirth;

    @Column(name = "address_user")
    @NotEmpty(message = "Address cannot be empty")
    private String address;

    @Column(name = "phone_member")
    @NotEmpty(message = "Phone number cannot be empty")
    @Pattern(regexp = "^(84|0[3|5|7|8|9])+([0-9]{9})$", message = "Phone number is invalid")
    private String phoneMember;

    @Column(name = "id_card_member")
    @NotEmpty(message = "Id card cannot be empty")
    private String idCardMember;

    @Column(name = "paypal_member")
    private String paypalMember;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;

    @ManyToOne(targetEntity = Rank.class)
    @JoinColumn(name = "rank_id")
    private Rank rank;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Payment> payments;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<ProductTransaction> productTransactions;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role")
    )
    private Set<Role> roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "province_id", referencedColumnName = "province_id")
    private Province province;

    public User() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneMember() {
        return phoneMember;
    }

    public void setPhoneMember(String phoneMember) {
        this.phoneMember = phoneMember;
    }

    public String getIdCardMember() {
        return idCardMember;
    }

    public void setIdCardMember(String idCardMember) {
        this.idCardMember = idCardMember;
    }

    public String getPaypalMember() {
        return paypalMember;
    }

    public void setPaypalMember(String paypalMember) {
        this.paypalMember = paypalMember;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<ProductTransaction> getProductTransactions() {
        return productTransactions;
    }

    public void setProductTransactions(List<ProductTransaction> productTransactions) {
        this.productTransactions = productTransactions;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
