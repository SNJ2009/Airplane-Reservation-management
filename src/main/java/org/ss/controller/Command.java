package org.ss.controller;

import org.ss.common.ConsoleView;
import org.ss.services.BookService;
import org.ss.services.MemberService;
import org.ss.services.ScheduleService;

import java.util.HashMap;
import java.util.function.Consumer;

public class Command {
    private static final MemberService memberService = new MemberService();
    private static final ScheduleService scheduleService = new ScheduleService();
    private static final BookService bookService = new BookService();

    private static final HashMap<String, Consumer<String[]>> commandMap = new HashMap<>();

    static {
        commandMap.put("help", command -> ConsoleView.commands());
        commandMap.put("user", command -> {
            memberService.user(command);
        });
        commandMap.put("schedules", command -> {
            if(isInvalidLength(command, 2)) return;
            scheduleService.schedule(command); // 임시

        });
        commandMap.put("book", command -> {
            if(isInvalidLength(command, 2)) return;
            bookService.book(command); // 임시
        });
    }
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

    // 명령어 실행부분
    public static void executeCommand(String[] command){
        if(command == null) {
            ConsoleView.error("Command is null");
            return;
        } else if (command.length < 2) {
            ConsoleView.error("Invalid command");
            return;
        }

        Consumer<String[]> action = commandMap.get(command[1]);
        if(action != null) action.accept(command); // 실행
        else ConsoleView.error("Unknown command");
    }

    /**
     * 명령어 배열의 길이가 요구하는 배열의 길이를 만족하는지 검사 <br>
     * 기준에 만족하지 않으면 "Invalid command length" 출력
     *
     * @param command 입력받은 명령어 배열
     * @param requiredLength 요구 배열 길이
     * @return 요구하는 배열의 길이를 만족하지 못하면 {@code true}, 그 외 {@code false}
     */
    public static boolean isInvalidLength(String[] command, int requiredLength){
        return isInvalidLength(command, requiredLength, requiredLength);
    }

    /**
     * 명령어 배열의 길이가 요구하는 배열의 길이를 만족하는지 검사 <br>
     * 기준에 만족하지 않으면 "Invalid command length" 출력
     *
     * @param command 입력받은 명령어 배열
     * @param minLength 입력받은 배열의 최소 길이
     * @param maxLength 입력받은 배열의 최대 길이
     * @return 요구하는 배열의 길이를 만족하지 못하면 {@code true}, 그 외 {@code false}
     */
    public static boolean isInvalidLength(String[] command, int minLength, int maxLength){
        if(command.length > maxLength || command.length < minLength){
            ConsoleView.error("Invalid command length");
            return true;
        }
        return false;
    }
}
