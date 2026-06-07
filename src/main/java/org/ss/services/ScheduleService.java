package org.ss.services;

import org.ss.common.ConsoleView;
import org.ss.controller.Command;
import org.ss.dao.ScheduleDAO;
import org.ss.entity.Member;
import org.ss.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public class ScheduleService {
    private final ScheduleDAO scheduleDAO = new ScheduleDAO();

    public void schedule(String[] cmd) {
        // air schedule // air schedule dep des // air schedule add p_i dep des run fl // air schedule remove id
        if(Command.isInvalidLength(cmd, 2, 8)) return;
        if(cmd.length == 2) { list(); return; } // air schedule 라면 전체조회

        String action = cmd[2];
        if(action.equals("add")){ // 스케줄 추가
            if(isNotManager()) return;
            if(Command.isInvalidLength(cmd, 8)) return;

            int planeId = Integer.parseInt(cmd[3]);
            String departure = cmd[4];
            String destination = cmd[5];
            // air schedule add 1 ICN JFK "2026-06-07 10:00:00" 850
            LocalDateTime departureTime = LocalDateTime.parse(cmd[6].replace(" ", "T"));
            int flyTime = Integer.parseInt(cmd[7]);

            // id 부분 0이랑, planeInfo 부분 ""은 DB에 들어가는 값 아니라 파라미터 수 맞추기용
            scheduleDAO.save(new Schedule(0, planeId, departure, destination, departureTime, flyTime, ""));
        }
        else if (action.equals("remove")) { // 스케줄 삭제
            if(isNotManager()) return;
            if(Command.isInvalidLength(cmd, 4)) return;
            // 여기 만들어야 함
        }
        else if (!Command.isInvalidLength(cmd, 4)){ // 필터링 조회
            show(cmd[2], cmd[3]);
        }
        else ConsoleView.error(action + " is not a valid action");
    }

    public void list(){ // 스케줄 전체 다 보여주기
        List<Schedule> scheduleList = scheduleDAO.getScheduleList();
        printSchedule(scheduleList);
    }

    public void show(String departure, String destination) { // 출발지, 도착지 필터링해서 보여주기
        List<Schedule> scheduleList = scheduleDAO.getScheduleList(departure, destination);
        printSchedule(scheduleList);
    }

    private void printSchedule(List<Schedule> scheduleList){
        if(scheduleList.isEmpty()){
            ConsoleView.message("조회된 비행 스케줄 없음");
            return;
        }

        for(Schedule schedule : scheduleList){
            ConsoleView.message(schedule.toString());
        }
    }

    /**
     * add / remove 명렁을 실행한 유저가 관리자 권한이 있는지 확인
     * @return 권한이 없으면 {@code true} 반환, 그 외 {@code false}
     */
    private boolean isNotManager(){
        boolean isManager = Member.getInstance().isManager();

        if(!isManager){
            ConsoleView.error("권한이 없습니다.");
            return true;
        }
        return false;
    }
}
