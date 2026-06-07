package org.ss;

import org.ss.common.ConsoleView;
import org.ss.common.Scan;
import org.ss.controller.Command;

public class Main {
    public static void main(String[] args) {
        ConsoleView.line(200);
        ConsoleView.asciArt();
        System.out.println("=============================================================");
        System.out.println("COMMAND HERE (show commands : air help)");
        System.out.println("=============================================================");

        while(true) {
            System.out.print(" > ");
            String command = Scan.line();

            String[] parsedCommand = Command.parseCommand(command);
            Command.executeCommand(parsedCommand);
        }
    }
}