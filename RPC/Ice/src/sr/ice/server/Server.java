package sr.ice.server;
// **********************************************************************
//
// Copyright (c) 2003-2019 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.LocalException;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;
import sr.ice.server.SmartHomeServantLocator;

import java.io.InputStreamReader;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        int status = 0;
        Communicator communicator = null;

        try {
            // 1. Inicjalizacja ICE
            communicator = Util.initialize(args);

            SmartHomeServantLocator servantLocator = new SmartHomeServantLocator();

            ObjectAdapter adapter = communicator.createObjectAdapter("ObjectAdapter1");

            adapter.addServantLocator(servantLocator, "");

            adapter.activate();

            Scanner in = new Scanner(new InputStreamReader(System.in));
            System.out.println("Enter \"list\" to see available servants.");
            while (true) {
                String line = in.nextLine();

                if (line.equalsIgnoreCase("list")) {
                    servantLocator.printServantsList();
                } else {
                    System.out.println("Invalid command");
                }
            }

        } catch (LocalException e) {
            e.printStackTrace();
            status = 1;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            status = 1;
        }
        if (communicator != null) { //clean
            try {
                communicator.destroy();
            } catch (Exception e) {
                System.err.println(e.getMessage());
                status = 1;
            }
        }
        System.exit(status);
    }

}
