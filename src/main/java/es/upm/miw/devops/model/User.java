package es.upm.miw.devops.model;

import es.upm.miw.devops.code.Fraction;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@SuppressWarnings("unused")
public class User {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "family_name", nullable = false)
    private String familyName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "email")
    private String email;

    @Column(name = "identity")
    private String identity;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;

    @Column(name = "postal_code")
    private String postalCode;

    @Transient
    private List<Fraction> fractions;

    public User() {
        this.fractions = new ArrayList<>();
    }

    public User(String id, String name, String familyName, List<Fraction> fractions) {
        this.id = id;
        this.name = name;
        this.firstName = name;
        this.familyName = familyName;
        this.fractions = fractions != null ? fractions : new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name != null ? name : getFirstName();
    }

    public void setName(String name) {
        this.name = name;
        this.firstName = name;
    }

    public String getFirstName() {
        return firstName != null ? firstName : name;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        this.name = firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public List<Fraction> getFractions() {
        return fractions;
    }

    public void setFractions(List<Fraction> fractions) {
        this.fractions = fractions != null ? fractions : new ArrayList<>();
    }

    public void addFraction(Fraction fraction) {
        this.fractions.add(fraction);
    }

    public boolean isBillable() {
        return hasContent(getFirstName())
                && hasContent(getFamilyName())
                && hasContent(email)
                && hasContent(identity)
                && hasContent(address)
                && hasContent(city)
                && hasContent(province)
                && hasContent(postalCode);
    }

    private boolean hasContent(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public String fullName() {
        return getName() + " " + getFamilyName();
    }

    public String initials() {
        String firstName = getName();
        return firstName == null || firstName.isBlank() ? "" : firstName.trim().charAt(0) + ".";
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", familyName='" + familyName + '\'' +
                ", fractions=" + fractions +
                '}';
    }
}
