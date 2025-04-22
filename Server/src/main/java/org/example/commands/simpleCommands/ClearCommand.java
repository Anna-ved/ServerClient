package org.example.commands.simpleCommands;

import org.example.commands.Command;
import org.example.managers.CollectionManager;
import org.example.module.Organization;
import org.example.datalib.models.ExecStatus;;

public class ClearCommand extends Command {
    private final CollectionManager collectionManager = CollectionManager.getInstance();
    public ClearCommand() {
        super("clear", "Очищает коллекцию", false);
    }

    @Override
    public ExecStatus execute(Organization organization, String... args) {
        if (organization != null || args.length > 1) {
            throw new IllegalArgumentException(String.format("Команде '%s' были переданы невалидные аргументы. Введите 'help' для справки.", getName()));
        }

        collectionManager.clear();
        ExecStatus execStatus = new ExecStatus("Коллекция была успешно очищена");
        execStatus.setNext(true);
        return execStatus;
    }
}
