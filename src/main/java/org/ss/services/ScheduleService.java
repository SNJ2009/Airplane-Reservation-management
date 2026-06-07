package org.ss.services;

import org.ss.common.ConsoleView;
import org.ss.dao.ScheduleDAO;
import org.ss.entity.Schedule;

import java.util.List;

public class ScheduleService {
    private final ScheduleDAO scheduleDAO = new ScheduleDAO();

    public void schedule(String[] cmd) { // 스케줄 보여줄건지 설정할건지 그런거 확인해서 show()같은 메소드로 보내주는 곳이랄까?

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
}
