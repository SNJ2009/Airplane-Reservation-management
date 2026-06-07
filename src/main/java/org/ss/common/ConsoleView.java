package org.ss.common;

public class ConsoleView {
    public static void commands(){ // HELP 명령 입력 시
        System.out.println("USER ===========================================");
        System.out.println("[SignUp]        user signup \"id\" \"password\"");
        System.out.println("[Login]         user login \"id\" \"name\" \"password\" \"phone\"");
        System.out.println("MANAGEMENT -------------------------------------");
        System.out.println("[Schedule Add]  manage schedule add [plane_id] [] [] [] []");
        System.out.println("FLIGHTS ----------------------------------------");
        System.out.println("[Search Flights]        schedules");
        System.out.println("[Booked Flights]        booked");
        System.out.println("[Reserve]               book add");
        System.out.println("[Reserve Cancel]        book remove");
        System.out.println("================================================");
    }

    public static void info(String message){
        System.out.println("[INFO] : " +message);
    }
    public static void message(String message) { System.out.println(message); }

    public static void error(String message){
        System.err.println("[ERROR] : " +message);
    }

    public static void asciArt(){
        System.out.println(Color.CYAN);
        System.out.println("     ___   _   ____   ____   _        ___   _   _   _____");
        System.out.println("    / _ \\ | | |  _ \\ |  _ \\ | |      / _ \\ | \\ | | | ____|");
        System.out.println("   / /_\\ \\| | | |_) || |_) || |     / /_\\ \\|  \\| | |  _|  ");
        System.out.println("  / ____ \\| | |  _ < |  __/ | |___ / ____ \\| |\\  | | |___ ");
        System.out.println(" /_/    \\_\\_|_|_| \\_\\|_|    |_____/_/    \\_\\_| \\_| |_____|");
        System.out.println(Color.RESET);
    }


    public static void line(int level){
        for(int i = 0; i < level; i++){
            System.out.println();
        }
    }
}
