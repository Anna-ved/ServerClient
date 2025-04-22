package org.example.module;

import lombok.Getter;

import java.io.Serializable;

/**
 * Перечисления для типов организаций
 */
@Getter
public enum OrganizationType implements Serializable {
    COMMERCIAL("commercial"),
    PUBLIC("public"),
    OPEN_JOINT_STOCK_COMPANY("open joint stock company");

    private final String label;
    OrganizationType(String label) {
        this.label = label;
    }

    public static OrganizationType valueOfLabel(String label) {
        for (OrganizationType e : values()) {
            if (e.label.equals(label)) {
                return e;
            }
        }
        return null;
    }
}
