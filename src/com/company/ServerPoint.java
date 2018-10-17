package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerPoint extends Thread {
    private String clientName;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public ServerPoint(String clientName, Socket socket) {
        this.clientName = clientName;
        this.socket = socket;
        try {
            input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println("WELCOME " + clientName);
        } catch (IOException e) {
            System.out.println("Server died");
        }
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void run() {
        try {
            // Repeatedly get commands from the client and process them.
            while (true) {
                String command = input.readLine();
                if (command.startsWith("UPDATE")) {
                    Main.notifyOtherPoints(this);
                }
                if (command.startsWith("QUIT")) {
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Server died: " + e);
        } finally {
            try {socket.close();} catch (IOException e) {}
        }
    }

    public void notifyClient() {
        output.println("UPDATE");
    }
}
