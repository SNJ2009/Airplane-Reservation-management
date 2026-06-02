package org.ss;

import org.ss.services.BookService;
import org.ss.services.ScheduleService;

import java.util.HashMap;

public class Command {
    public static void parseCommand(String command){
        if(command.isEmpty() || command.startsWith("air")){ // cmd에 아무것도 없음
            return;
        }

        command = command.toLowerCase().strip();
        String[] commands = command.split(" ");

        HashMap<String, Runnable> commandMap = new HashMap<>();
        commandMap.put("help", Logger::commands);
        commandMap.put("user", () -> {});
        commandMap.put("schedules", () -> {});
        commandMap.put("book", () -> {});
    }
}
