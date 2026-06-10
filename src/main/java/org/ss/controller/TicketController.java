package org.ss.controller;

import org.ss.common.ConsoleView;
import org.ss.dao.TicketDAO;
import org.ss.entity.Member;
import org.ss.entity.Ticket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class TicketController {
    private static final TicketDAO TICKET_DAO = new TicketDAO();
    private static final ScheduleController SCHEDULE_CONTROLLER = new ScheduleController();
    private static final Member MEMBER = Member.getInstance();

    private Map<String, Consumer<String[]>> commandMap = new HashMap<>();
    public TicketController() {
        commandMap.put("add", this::addTicket);
        commandMap.put("remove", this::removeTicket);
    }

    public void ticket(String[] cmd) {
        Command.validLength(cmd, 0, 5);
        if(cmd.length == 2) { ticketList(); return; }

        String action = cmd[2];
        if(commandMap.containsKey(action)) {
            commandMap.get(action).accept(cmd);
        }
        else if (cmd.length == 3) { // air book 도착지 || air book 항공편ID (필터링 조회)
            String value = cmd[2];
            if(value.matches("\\d+")){ // 숫자 (ID)
                filteredList(Integer.parseInt(value));
            } else { // 문자 (도착지)
                filteredList(value);
            }
        } else if (cmd.length == 4) { // air book 출발지 도착지
            String start = cmd[2]; // 출발
            String end = cmd[3]; // 도착

            filteredList(start, end);
        }
        else throw new IllegalArgumentException("Unknown action: " + action);
    }

    /**
     * 예약된 리스트 전체 출력
     */
    public void ticketList(){
        List<Ticket> list = TICKET_DAO.ticketList(MEMBER.getId());
        ConsoleView.printTicketList(list);
    }

    /**
     * 예약된 리스트 중 스케줄 ID로 필터링 후 출력
     * @param id 스케줄 ID
     */
    public void filteredList(int id){
        List<Ticket> list = TICKET_DAO.ticketList(MEMBER.getId(), id);
        ConsoleView.printTicketList(list);
    }

    /**
     * 예약된 리스트 중 도착지로 필터링 후 출력
     * @param arrival 도착지
     */
    public void filteredList(String arrival){
        List<Ticket> list = TICKET_DAO.ticketList(MEMBER.getId(), arrival);
        ConsoleView.printTicketList(list);
    }

    /**
     * 예약된 리스트 중 출발지 + 도착지로 필터링 후 출력
     * @param departure 출발지
     * @param arrival 도착지
     */
    public void filteredList(String departure, String arrival){
        List<Ticket> list = TICKET_DAO.ticketList(MEMBER.getId(), departure, arrival);
        ConsoleView.printTicketList(list);
    }

    public void addTicket(String[] cmd){
        Command.validLength(cmd, 5);

        int scheduleID = Integer.parseInt(cmd[3]);
        int selectedSeatID = Integer.parseInt(cmd[4]);

        TICKET_DAO.save(new Ticket(
                0, // save에 안들어감 그냥 파라미터 채우깅
                MEMBER.getId(),
                scheduleID,
                selectedSeatID
        ));
    }

    public void removeTicket(String[] cmd){
        Command.validLength(cmd, 4);

        int ticketId = Integer.parseInt(cmd[3]);
        TICKET_DAO.delete(MEMBER.getId(), ticketId);
    }
}
