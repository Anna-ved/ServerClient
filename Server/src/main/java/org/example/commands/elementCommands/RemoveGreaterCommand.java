package org.example.commands.elementCommands;

import org.example.commands.Command;
import org.example.datalib.models.ExecStatus;
import org.example.managers.CollectionManager;
import org.example.module.Organization;

public class RemoveGreaterCommand extends Command {
    private final CollectionManager collectionManager = CollectionManager.getInstance();
    public RemoveGreaterCommand() {
        super("remove_greater {element}", "Удаляет из коллекции все Организации, чей годовой оборот больше, чем у заданной", true);
    }

    @Override
    public ExecStatus execute(Organization organization, String... args) {
        if (organization == null || args.length != 1)
            throw new IllegalArgumentException(String.format("Команде '%s' были переданы невалидные аргументы. Введите 'help' для справки.", getName()));

        collectionManager.removeGreater(organization);
        ExecStatus execStatus = new ExecStatus("Все элементы с годовым оборотом выше заданного были удалены");
        execStatus.setNext(true);
        return execStatus;
    }
}
