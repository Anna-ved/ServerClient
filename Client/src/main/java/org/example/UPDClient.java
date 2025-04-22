package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.datalib.models.ExecStatus;
import org.example.datalib.models.SendToken;

import java.io.IOException;
import java.net.*;

import static org.example.datalib.SocketEnvironment.*;

public class UPDClient {
    private static final ObjectMapper mapper = new ObjectMapper();

    DatagramSocket socket;
    InetAddress serverAddress;
    public UPDClient() throws IOException {
        socket = new DatagramSocket();
        serverAddress = InetAddress.getByName(SERVER_HOST);
    }

    // false если tokens не валидные для отправки
    public boolean send(String[] tokens) throws IOException{
        if (tokens.length == 0 || tokens[0].isEmpty()) return false;
        SendToken sendTokens = new SendToken(tokens);
        String jsonMessage = mapper.writeValueAsString(sendTokens);
        byte[] sendData = jsonMessage.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(
                sendData,
                sendData.length,
                serverAddress,
                SERVER_PORT
        );
        socket.send(sendPacket);
        return true;
    }

    public ExecStatus receive() throws IOException{
        byte[] receiveData = new byte[BUFFER_SIZE];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);
        String response = new String(
                receivePacket.getData(),
                0,
                receivePacket.getLength()
        );
        return mapper.readValue(response, ExecStatus.class);
    }
}
