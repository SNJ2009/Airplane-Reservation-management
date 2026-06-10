package org.ss.controller;

import org.ss.common.ConsoleView;
import org.ss.dao.ScheduleDAO;
import org.ss.entity.Member;
import org.ss.entity.Schedule;
import org.ss.exception.AccessException;

import java.time.LocalDateTime;
import java.util.List;

public class ScheduleController {
    private final ScheduleDAO scheduleDAO = new ScheduleDAO();

    public void schedule(String[] cmd) {
        // air schedule // air schedule dep des // air schedule add p_i dep des run fl // air schedule remove id
        Command.validLength(cmd, 2, 8);
        if(cmd.length == 2) { list(); return; } // air schedule 라면 전체조회

        String action = cmd[2];
        if(action.equals("add")){ // 스케줄 추가
            isNotManager();
            Command.validLength(cmd, 8);

            int planeId = Integer.parseInt(cmd[3]);
            String departure = cmd[4];
            String arrival = cmd[5];

            // air schedule add 1 ICN JFK "2026-06-07 10:00:00" 850
            LocalDateTime departureTime = LocalDateTime.parse(cmd[6].replace(" ", "T"));
            int flyTime = Integer.parseInt(cmd[7]);

            // id 부분 0이랑, planeInfo 부분 ""은 DB에 들어가는 값 아니라 파라미터 수 맞추기용
            scheduleDAO.save(new Schedule(0, planeId, departure, arrival, departureTime, flyTime, ""));
        }
        else if (action.equals("remove")) { // 스케줄 삭제
            isNotManager();
            Command.validLength(cmd, 4); // air schedule remove id

            int id = Integer.parseInt(cmd[3]);
            scheduleDAO.remove(id);
        }
        else if (cmd.length == 4){ // 필터링 조회
            show(cmd[2], cmd[3]);
        }
        else throw new IllegalArgumentException(action + " is not a valid action"); // 잘못된 명령어
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
