package org.ss.dao;

import org.ss.common.ConsoleView;
import org.ss.db.DBUtil;
import org.ss.entity.Schedule;
import org.ss.entity.Ticket;
import org.ss.entity.TicketDetail;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

public class TicketDAO {
    private static final ScheduleDAO scheduleDAO = new ScheduleDAO();

    public int save(Ticket ticket) {
        String sql = "INSERT INTO ticket(user_id, schedule_id, selected_seat) VALUES (?, ?, ?)";
//        ConsoleView.debugger(sql);
        int result = DBUtil.executeUpdate(
                sql,
                ticket.getUserId(),
                ticket.getScheduleId(),
                ticket.getSelectedSeat()
        );
//        ConsoleView.debugger(result);
        if(result == -1) throw new RuntimeException("Failed to save ticket");
        return result;
    }
    public void delete(int ticket_id, String userId) {
        String sql = "DELETE FROM ticket WHERE id = ? AND user_id = ?";
        DBUtil.executeUpdate(sql, ticket_id, userId);
    }

    public TicketDetail findById(int ticketId) {
        String sql = "SELECT * FROM ticket WHERE id = ?"; // 이거 JOIN으로 바꿔야할듯

        return DBUtil.executeQueryForObject(sql, this::mapToRow, ticketId);
    }
    public TicketDetail findByUserId(String userId) {
        String sql = "SELECT * FROM ticket WHERE user_id = ?"; // 이거 JOIN으로 바꿔야할듯

        return DBUtil.executeQueryForObject(sql, this::mapToRow, userId);
    }

    /**
     * 로그인한 유저의 ID를 기준으로 필터링 후 리스트 반환
     * @param userId 유저 ID
     * @return DB 조회 후, 조회 결과 리스트로 반환
     */
    public List<TicketDetail> ticketList(String userId) {
        String sql = "SELECT t.id, t.user_id, t.schedule_id, t.selected_seat, " +
                "s.id AS s_id, s.plane_id, s.departure, s.arrival, s.start_time, s.flight_time, " +
                "p.airline, p.model " +
                "FROM ticket t " +
                "JOIN schedule s ON t.schedule_id = s.id " +
                "JOIN plane p ON s.plane_id = p.id " +
                "WHERE t.user_id = ?";
        return DBUtil.executeQueryForList(sql, this::mapToRow, userId);
    }

    /**
     * 로그인한 유저 ID 기준으로 출발지와 도착지로 필터링 해 리스트로 반환
     * @param userId 유저 ID
     * @param departure 출발지
     * @param arrival 도착지
     * @return 조회 결과 리스트로 반환
     */
    public List<TicketDetail> ticketList(String userId, String departure, String arrival) {
        String sql = "SELECT t.id, t.user_id, t.schedule_id, t.selected_seat, " +
                "s.id AS s_id, s.plane_id, s.departure, s.arrival, s.start_time, s.flight_time, " +
                "p.airline, p.model " +
                "FROM ticket t " +
                "JOIN schedule s ON t.schedule_id = s.id " +
                "JOIN plane p ON s.plane_id = p.id " +
                "WHERE t.user_id = ? AND s.departure = ? AND s.arrival = ?";
        return DBUtil.executeQueryForList(sql, this::mapToRow, userId, departure, arrival);
    }

    /**
     * ID 기준 조회, 도착지로 필터링
     * @param userId 유저 ID
     * @param arrival 도착지
     * @return 리스트로 반환
     */
    public List<TicketDetail> ticketList(String userId, String arrival) {
        String sql = "SELECT t.id, t.user_id, t.schedule_id, t.selected_seat, " +
                "s.id AS s_id, s.plane_id, s.departure, s.arrival, s.start_time, s.flight_time, " +
                "p.airline, p.model " +
                "FROM ticket t " +
                "JOIN schedule s ON t.schedule_id = s.id " +
                "JOIN plane p ON s.plane_id = p.id " +
                "WHERE t.user_id = ? AND s.arrival = ?";
        return DBUtil.executeQueryForList(sql, this::mapToRow, userId, arrival);
    }

    /**
     * 유저 ID + 스케줄 ID로 조회
     * @param userId 유저 ID
     * @param scheduleId 스케줄 ID
     * @return 리스트로
     */
    public List<TicketDetail> ticketList(String userId, int scheduleId) {
        String sql = "SELECT t.id, t.user_id, t.schedule_id, t.selected_seat, " +
                "s.id AS s_id, s.plane_id, s.departure, s.arrival, s.start_time, s.flight_time, " +
                "p.airline, p.model " +
                "FROM ticket t " +
                "JOIN schedule s ON t.schedule_id = s.id " +
                "JOIN plane p ON s.plane_id = p.id " +
                "WHERE t.user_id = ? AND s.id = ?";
        return DBUtil.executeQueryForList(sql, this::mapToRow, userId, scheduleId);
    }

    private TicketDetail mapToRow(ResultSet rs) {
        try {
            return new TicketDetail(
                    new Ticket(
                            rs.getInt("id"),
                            rs.getString("user_id"),
                            rs.getInt("schedule_id"),
                            rs.getInt("selected_seat")
                    ),
                    new Schedule(
                            rs.getInt("s_id"),
                            rs.getInt("plane_id"),
                            rs.getString("departure"),
                            rs.getString("arrival"),
                            rs.getObject("start_time", LocalDateTime.class),
                            rs.getInt("flight_time"),
                            rs.getString("airline") + " (" +rs.getString("model") + ")"
                    )
            );
        } catch (Exception e) {
            ConsoleView.error("데이터를 불러오는 중 오류가 발생했습니다. 입력값을 확인 후 다시 시도해 주세요.");
            return null;
        }
    }
}
