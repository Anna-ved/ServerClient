package org.example.module;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Address implements Validatable, Serializable {
    private String zipCode;     //Длина строки должна быть не меньше 4, Поле не может быть null
    private Location town;      //Поле не может быть null

    public Address(String zipCode, Location town) {
        if (zipCode == null || zipCode.length() < 4 || town == null) {
            throw new IllegalArgumentException();
        }
        this.zipCode = zipCode;
        this.town = town;
    }

    @Override
    public boolean validate() {
        if (zipCode == null || zipCode.length() < 4)
            return false;
        return town != null;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Location getTown() {
        return town;
    }
}
