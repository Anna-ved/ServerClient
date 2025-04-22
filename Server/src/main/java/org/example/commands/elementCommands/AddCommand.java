package org.example.commands.elementCommands;

import org.example.commands.Command;
import org.example.datalib.models.ExecStatus;
import org.example.managers.CollectionManager;
import org.example.module.Organization;

public class AddCommand extends Command {
    private final CollectionManager collectionManager = CollectionManager.getInstance();
    public AddCommand() {
        super("add {element}", "Добавляет элемент в коллекцию", true);
    }

    @Override
    public ExecStatus execute(Organization organization, String... args) {
        if (organization == null || args.length != 1)
            throw new IllegalArgumentException(String.format("Команде '%s' были переданы невалидные аргументы. Введите 'help' для справки.", getName()));

        String message = "";
        boolean isOk = true;
        if (collectionManager.add(organization)){
            message = "Организация успешно добавлена в коллекцию!";
        }else{
            isOk = false;
            message = "Не удалось добавить элемент в коллекцию";
        }
        ExecStatus execStatus = new ExecStatus(isOk, message);
        return execStatus;
    }
}
