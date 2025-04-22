package org.example.commands.simpleCommands;

import org.example.commands.Command;
import org.example.managers.CollectionManager;
import org.example.module.Organization;
import org.example.datalib.models.ExecStatus;

import java.io.IOException;

public class SaveCommand extends Command {
    private final CollectionManager collectionManager = CollectionManager.getInstance();
    public SaveCommand() {
        super("save", "Сохраняет коллекцию в файл", false);
    }

    @Override
    public ExecStatus execute(Organization organization, String... args) {
        if (organization != null || args.length > 1) {
            throw new IllegalArgumentException(String.format("Команде '%s' были переданы невалидные аргументы. Введите 'help' для справки.", getName()));
        }
        try {
            collectionManager.save();
            return new ExecStatus("Коллекция успешно сохранена!");
        } catch (IOException e) {
            return new ExecStatus(false, "Не удалось сохранить коллекцию в файл");
        }
    }
}
