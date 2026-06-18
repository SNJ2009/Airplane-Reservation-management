package org.ss.dao;

import org.ss.common.ConsoleView;
import org.ss.db.DBUtil;
import org.ss.entity.Schedule;
import org.ss.entity.Seat;

import java.sql.ResultSet;

public class SeatDAO {
    private static final ScheduleDAO scheduleDAO = new ScheduleDAO();
    public void save(Seat seat){
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
    public int updateBooked(int scheduleId, int seatNumber, boolean currentStatus, boolean newStatus){
        String sql = "UPDATE seat SET is_booked = ? WHERE schedule_id = ? AND seat_number = ? AND is_booked = ?;";

        return DBUtil.executeUpdateN(
                sql,
                currentStatus,
                scheduleId,
                seatNumber,
                newStatus
        );
    }

    private Seat mapToRow(ResultSet rs) {
        try{
            return new Seat(
                    rs.getInt("id"),
                    rs.getInt("schedule_id"),
                    rs.getInt("seat_number"),
                    rs.getBoolean("is_booked")
            );
        } catch(Exception e){
            ConsoleView.error("seat not found");
            return null;
        }
    }
}
