package org.example.commands.simpleCommands;

import org.example.commands.Command;
import org.example.datalib.models.ExecStatus;
import org.example.module.Organization;

public class ExecuteScriptCommand extends Command {

    public ExecuteScriptCommand() {
        super("execute_script", "Считывает и исполняет скрипт из указанного файла.", false);
    }

    @Override
    public ExecStatus execute(Organization organization, String... args) {
        return null;
    }
}
