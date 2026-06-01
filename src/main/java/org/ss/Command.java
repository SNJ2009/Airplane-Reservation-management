package org.ss;

import org.ss.services.BookService;
import org.ss.services.ScheduleService;

import java.util.HashMap;

public class Command {
    public static void parseCommand(String command){
        if(command.isEmpty()){ // cmd에 아무것도 없음
            return;
        }

        command = command.toLowerCase().strip();
//        String[] commands = command.split(" ");


        HashMap<String, Runnable> commandMap = new HashMap<>();
        commandMap.put("help", Logger::commands);
        commandMap.put("login", () -> {});
        commandMap.put("signup", () -> {});
        commandMap.put("schedules", ScheduleService::list);
        commandMap.put("schedule add", () -> {});
        commandMap.put("schedule remove", () -> {});
        commandMap.put("booklist", BookService::list);
        commandMap.put("book add", () -> {});
        commandMap.put("book remove", () -> {});
    }

//    public boolean isCommand(String[] command, int length){
//        return command.length == length;
//    }
}
