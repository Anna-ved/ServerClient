package org.example.managers;

import lombok.Getter;
import org.example.commands.Command;
import org.example.commands.elementCommands.AddCommand;
import org.example.commands.elementCommands.RemoveGreaterCommand;
import org.example.commands.elementCommands.UpdateCommand;
import org.example.commands.simpleCommands.*;

import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class CommandManager {
    private static CommandManager instance;
    private CommandManager(){}

    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }


    private static final Map<String, Command> commandMap = new LinkedHashMap<>();
    private static final Deque<Command> history = new LinkedList<>();

    public static Map<String, Command> getCommandMap() {
        return commandMap;
    }

    public static Deque<Command> getHistory() {
        return history;
    }

    static {
        commandMap.put("help", new HelpCommand());
        commandMap.put("info", new InfoCommand());
        commandMap.put("show", new ShowCommand());
        commandMap.put("add", new AddCommand());
        commandMap.put("update", new UpdateCommand());
        commandMap.put("remove_by_id", new RemoveByIdCommand());
        commandMap.put("clear", new ClearCommand());
        commandMap.put("execute_script", new ExecuteScriptCommand());
        commandMap.put("exit", new ExitCommand());
        commandMap.put("remove_at", new RemoveAtCommand());
        commandMap.put("remove_greater", new RemoveGreaterCommand());
        commandMap.put("history", new HistoryCommand());
        commandMap.put("sum_of_employees_count", new SumOfEmployeesCountCommand());
        commandMap.put("filter_starts_with_full_name", new FilterStartsWithFullNameCommand());
        commandMap.put("print_field_ascending_employees_count", new PrintFieldAscendingEmployeesCountCommand());
    }

    public Command getCommand(String commandName) {
        return commandMap.get(commandName);
    }
    public void updateHistory(Command command) {
        history.addLast(command);
        if (history.size() > 11)
            history.removeFirst();
    }
}
