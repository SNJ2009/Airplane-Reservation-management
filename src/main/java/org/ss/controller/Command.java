package org.ss.controller;

import org.ss.common.ConsoleView;

import java.util.HashMap;
import java.util.function.Consumer;

public class Command {
    private static final MemberController MEMBER_CONTROLLER = new MemberController();
    private static final ScheduleController SCHEDULE_CONTROLLER = new ScheduleController();
    private static final TicketController TICKET_CONTROLLER = new TicketController();
    private static final PlaneController PLANE_CONTROLLER = new PlaneController();

    private static final HashMap<String, Consumer<String[]>> commandMap = new HashMap<>();

    static {
        commandMap.put("help", command -> ConsoleView.commands());
        commandMap.put("user", command -> MEMBER_CONTROLLER.user(command));
        commandMap.put("schedule", command -> SCHEDULE_CONTROLLER.schedule(command));
        commandMap.put("plane", command -> PLANE_CONTROLLER.plane(command));
        commandMap.put("book", command -> TICKET_CONTROLLER.ticket(command));
    }

    /**
     * 입력밭은 명령어 변환 <br>
     * 입력받은 명렁어의 최소 요구사항 'air' 없다면 return null <br><br>
     * 명렁어 키워드 별로 구분하기 쉽게 배열로 만듦 <br>
     * - 공백제거 <br>
     * - 띄어쓰기 기준 단, " 있다면 띄어쓰기 무시하고 " 한번 더 나올 때 까지 <br>
     * - 입력받은 명령어 중, 띄어쓰기 있는 명령어에 필수로 들어가있는 "" 제거
     *
     * @param command 유저가 입력한 명령어
     * @return String 배열로 변환한 값
     */
    public static String[] parseCommand(String command){
        if(command.isEmpty() || !command.startsWith("air")){ // check
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

    /**
     * 명령어 실행 <br>
     * 매개변수로 받은 command 배열의 길이가 2 이하(air ___ 형식) 또는 cmd 배열의 값 중 하나라도 "", "   ", null 이라면 실행 안하고 예외 던짐. <br><br>
     * 명렁어 포멧 확인 이후 명령어 실행 <br>
     * 만약 'air ___'에서 입력받은 ___이 명령어 맵에 없다면, 예외 또 던짐
     * @param command 명령어 배열
     */
    public static void executeCommand(String[] command) {
        if (command.length < 2) {
            throw new RuntimeException("Invalid command format");
        }
        for (String cmd : command) {
            if (cmd == null || cmd.isBlank()) {
                throw new RuntimeException("Command or argument cannot be empty");
            }
        }

        Consumer<String[]> action = commandMap.get(command[1]);
        if (action != null) action.accept(command); // 실행
        else throw new RuntimeException("Command not found");
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
