package org.ss;

import org.ss.common.Color;
import org.ss.common.Scan;

public class Main {
    public static void main(String[] args) {
        Logger.line(200);
        Logger.asciArt();
        System.out.println("=============================================================");
        System.out.println("COMMAND HERE");
        System.out.println("=============================================================");

        while(true) {
            System.out.print(" > ");
            String command = Scan.line();

            String[] parsedCommand = Command.parseCommand(command);
            Command.executeCommand(parsedCommand);
        }
    }
}