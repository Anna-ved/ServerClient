package org.example.commands.simpleCommands;

import org.example.commands.Command;
import org.example.managers.CollectionManager;
import org.example.module.Organization;
import org.example.datalib.models.ExecStatus;;import java.util.stream.StreamSupport;

public class FilterStartsWithFullNameCommand extends Command {
    private final CollectionManager collectionManager = CollectionManager.getInstance();
    public FilterStartsWithFullNameCommand() {
        super("filter_starts_with_full_name fullName", "вывести элементы, значение поля fullName которых начинается с заданной подстроки", false);
    }

    @Override
    public ExecStatus execute(Organization organization, String... args) {
        if (organization != null || args.length != 2) {
            throw new IllegalArgumentException(String.format("Команде '%s' были переданы невалидные аргументы. Введите 'help' для справки.", getName()));
        }

        StringBuilder sb = new StringBuilder();
        String HEADER_COLOR = "\u001B[34m"; // Синий цвет заголовка
        String RESET        = "\u001B[0m";  // Сброс цвета
        String RED          = "\u001B[31m"; // Красный цвет
        String ORANGE       = "\u001B[38;5;214m"; // ANSI 256-цветная палитра, код 214 (оранжевый)


        String fullName = args[1];

        sb.append("┌──────────────────────────────────────────────────────────────────┐\n");
        sb.append(String.format("│ " + HEADER_COLOR + "%-64s" + RESET + " │%n", String.format("Элементы, которые начинаются на '%s'", fullName)));

        StreamSupport.stream(collectionManager.spliterator(), false)
                .filter(org -> org.getFullName() != null && org.getFullName().startsWith(fullName))
                .forEach(org -> {
                    sb.append("├──────────────────────────────────────────────────────────────────┤\n");
                    sb.append(String.format("│ ID: %-60d │%n", org.getId()));
                    sb.append(String.format("│ Name: %-58s │%n", org.getName()));
                    sb.append(String.format("│ Coordinates: X: %-10d; Y: %-33.2f │%n",
                            org.getCoordinates().getX(), org.getCoordinates().getY()));
                    sb.append(String.format("│ Creation Date: %-49s │%n", org.getCreationDate().toLocalDate()));
                    sb.append(String.format("│ Creation Time: %-49s │%n", org.getCreationDate().toLocalTime()));
                    sb.append(String.format("│ Annual Turnover: %-47d │%n", org.getAnnualTurnover()));
                    sb.append(String.format("│ "+ORANGE+"Full Name: %-53s"+RESET+" │%n", org.getFullName()));
                    sb.append(String.format("│ Employees: %-53d │%n", org.getEmployeesCount()));
                    sb.append(String.format("│ Type: %-58s │%n", org.getType()));
                    sb.append(String.format("│ Postal Address: %-48s │%n", org.getPostalAddress().getZipCode()));
                    sb.append(String.format("│ Location: X: %-5d; Y: %-5d; Name: %-28s │%n",
                            org.getPostalAddress().getTown().getX(),
                            org.getPostalAddress().getTown().getY(),
                            org.getPostalAddress().getTown().getName()));
                });

        sb.append("└──────────────────────────────────────────────────────────────────┘\n");

        return new ExecStatus(sb.toString());
    }
}
