package org.example.commands.elementCommands;


import org.example.commands.Command;
import org.example.datalib.models.ExecStatus;
import org.example.managers.CollectionManager;
import org.example.module.Organization;


public class UpdateCommand extends Command {
    private final CollectionManager collectionManager = CollectionManager.getInstance();
    public UpdateCommand() {
        super("update id {element}", "Обновляет элемент коллекции, id которого равен заданному", true);
    }

    @Override
    public ExecStatus execute(Organization organization, String... args) {
        if (organization == null || args.length != 2)
            throw new IllegalArgumentException(String.format("Команде '%s' были переданы невалидные аргументы. Введите 'help' для справки.", getName()));

        String message = "";
        boolean isOk = true;
        if (collectionManager.add(organization)){
            message = "Организация успешно обновлена!";
        }else{
            isOk = false;
            message = "Не удалось обновить коллекцию. Похоже, элемента с таким ID не существует";
        }
        ExecStatus execStatus = new ExecStatus(isOk, message);
        execStatus.setNext(true);

        return execStatus;
    }
}
