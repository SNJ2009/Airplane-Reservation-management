package org.ss;

public class Command {
    public static void parseCommand(String command){
        command = command.toLowerCase().strip();
        String[] commands = command.split(" ");

        if(commands.length == 1){ // cmd에 아무것도 없음
            System.out.println("명령어가 입력되지 않았습니다.");
            return;
        }

        if (commands[0].equals("help")) { // 명령어

        } else if (commands[0].equals("user")) { // 회원정보
            if(commands[1].equals("login")) {

            } else if (commands[1].equals("signup")) {

            }
        } else if (commands[0].equals("manage")) { // 관리

        }
    }
}
