package org.ss.dao;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.ss.common.ConsoleView;
import org.ss.db.DBUtil;
import org.ss.entity.Plane;
import org.ss.entity.Schedule;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

public class ScheduleDAO {
    private static final PlaneDAO planeDAO = new PlaneDAO();

    public void save(@NotNull Schedule schedule){
        String sql = "INSERT INTO schedule (plane_id, departure, arrival, run_time, flight_time) VALUES (?, ?, ?, ?, ?)";

        Plane plane = planeDAO.findById(schedule.getPlaneId());
        if(plane == null) throw new RuntimeException("Plane not found");

        DBUtil.executeUpdate(
                sql,
                schedule.getPlaneId(),
                schedule.getDeparture(),
                schedule.getArrival(),
                schedule.getDepartureTime(),
                schedule.getFlightTime()
        );
    }

    public Schedule findById(int id){
        String sql =
                "SELECT s.*, p.airline, p.model " +
                "FROM schedule s " +
                "JOIN plane p ON s.plane_id = p.id " +
                "WHERE s.id = ?";

        return DBUtil.executeQueryForObject( sql, this::mapToRow, id );
    }
    public List<Schedule> getScheduleList(){ // 전체조회
        String sql =
                "SELECT s.*, p.airline, p.model " +
                "FROM schedule s " +
                "JOIN plane p ON s.plane_id = p.id";

        return DBUtil.executeQueryForList(sql, this::mapToRow);
    }
    public List<Schedule> getScheduleList(String departure, String arrival){ // 출발지, 도착지 필터링
        String sql =
                "SELECT s.*, p.airline, p.model " +
                "FROM schedule s " +
                "JOIN plane p ON s.plane_id = p.id " +
                "WHERE s.departure = ? AND s.arrival = ?";

        return DBUtil.executeQueryForList(sql, this::mapToRow, departure, arrival);
    }

    public void remove(int id) {
        String sql = "DELETE FROM schedule WHERE id = ?";

        DBUtil.executeUpdate(sql, id);
    }

    private Schedule mapToRow(ResultSet rs) {
        try{
            return new Schedule(
                    rs.getInt("id"),
                    rs.getInt("plane_id"),
                    rs.getString("departure"),
                    rs.getString("arrival"),
                    rs.getObject("run_time", LocalDateTime.class),
                    rs.getInt("flight_time"),
                    rs.getString("airline") + " (" + rs.getString("model") + ")"
            );
        } catch (Exception e){
            ConsoleView.error("데이터를 불러오는 중 오류가 발생했습니다. 입력값을 확인 후 다시 시도해 주세요.");
            return null;
        }
    }
}
