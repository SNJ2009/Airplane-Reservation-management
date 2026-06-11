package org.ss.controller;

import org.ss.common.ConsoleView;
import org.ss.dao.PlaneDAO;
import org.ss.dao.ScheduleDAO;
import org.ss.dao.SeatDAO;
import org.ss.entity.Member;
import org.ss.entity.Schedule;
import org.ss.entity.Seat;
import org.ss.exception.AccessException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ScheduleController {
    private final ScheduleDAO scheduleDAO = new ScheduleDAO();
    private final PlaneDAO planeDAO = new PlaneDAO();
    private final SeatDAO seatDAO = new SeatDAO();

    private final Map<String, Consumer<String[]>> commandMap = new HashMap<>();
    public ScheduleController() {
        commandMap.put("add", this::add);
        commandMap.put("remove", this::delete);
    }

    public void schedule(String[] cmd) {
        // air schedule // air schedule dep des // air schedule add p_i dep des run fl // air schedule remove id
        Command.validLength(cmd, 2, 8);
        if(cmd.length == 2) { list(); return; } // air schedule 라면 전체조회

        String action = cmd[2];
        if(commandMap.containsKey(action)) {
            commandMap.get(action).accept(cmd);
        } else if (cmd.length == 4){ // 필터링 조회
            show(cmd[2], cmd[3]);
        }
        else throw new IllegalArgumentException(action + " is not a valid action"); // 잘못된 명령어
    }

    public void add(String[] cmd) {
        isNotManager();
        Command.validLength(cmd, 8);

        int planeId = Integer.parseInt(cmd[3]);
        String departure = cmd[4];
        String arrival = cmd[5];
        // air schedule add 1 ICN JFK "2026-06-07 10:00:00" 850
        LocalDateTime departureTime = LocalDateTime.parse(cmd[6].replace(" ", "T"));
        int flyTime = Integer.parseInt(cmd[7]);

        // 저장 후 PK값 받아오기
        // id 부분 0이랑, planeInfo 부분 ""은 DB에 들어가는 값 아니라 파라미터 수 맞추기용
        int scheduleID = scheduleDAO.save(new Schedule(0, planeId, departure, arrival, departureTime, flyTime, ""));

        // 스케줄에 맞는 좌석 만들기
        int planeID2 = scheduleDAO.findById(scheduleID).getPlaneId();
        int maxSeatNumber = planeDAO.findById(planeID2).getMaxSeat();

        for(int i = 0; i < maxSeatNumber; i++) { // 좌석 번호 지정하고(1, 2, 3. . .), DB에 추가
            int seatNumber = i + 1;
            seatDAO.save(new Seat(0, scheduleID, seatNumber, false));
        }
    }

    public void delete(String[] cmd) {
        isNotManager();
        Command.validLength(cmd, 4); // air schedule remove id

        int id = Integer.parseInt(cmd[3]);
        scheduleDAO.remove(id);
    }


    public void list(){ // 스케줄 전체 다 보여주기
        List<Schedule> scheduleList = scheduleDAO.getScheduleList();
        ConsoleView.printScheduleList(scheduleList);
    }

    public void show(String departure, String arrival) { // 출발지, 도착지 필터링해서 보여주기
        List<Schedule> scheduleList = scheduleDAO.getScheduleList(departure, arrival);
        ConsoleView.printScheduleList(scheduleList);
    }

    /**
     * add / remove 명렁을 실행한 유저가 관리자 권한이 있는지 확인
     */
    private void isNotManager() {
        boolean isManager = Member.getInstance().isManager();

        if(!isManager){
            throw new AccessException("Access denied");
        }
    }
}
