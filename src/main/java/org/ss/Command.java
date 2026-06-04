package org.ss;

import org.ss.entity.Member;
import org.ss.services.BookService;
import org.ss.services.MemberService;
import org.ss.services.ScheduleService;

import java.util.Arrays;
import java.util.HashMap;

public class Command {
    public static String[] parseCommand(String command){
//        System.out.println("[Command] "+command);
        if(command.isEmpty() || !command.startsWith("air")){ // cmd에 아무것도 없음
            return null;
        }

        command = command.strip();
        String[] commands = command.split(" (?=([^\"]*\"[^\"]*\")*[^\"]*$)");

        for(int i = 0; i < commands.length; i++){
            if(commands[i].startsWith("\"") && commands[i].endsWith("\"")){
                commands[i] = commands[i].substring(1, commands[i].length() - 1);
            }
        }
        return commands;
//        System.out.println(Arrays.toString(commands));
    }

    public static void executeCommand(String[] command){ // 여기 수정해야 함
        HashMap<String, Runnable> commandMap = new HashMap<>();
        MemberService memberService = new MemberService();

        commandMap.put("help", Logger::commands);
        commandMap.put("user", () -> memberService.user(command[2], command[3], command[4])); // 문제 있는듯
        commandMap.put("schedules", () -> {});
        commandMap.put("book", () -> {});

        commandMap.get(command[1]).run();
    }
}
