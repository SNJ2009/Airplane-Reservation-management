package org.ss.controller;

import org.ss.common.ConsoleView;

import java.util.HashMap;
import java.util.function.Consumer;

public class Command {
    private static final MemberController MEMBER_CONTROLLER = new MemberController();
    private static final ScheduleController SCHEDULE_CONTROLLER = new ScheduleController();
    private static final BookController BOOK_CONTROLLER = new BookController();
    private static final PlaneController PLANE_CONTROLLER = new PlaneController();

    private static final HashMap<String, Consumer<String[]>> commandMap = new HashMap<>();

    static {
        commandMap.put("help", command -> ConsoleView.commands());
        commandMap.put("user", command -> MEMBER_CONTROLLER.user(command));
        commandMap.put("schedule", command -> SCHEDULE_CONTROLLER.schedule(command));
        commandMap.put("plane", command -> PLANE_CONTROLLER.plane(command));
        commandMap.put("book", command -> BOOK_CONTROLLER.book(command));
    }
    public static String[] parseCommand(String command){
        if(command.isEmpty() || !command.startsWith("air")){ // cmd에 아무것도 없음
            return null;
        }

        command = command.strip();
        String[] commands = command.split(" (?=([^\"]*\"[^\"]*\")*[^\"]*$)");

        for(int i = 0; i < commands.length; i++){ // "" 제거
            if(commands[i].startsWith("\"") && commands[i].endsWith("\"")){
                commands[i] = commands[i].substring(1, commands[i].length() - 1);
            }
        }
        return commands;
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
     * 명령어 배열의 길이가 요구하는 범위를 만족하는지 검사
     * 범위를 만족하지 못하면 IllegalArgumentException
     *
     * @param command 입력받은 명령어 배열
     * @param requiredLength 요구 배열 길이
     */
    public static void validLength(String[] command, int requiredLength){
        validLength(command, requiredLength, requiredLength);
    }

    /**
     * 명령어 배열의 길이가 요구하는 최소/최대 범위를 만족하는지 검사
     * 범위를 만족하지 못하면 IllegalArgumentException
     *
     * @param command 입력받은 명령어 배열
     * @param minLength 입력받은 배열의 최소 길이
     * @param maxLength 입력받은 배열의 최대 길이
     */
    public static void validLength(String[] command, int minLength, int maxLength){
        if(command.length > maxLength || command.length < minLength)
            throw new IllegalArgumentException("Invalid command length");
    }
}
