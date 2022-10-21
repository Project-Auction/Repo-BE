package auctionbe.dto;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserRegisterDTO {
    @Column(name = "user_name")
    @NotEmpty(message = "Username not be empty")
    public String fullName;

    @NotEmpty(message = "Birthday is not empty")
//    @Pattern(regexp = "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$" , message = "Birthday is invalid")
    public String dateOfBirth;

    @NotEmpty(message = "Email cannot be empty")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email is invalid")
    public String email;

    @NotEmpty(message = "Phone number cannot be empty")
    public String phoneNumber;

    @NotEmpty(message = "Id card cannot be empty")
    public String identityNumber;

    @Length(min = 6 , max = 30 , message = "Length password must be between 9 and 30")
    public String password;

    public UserRegisterDTO() {}

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
