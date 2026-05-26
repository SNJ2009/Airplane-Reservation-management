package org.ss;

import org.ss.common.Scan;

public class Main {
    public static void main(String[] args) {
        String command = Scan.line();
        Command.parseCommand(command);
    }
}
