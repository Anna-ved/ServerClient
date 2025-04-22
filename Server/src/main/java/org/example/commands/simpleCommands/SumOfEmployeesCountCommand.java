package org.example.commands.simpleCommands;

import org.example.commands.Command;
import org.example.managers.CollectionManager;
import org.example.module.Organization;
import org.example.datalib.models.ExecStatus;;

public class SumOfEmployeesCountCommand extends Command {
    private final CollectionManager collectionManager = CollectionManager.getInstance();
    public SumOfEmployeesCountCommand() {
        super("sum_of_employees_count", "Выводит сумму значений поля employeesCount для всех элементов коллекции", false);
    }

    @Override
    public ExecStatus execute(Organization organization, String... args) {
        if (organization != null || args.length > 1) {
            throw new IllegalArgumentException(String.format("Команде '%s' были переданы невалидные аргументы. Введите 'help' для справки.", getName()));
        }


        StringBuilder sb = new StringBuilder();

        String HEADER_COLOR = "\u001B[34m"; // Синий цвет заголовка
        String RESET        = "\u001B[0m";  // Сброс цвета
        String RED          = "\u001B[31m"; // Красный цвет


        int sum = collectionManager.getCollection().stream()
                .mapToInt(org -> org.getEmployeesCount() != null ? org.getEmployeesCount() : 0)
                .sum();

        sb.append("┌─────────────────────────────────────────────┐\n");
        sb.append(String.format("│ " + HEADER_COLOR + "%s:" +RED+ " %-11d" + RESET + " │%n", "Сумма работников всех компаний", sum));

        sb.append("└─────────────────────────────────────────────┘\n");

        return new ExecStatus(sb.toString());
    }
}
