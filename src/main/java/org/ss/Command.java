package org.ss;

public class Command {
    public static void parseCommand(String command){
        String[] commands = command.split(" ");

        if(commands.length == 1){ // cmd에 아무것도 없음
            System.out.println("명령어가 입력되지 않았습니다.");
            return;
        }


    }
}
