package org.example.commands.simpleCommands;

import org.example.commands.Command;
import org.example.managers.CollectionManager;
import org.example.module.Organization;
import org.example.datalib.models.ExecStatus;;

public class RemoveAtCommand extends Command {
    private final CollectionManager collectionManager = CollectionManager.getInstance();
    public RemoveAtCommand() {
        super("remove_at index", "Удаляет элемент из коллекции по его индексу (позиции в коллекции)", false);
    }

    @Override
    public ExecStatus execute(Organization organization, String... args) {
        if (organization != null || args.length != 2)
            throw new IllegalArgumentException(String.format("Команде '%s' были переданы невалидные аргументы. Введите 'help' для справки.", getName()));

        int index = Integer.parseInt(args[1]);
        try {
            collectionManager.removeByIndex(index);
            return new ExecStatus("Элемент был успешно удалён");
        } catch (IndexOutOfBoundsException e) {
            return new ExecStatus(false, "Индекс вышел за пределы коллекции");
        }
    }
}
