package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.datalib.models.ExecStatus;
import org.example.datalib.models.SendToken;
import java.io.IOException;
import java.net.*;

import static org.example.datalib.SocketEnvironment.*;

public class UPDServer {
    private static final ObjectMapper mapper = new ObjectMapper();

    DatagramSocket socket;
    DatagramPacket receivePacket;

    private InetAddress lastClientAddress;
    private int lastClientPort;

    private SendToken commandTokens;

    public UPDServer() throws IOException {
        socket = new DatagramSocket(SERVER_PORT);
        //socket.setSoTimeout(1000); // для неблокового режима
        byte[] receiveData = new byte[BUFFER_SIZE];
        // Создаем пакет для получения данных
        receivePacket = new DatagramPacket(receiveData, receiveData.length);
    }

    public SendToken receive() throws IOException {
        // Получаем данные от клиента
        socket.receive(receivePacket);

        // Сохраняем адрес и порт клиента
        lastClientAddress = receivePacket.getAddress();
        lastClientPort = receivePacket.getPort();

        // Преобразуем данные в строку
        String message = new String(receivePacket.getData(), 0, receivePacket.getLength());

        commandTokens = mapper.readValue(message, SendToken.class);
        return commandTokens;
    }

    public boolean isCommandReceived(String command) throws IOException {
        return commandTokens.getFirstToken().equalsIgnoreCase(command);
    }

    public void stop() {
        if (socket != null) {
            socket.close();
        }
    }

    public void send(String message) throws IOException {
        send(new ExecStatus(message));
    }

    public void sendErr(String message) throws IOException {
        ExecStatus execStatus = new ExecStatus(false, message);
        execStatus.setNext(true);
        send(execStatus);
    }

    public void sendGritting(String message) throws IOException {
        ExecStatus execStatus = new ExecStatus(message);
        execStatus.setGritting(true);
        execStatus.setNext(true);
        send(execStatus);
    }

    public void send(ExecStatus execStatus) throws IOException {
        if (!(lastClientAddress == null || lastClientPort == 0)) {
            String responseMessage = mapper.writeValueAsString(execStatus);
            byte[] sendData = responseMessage.getBytes();
            // Создаем пакет для отправки данных обратно клиенту
            DatagramPacket sendPacket = new DatagramPacket(
                    sendData,
                    sendData.length,
                    lastClientAddress,
                    lastClientPort
            );
            // Отправляем ответ клиенту
            socket.send(sendPacket);
        }
    }
}
