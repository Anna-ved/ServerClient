package org.example.commands.simpleCommands;

import org.example.commands.Command;
import org.example.managers.CollectionManager;
import org.example.module.Organization;
import org.example.datalib.models.ExecStatus;;

public class InfoCommand extends Command {
    private final CollectionManager collectionManager = CollectionManager.getInstance();
    public InfoCommand() {
        super("info", "Выводит в стандартный поток вывода информацию о коллекции", false);
    }

    @Override
    public ExecStatus execute(Organization organization, String... args) {
        if (organization != null || args.length > 1) {
            throw new IllegalArgumentException(String.format("Команде '%s' были переданы невалидные аргументы. Введите 'help' для справки.", getName()));
        }


        String HEADER_COLOR = "\u001B[34m"; // Синий цвет заголовка
        String RESET        = "\u001B[0m";  // Сброс цвета
        String RED          = "\u001B[31m"; // Красный цвет

        String initTime = (collectionManager.getLastInitTime() == null)
                ? RED + "Коллекция ещё не была инициализирована                      " + RESET
                : "Дата: " + collectionManager.getLastInitTime().toLocalDate() + " | Время: " + collectionManager.getLastInitTime().toLocalTime();

        String saveTime = (collectionManager.getLastSaveTime() == null)
                ? RED + "Коллекция ещё не была сохранена                             " + RESET
                : "Дата: " + collectionManager.getLastSaveTime().toLocalDate() + " | Время: " + collectionManager.getLastSaveTime().toLocalTime();

        StringBuilder sb = new StringBuilder();


        sb.append("┌──────────────────────────────┬──────────────────────────────────────────────────────────────┐\n");
        sb.append(String.format("│ " + HEADER_COLOR + "%-28s" + RESET + " │ " + HEADER_COLOR + "%-61s" + RESET + "│%n", "Атрибут", "Значение"));
        sb.append("├──────────────────────────────┼──────────────────────────────────────────────────────────────┤\n");
        sb.append(String.format("│ %-28s │ %-60s │%n", "Тип коллекции", collectionManager.getCollection().getClass().getSimpleName()));
        sb.append(String.format("│ %-28s │ %-60s │%n", "Время инициализации", initTime));
        sb.append(String.format("│ %-28s │ %-60s │%n", "Время сохранения", saveTime));
        sb.append(String.format("│ %-28s │ %-60d │%n", "Элементов в коллекции", collectionManager.getCollection().size()));
        sb.append("└──────────────────────────────┴──────────────────────────────────────────────────────────────┘\n");

        return new ExecStatus(sb.toString());
    }
}
