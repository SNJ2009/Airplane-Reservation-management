package org.ss.dao;

import org.ss.common.ConsoleView;
import org.ss.db.DBUtil;
import org.ss.entity.Schedule;
import org.ss.entity.Ticket;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

public class TicketDAO {
    public void save(Ticket ticket) {
        String sql = "INSERT INTO ticket(user_id, schedule_id, selected_seat) VALUES (?, ?, ?)";
        DBUtil.executeUpdate(
                sql,
                ticket.getUserId(),
                ticket.getScheduleId(),
                ticket.getSelectedSeat()
        );
    }
    public void delete(int id, String userId) {
        String sql = "DELETE FROM ticket WHERE id = ? AND user_id = ?";
        DBUtil.executeUpdate(sql, id, userId);
    }

    public Ticket findById(String userId) {
        String sql = "SELECT * FROM ticket WHERE user_id = ?"; // 이거 JOIN으로 바꿔야할듯

        return DBUtil.executeQueryForObject(sql, this::mapToRow, userId);
    }

    public List<Ticket> ticketList(String userId, int scheduleId) {
        String sql = "SELECT * FROM ticket WHERE user_id = ? AND schedule_id = ?";
        return DBUtil.executeQueryForList(sql, this::mapToRow, userId, scheduleId);
    }
    public List<Ticket> ticketList(String userId) {
        String sql = "SELECT * FROM ticket WHERE user_id = ?";
        return DBUtil.executeQueryForList(sql, this::mapToRow);
    }

    private Ticket mapToRow(ResultSet rs) {
        try{
            return new Ticket(
                    rs.getInt("id"),
                    rs.getString("user_id"),
                    rs.getInt("schedule_id"),
                    rs.getInt("selected_seat")
            );
        } catch (Exception e){
            ConsoleView.error("데이터를 불러오는 중 오류가 발생했습니다. 입력값을 확인 후 다시 시도해 주세요.");
            return null;
        }
    }
}
