package org.example.commands.simpleCommands;

import org.example.commands.Command;
import org.example.datalib.models.ExecStatus;
import org.example.managers.CommandManager;
import org.example.module.Organization;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help", "Выводит справку по доступным командам", false);
    }

    @Override
    public ExecStatus execute(Organization organization, String... args) {
        if (organization != null || args.length > 1) {
            throw new IllegalArgumentException(String.format("Команде '%s' были переданы невалидные аргументы. Введите 'help' для справки.", getName()));
        }

        StringBuilder sb = new StringBuilder();

        String HEADER_COLOR = "\u001B[34m"; // Синий цвет заголовка
        String RESET        = "\u001B[0m";  // Сброс цвета
        //                 //
        sb.append("┌──────────────────────────────────────────┬─────────────────────────────────────────────────────────────────────────────────────────────────────────────┐\n");
        sb.append(String.format("│ " + HEADER_COLOR + "%-40s" + RESET + " │ " + HEADER_COLOR + "%-108s" + RESET + "│%n", "Команда", "Описание"));
        sb.append("├──────────────────────────────────────────┼─────────────────────────────────────────────────────────────────────────────────────────────────────────────┤\n");

        CommandManager.getCommandMap().values()
                .forEach(command ->
                        sb.append(String.format("│ %-40s │ %-108s│%n",
                                command.getName(),
                                command.getDescription()))
                );

        sb.append("└──────────────────────────────────────────┴─────────────────────────────────────────────────────────────────────────────────────────────────────────────┘\n");



        return new ExecStatus(sb.toString());
    }
}
