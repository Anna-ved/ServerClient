package org.example.commands;

import org.example.datalib.models.ExecStatus;
import org.example.module.Organization;

public abstract class Command {
    private String name;
    private String description;
    private boolean needOrganization;

    public Command(String name, String description, boolean needOrganization) {
        this.name = name;
        this.description = description;
        this.needOrganization = needOrganization;
    }

    public abstract ExecStatus execute(Organization organization, String... args);

    public boolean needOrganization() {
        return needOrganization;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
