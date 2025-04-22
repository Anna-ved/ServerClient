package org.example.commands.simpleCommands;

import org.example.commands.Command;
import org.example.managers.CollectionManager;
import org.example.module.Organization;
import org.example.datalib.models.ExecStatus;;

public class RemoveByIdCommand extends Command {
    private final CollectionManager collectionManager = CollectionManager.getInstance();
    public RemoveByIdCommand() {
        super("remove_by_id id", "Удаляет элемент из коллекции по его id", false);
    }

    @Override
    public ExecStatus execute(Organization organization, String... args) {
        if (organization != null || args.length != 2)
            throw new IllegalArgumentException(String.format("Команде '%s' были переданы невалидные аргументы. Введите 'help' для справки.", getName()));

        String message = "";
        boolean isOk = true;
        if (collectionManager.removeById(Long.parseLong(args[1]))){
            message = "Элемент был успешно удалён!";
        }else{
            isOk = false;
            message = "Не удалось удалить элемент";
        }
        ExecStatus execStatus = new ExecStatus(isOk, message);
        execStatus.setNext(true);

        return execStatus;
    }
}
