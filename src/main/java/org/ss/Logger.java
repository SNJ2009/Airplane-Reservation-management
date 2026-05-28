package org.ss;

import org.ss.common.Color;

public class Logger {
    public static void commands(){ // HELP 명령 입력 시
        System.out.println("USER ===========================================");
        System.out.println("[SignUp]        user signup [id] [password]");
        System.out.println("[Login]         user login [id] [password]");
        System.out.println("MANAGEMENT -------------------------------------");
        System.out.println("FLIGHTS ----------------------------------------");
        System.out.println("[Search Flights]        show schedules");
        System.out.println("[Booked Flights]        show booked");
        System.out.println("[Reserve]               book add");
        System.out.println("[Reserve Cancel]        book remove");
        System.out.println("================================================");
    }

    public static void info(String message){
        System.out.println("[INFO] : " +message);
    }

    public static void error(String message){
        System.out.println(Color.RED+ "[ERROR] : " +message+ Color.RESET);
    }
}
