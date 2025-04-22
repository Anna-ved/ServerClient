package org.example;

import org.example.datalib.console.Console;
import org.example.datalib.console.StandardConsole;
import org.example.datalib.exceptions.ExitException;
import org.example.datalib.models.ExecStatus;
import org.example.datalib.models.SendToken;
import org.example.managers.CollectionManager;
import org.example.managers.CommandExecManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.example.datalib.SocketEnvironment.SERVER_STOP_COMMAND;

public class Main {
    public static void main(String[] args) {
        Console console = new StandardConsole(new BufferedReader(new InputStreamReader(System.in)));
        try{
            UPDServer server = new UPDServer();
            CommandExecManager commandExecManager = new CommandExecManager(server);
            System.out.println("Сервер запущен. Ожидание клиентов...");
            while (true){
                SendToken sendToken;
                sendToken = server.receive();
                ExecStatus execStatus = commandExecManager.runCommand(sendToken.getTokens());
                server.send(execStatus);
                if(server.isCommandReceived(SERVER_STOP_COMMAND) && execStatus.isStopServer()) break;
            }
            server.stop();
        }catch (IOException e) {
            System.err.println("Ошибка работы с сервером: " + e.getMessage());
        }catch (ExitException e){
            console.println("Закрываем программу...");
            console.println("""
                      \s
               ／＞　 フ\s
      　　　　| 　u　 u|\s
       　　　／`ミ _x 彡    | Пока - пока :3 |
      　 　 /　　　 　 |\s
      　　 /　 ヽ　　 ﾉ\s
      ／￣|　　 |　|　|\s
      | (￣ヽ＿_ヽ_)_)\s
      ＼二つ""".indent(1));
        }
    }
}
