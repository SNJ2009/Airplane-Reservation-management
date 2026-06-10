package org.ss.dao;

import org.jetbrains.annotations.NotNull;
import org.ss.common.ConsoleView;
import org.ss.db.DBUtil;
import org.ss.entity.Schedule;
import org.ss.entity.Seat;

import java.sql.ResultSet;

public class SeatDAO {
    private static final ScheduleDAO scheduleDAO = new ScheduleDAO();
    public void save(@NotNull Seat seat){
        String sql = "INSERT INTO seat(schedule_id, seat_number, is_booked) VALUES (?, ?, ?)";

        Schedule schedule = scheduleDAO.findById(seat.getScheduleId());
        if(schedule == null) throw new RuntimeException("Schedule not found");

        DBUtil.executeUpdate(
                sql,
                seat.getScheduleId(),
                seat.getSeatNumber(),
                seat.isBooked()
        );
    }

    public Seat findById(int id){
        String sql = "";

        return DBUtil.executeQueryForObject(sql, this::mapToRow, id);
    }

    private Seat mapToRow(ResultSet rs) {
        try{
            return new Seat(
                    rs.getInt("id"),
                    rs.getInt("schedule_id"),
                    rs.getString("seat_number"),
                    rs.getBoolean("is_booked")
            );
        } catch(Exception e){
            ConsoleView.error("seat not found");
            return null;
        }
    }
}
