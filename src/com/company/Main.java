package com.company;

import java.net.ServerSocket;

public class Main {
    private static ServerPoint customerPoint;
    private static ServerPoint chefPoint;

    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(8901);
        System.out.println("Restaurant Server Running");
        try {
            customerPoint = new ServerPoint("Customer", listener.accept());
            chefPoint = new ServerPoint("Chef", listener.accept());
            customerPoint.start();
            chefPoint.start();
        }
        finally {
            listener.close();
        }
    }

    public static void notifyOtherPoints(ServerPoint point) {
        if (point.getClientName().equals("Customer")) {
            chefPoint.notifyClient();
        }
        else if (point.getClientName().equals("Chef")) {
            customerPoint.notifyClient();
        }
    }
}
