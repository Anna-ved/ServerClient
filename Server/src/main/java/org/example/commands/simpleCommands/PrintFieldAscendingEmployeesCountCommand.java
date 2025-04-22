package org.example.commands.simpleCommands;

import org.example.commands.Command;
import org.example.managers.CollectionManager;
import org.example.module.Organization;
import org.example.datalib.models.ExecStatus;;import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class PrintFieldAscendingEmployeesCountCommand extends Command {
    private final CollectionManager collectionManager = CollectionManager.getInstance();

    public PrintFieldAscendingEmployeesCountCommand() {
        super("print_field_ascending_employees_count", "Выводит значения поля employeesCount всех элементов в порядке возрастания", false);
    }

    @Override
    public ExecStatus execute(Organization organization, String... args) {
        if (organization != null || args.length != 1) {
            throw new IllegalArgumentException(String.format(
                    "Команде '%s' были переданы невалидные аргументы. Введите 'help' для справки.",
                    getName()));
        }

        StringBuilder sb = new StringBuilder();
        final String HEADER_COLOR = "\u001B[34m";
        final String RESET = "\u001B[0m";

        // Заголовок таблицы
        sb.append("┌─────────────────────────────────────────────┐\n")
                .append(String.format("│ " + HEADER_COLOR + "%-43s" + RESET + " │%n",
                        "Количество работников в порядке возрастания"))
                .append("├──────────┬──────────────────────────────────┤\n")
                .append(String.format("│ %-8s │ %-32s │%n", "Номер", "Количество работников"));

        AtomicInteger index = new AtomicInteger(1);

        // Тело таблицы
        collectionManager.getCollection().stream()
                .sorted(Comparator.comparingInt(Organization::getEmployeesCount))
                .forEach(org -> {
                    sb.append("├──────────┼──────────────────────────────────┤\n")
                            .append(String.format("│ %-8d │ %,32d │%n",
                                    index.getAndIncrement(),
                                    org.getEmployeesCount()));
                });

        // Нижняя граница таблицы
        sb.append("└──────────┴──────────────────────────────────┘\n");

        return new ExecStatus(sb.toString());
    }
}