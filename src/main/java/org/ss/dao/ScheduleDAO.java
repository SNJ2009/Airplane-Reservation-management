package org.ss.dao;

import lombok.SneakyThrows;
import org.ss.db.DBUtil;
import org.ss.entity.Schedule;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

public class ScheduleDAO {
    @SneakyThrows
    public void save(Schedule schedule){
        String sql = "INSERT INTO schedule (plane_id, departure, destination, run_time, flight_time) VALUES (?, ?, ?, ?, ?)";

        DBUtil.executeUpdate(
                sql,
                schedule.getPlaneId(),
                schedule.getDeparture(),
                schedule.getDestination(),
                schedule.getDepartureTime(),
                schedule.getFlightTime()
        );
    }

    @SneakyThrows
    public Schedule findById(int id){
        String sql =
                "SELECT s.*, p.airline, p.model " +
                "FROM schedule s " +
                "JOIN plane p ON s.plane_id = p.id " +
                "WHERE s.id = ?";

        return DBUtil.executeQueryForObject( sql, this::mapToRow, id );
    }
    @SneakyThrows
    public List<Schedule> getScheduleList(){ // 전체조회
        String sql =
                "SELECT s.*, p.airline, p.model " +
                "FROM schedule s " +
                "JOIN plane p ON s.plane_id = p.id";

        return DBUtil.executeQueryForList(sql, this::mapToRow);
    }
    @SneakyThrows
    public List<Schedule> getScheduleList(String departure, String destination){ // 출발지, 도착지 필터링
        String sql =
                "SELECT s.*, p.airline, p.model " +
                "FROM schedule s " +
                "JOIN plane p ON s.plane_id = p.id " +
                "WHERE s.departure = ? AND s.destination = ?";

        return DBUtil.executeQueryForList(sql, this::mapToRow, departure, destination);
    }

    @SneakyThrows
    private Schedule mapToRow(ResultSet rs) {
        return new Schedule(
                rs.getInt("id"),
                rs.getInt("plane_id"),
                rs.getString("departure"),
                rs.getString("destination"),
                rs.getObject("run_time", LocalDateTime.class),
                rs.getInt("flight_time"),
                rs.getString("airline") + " (" + rs.getString("model") + ")"
        );
    }
}
