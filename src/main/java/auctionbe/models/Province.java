package auctionbe.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@Table(name = "provinces")
public class Province {
    @Id
    @Column(name = "province_id")
    @GeneratedValue(generator= "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String provinceId;

    @Column(name = "city_name")
    @NotEmpty(message = "City not be empty")
    public String city;

    @Column(name = "district_name")
    @NotEmpty(message = "District not be empty")
    public String district;

    @Column(name = "ward_name")
    @NotEmpty(message = "Ward not be empty")
    public String ward;

    @OneToOne(mappedBy = "province")
    @JsonBackReference
    private User user;

    public Province() {}

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
