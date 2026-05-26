package org.ss.common;

import java.util.Scanner;

public class Scan {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static String line() {
        return SCANNER.nextLine();
    }
}
