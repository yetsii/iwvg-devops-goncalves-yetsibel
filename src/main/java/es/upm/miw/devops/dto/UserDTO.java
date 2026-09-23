package es.upm.miw.devops.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import es.upm.miw.devops.code.Fraction;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
    private String id;
    private String name;
    private String familyName;
    private boolean isBillable;
    private boolean active = true;
    private List<Fraction> fractions;

    public UserDTO() {
        this.fractions = new ArrayList<>();
    }

    public UserDTO(String id, String name, String familyName, List<Fraction> fractions) {
        this(id, name, familyName, false, fractions);
    }

    public UserDTO(String id, String name, String familyName, boolean isBillable, List<Fraction> fractions) {
        this(id, name, familyName, isBillable, true, fractions);
    }

    public UserDTO(String id, String name, String familyName, boolean isBillable, boolean active, List<Fraction> fractions) {
        this.id = id;
        this.name = name;
        this.familyName = familyName;
        this.isBillable = isBillable;
        this.active = active;
        this.fractions = fractions;
    }

    @Schema(hidden = true, description = "Identificador del usuario. Se define en la URL y no se modifica en el body.")
    public String getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamilyName() {
        return this.familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    @JsonProperty("isBillable")
    public boolean isBillable() {
        return isBillable;
    }

    @JsonProperty("isBillable")
    public void setBillable(boolean billable) {
        isBillable = billable;
    }

    @JsonProperty("isActive")
    @JsonAlias({"active", "isActive"})
    public boolean isActive() {
        return active;
    }

    @JsonProperty("isActive")
    @JsonAlias({"active", "isActive"})
    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Fraction> getFractions() {
        return fractions;
    }

    public void setFractions(List<Fraction> fractions) {
        this.fractions = fractions;
    }

    public void addFraction(Fraction fraction) {
        this.fractions.add(fraction);
    }

    public String fullName() {
        return this.name + " " + this.familyName;
    }

    public String initials() {
        return this.name.charAt(0) + ".";
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", familyName='" + familyName + '\'' +
                ", isBillable=" + isBillable +
                ", fractions=" + fractions +
                '}';
    }
}
