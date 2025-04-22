package org.example.commands.simpleCommands;

import org.example.commands.Command;
import org.example.managers.CollectionManager;
import org.example.module.Organization;
import org.example.datalib.models.ExecStatus;;

public class ShowCommand extends Command {
    private final CollectionManager collectionManager = CollectionManager.getInstance();
    public ShowCommand() {
        super("show", "Выводит в стандартный поток вывода все элементы коллекции в строковом представлении", false);
    }
    @Override
    public ExecStatus execute(Organization organization, String... args) {
        if (organization != null || args.length > 1) {
            throw new IllegalArgumentException(String.format("Команде '%s' были переданы невалидные аргументы. Введите 'help' для справки.", getName()));
        }

        StringBuilder sb = new StringBuilder();
        String HEADER_COLOR = "\u001B[34m"; // Синий цвет заголовка
        String RESET = "\u001B[0m";  // Сброс цвета

        sb.append("┌──────────────────────────────────────────────────────────────────┐\n");
        sb.append(String.format("│ " + HEADER_COLOR + "%-64s" + RESET + " │%n", "Collection Contents"));

        collectionManager.getCollection().stream()
                .forEachOrdered(org -> {
                    sb.append("├──────────────────────────────────────────────────────────────────┤\n")
                            .append(String.format("│ ID: %-60d │%n", org.getId()))
                            .append(String.format("│ Name: %-58s │%n", org.getName()))
                            .append(String.format("│ Coordinates: X: %-10d; Y: %-33.2f │%n",
                                    org.getCoordinates().getX(), org.getCoordinates().getY()))
                            .append(String.format("│ Creation Date: %-49s │%n", org.getCreationDate().toLocalDate()))
                            .append(String.format("│ Creation Time: %-49s │%n", org.getCreationDate().toLocalTime()))
                            .append(String.format("│ Annual Turnover: %-47d │%n", org.getAnnualTurnover()))
                            .append(String.format("│ Full Name: %-53s │%n", org.getFullName()))
                            .append(String.format("│ Employees: %-53d │%n", org.getEmployeesCount()))
                            .append(String.format("│ Type: %-58s │%n", org.getType()))
                            .append(String.format("│ Postal Address: %-48s │%n", org.getPostalAddress().getZipCode()))
                            .append(String.format("│ Location: X: %-5d; Y: %-5d; Name: %-28s │%n",
                                    org.getPostalAddress().getTown().getX(),
                                    org.getPostalAddress().getTown().getY(),
                                    org.getPostalAddress().getTown().getName()));
                });

        sb.append("└──────────────────────────────────────────────────────────────────┘\n");

        return new ExecStatus(sb.toString());
    }

}
