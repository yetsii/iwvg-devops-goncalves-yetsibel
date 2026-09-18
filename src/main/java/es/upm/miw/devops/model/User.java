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

    @Transient
    private List<Fraction> fractions;

    public User() {
        this.fractions = new ArrayList<>();
    }

    public User(String id, String name, String familyName, List<Fraction> fractions) {
        this.id = id;
        this.name = name;
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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
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

    public String fullName() {
        return this.name + " " + this.familyName;
    }

    public String initials() {
        return this.name == null || this.name.isEmpty() ? "" : this.name.charAt(0) + ".";
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
