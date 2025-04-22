package org.example.commands.simpleCommands;

import org.example.commands.Command;
import org.example.managers.CollectionManager;
import org.example.module.Organization;
import org.example.datalib.models.ExecStatus;;import java.io.IOException;

public class ExitCommand extends Command {
    public ExitCommand() {
        super("exit", "Завершает программу (с сохранением)", false);
    }

    @Override
    public ExecStatus execute(Organization organization, String... args) {
        try {
            CollectionManager.getInstance().save();
            return new ExecStatus(true, "Коллекция сохранена. Завершение работы...");
        } catch (IOException e) {
            return new ExecStatus(false, "Ошибка сохранения при выходе: " + e.getMessage());
        }
    }
}
