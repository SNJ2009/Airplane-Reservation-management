package org.ss;

import org.ss.common.Color;
import org.ss.common.Scan;

public class Main {
    public static void main(String[] args) {
        Main.line();
        Main.asciArt();
        System.out.println("=============================================================");
        System.out.println("COMMAND HERE");
        System.out.println("=============================================================");

        while(true) {
            System.out.print(" > ");
            String command = Scan.line();
            Command.parseCommand(command);
        }
    }

    private static void line(){
        for(int i = 0; i < 100; i++){
            System.out.println();
        }
    }

    private static void asciArt(){
        System.out.println(Color.CYAN);
        System.out.println("     ___   _   ____   ____   _        ___   _   _   _____");
        System.out.println("    / _ \\ | | |  _ \\ |  _ \\ | |      / _ \\ | \\ | | | ____|");
        System.out.println("   / /_\\ \\| | | |_) || |_) || |     / /_\\ \\|  \\| | |  _|  ");
        System.out.println("  / ____ \\| | |  _ < |  __/ | |___ / ____ \\| |\\  | | |___ ");
        System.out.println(" /_/    \\_\\_|_|_| \\_\\|_|    |_____/_/    \\_\\_| \\_| |_____|");
        System.out.println(Color.RESET);
    }
}