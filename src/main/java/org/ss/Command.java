package org.ss;

public class Command {
    public static void parseCommand(String command){
        if(command.isEmpty()){ // cmd에 아무것도 없음
            return;
        }

        command = command.toLowerCase().strip();
        String[] commands = command.split(" ");
//        System.out.println(commands[0]);

        if (commands[0].equals("help")) { // 명령어
            System.out.println("help");
            Logger.commands();
        } else if (commands[0].equals("user")) { // 회원정보
            if(commands[1].equals("login")) {

            } else if (commands[1].equals("signup")) {

            }
        } else if (commands[0].equals("manage")) { // 관리

        } else {
            Logger.error("Invalid command");
        }
    }
}
