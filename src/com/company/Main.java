package com.company;

import java.net.ServerSocket;

public class Main {
    private static ServerPoint customerPoint;
    private static ServerPoint chefPoint;
    private static ServerPoint billerPoint;

    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(8901);
        System.out.println("Restaurant Server Running");
        try {
            customerPoint = new ServerPoint("Customer", listener.accept());
            chefPoint = new ServerPoint("Chef", listener.accept());
            billerPoint = new ServerPoint("Biller", listener.accept());
            customerPoint.start();
            chefPoint.start();
            billerPoint.start();
        }
        finally {
            listener.close();
        }
    }

    public static void notifyOtherPoints(ServerPoint point) {
        if (point.getClientName().equals("Customer")) {
            chefPoint.notifyClient();
            billerPoint.notifyClient();
        }
        else if (point.getClientName().equals("Chef")) {
            customerPoint.notifyClient();
            billerPoint.notifyClient();
        }
        else if (point.getClientName().equals("Biller")) {
            customerPoint.notifyClient();
            chefPoint.notifyClient();
        }
    }
}
