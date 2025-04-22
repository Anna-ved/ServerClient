package org.example.module;


import lombok.Generated;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Organization implements Validatable, Serializable, Comparable<Organization> {
    private long id;                                //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name;                            //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates;                //Поле не может быть null
    private LocalDateTime creationDate;             //Поле не может быть null, Значение этого поля должно генерироваться автоматически при создании/подгружаться из файла при запуске приложения
    private int annualTurnover;                     //Значение поля должно быть больше 0
    private String fullName;                        //Поле может быть null
    private Integer employeesCount;                 //Поле может быть null, Значение поля должно быть больше 0
    private OrganizationType type;                  //Поле может быть null
    private Address postalAddress;                  //Поле не может быть null

    public void setAnnualTurnover(int annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    public void setEmployeesCount(Integer employeesCount) {
        this.employeesCount = employeesCount;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setType(OrganizationType type) {
        this.type = type;
    }

    @Override
    public boolean validate() {
        if (id <= 0) return false;
        if (name.isEmpty()) return false;
        if (annualTurnover <= 0) return false;
        if (!coordinates.validate()) return false;
        if (!postalAddress.validate()) return false;
        return !( employeesCount == null || employeesCount <= 0);
    }

    @Override
    public int compareTo(Organization organization) {
        return this.getAnnualTurnover() - organization.getAnnualTurnover();
    }


    public @NonNull Coordinates getCoordinates() {
        return this.coordinates;
    }

    @Generated
    public @NonNull LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    @Generated
    public int getAnnualTurnover() {
        return this.annualTurnover;
    }

    @Generated
    public String getFullName() {
        return this.fullName;
    }

    @Generated
    public Integer getEmployeesCount() {
        return this.employeesCount;
    }

    @Generated
    public OrganizationType getType() {
        return this.type;
    }

    @Generated
    public @NonNull Address getPostalAddress() {
        return this.postalAddress;
    }

    @Generated
    public void setId(long id) {
        this.id = id;
    }

    @Generated
    public void setName(@NonNull String name) {
        this.name = name;
    }

    @Generated
    public void setCoordinates(@NonNull Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Generated
    public void setCreationDate(@NonNull LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setPostalAddress(@NonNull Address postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String toString() {
        long getId = this.getId();
        return "Organization(id=" + getId + ", name=" + this.getName() + ", coordinates=" + this.getCoordinates() + ", creationDate=" + this.getCreationDate() + ", annualTurnover=" + this.getAnnualTurnover() + ", fullName=" + this.getFullName() + ", employeesCount=" + this.getEmployeesCount() + ", type=" + this.getType() + ", postalAddress=" + this.getPostalAddress() + ")";
    }

    public Organization() {
    }

    public Organization(long id, @NonNull String name, @NonNull Coordinates coordinates, @NonNull LocalDateTime creationDate, int annualTurnover, String fullName, Integer employeesCount, OrganizationType type, @NonNull Address postalAddress) {
        if (id <= 0) {
            throw new IllegalArgumentException("невалидный id");
        }
        else {
            this.id = id;
            this.name = name;
            this.coordinates = coordinates;
            this.creationDate = creationDate;
            this.annualTurnover = annualTurnover;
            this.fullName = fullName;
            this.employeesCount = employeesCount;
            this.type = type;
            this.postalAddress = postalAddress;
        }
    }
}
