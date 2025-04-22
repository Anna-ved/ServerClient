package org.example.commands.simpleCommands;

import org.example.commands.Command;
import org.example.managers.CommandManager;
import org.example.module.Organization;
import org.example.datalib.models.ExecStatus;;import java.util.concurrent.atomic.AtomicInteger;

public class HistoryCommand extends Command {

    public HistoryCommand() {
        super("history", "Выводит последние 11 команд", false);
    }

    @Override
    public ExecStatus execute(Organization organization, String... args) {
        if (organization != null || args.length > 1) {
            throw new IllegalArgumentException(String.format("Команде '%s' были переданы невалидные аргументы. Введите 'help' для справки.", getName()));
        }

        StringBuilder sb = new StringBuilder();

        String HEADER_COLOR = "\u001B[34m"; // Синий цвет заголовка
        String RESET        = "\u001B[0m";  // Сброс цвета
        sb.append("┌─────────────────────────────────────────────┐\n");
        sb.append(String.format("│ " + HEADER_COLOR + "%-43s" + RESET + " │%n", "История"));

        AtomicInteger index = new AtomicInteger(1);
        CommandManager.getHistory()
                .forEach(command -> {
                    sb.append("├─────────────────────────────────────────────┤\n")
                            .append(String.format("│ %-2d) %-39s │%n",
                                    index.getAndIncrement(),
                                    command.getName()));
                });

        sb.append("└─────────────────────────────────────────────┘\n");

        return new ExecStatus(sb.toString());
    }
}
