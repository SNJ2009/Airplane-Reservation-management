package org.ss.dao;

import org.ss.common.ConsoleView;
import org.ss.db.DBUtil;
import org.ss.entity.Ticket;

import java.sql.ResultSet;
import java.util.List;

public class TicketDAO {
    public void save( Ticket ticket) {
        String sql = "INSERT INTO ticket(user_id, schedule_id, selected_seat) VALUES (?, ?, ?)";

        DBUtil.executeUpdate(
                sql,
                ticket.getUserId(),
                ticket.getScheduleId(),
                ticket.getSelectedSeat()
        );
    }
    public void delete(String userId, int id) {
        String sql = "DELETE FROM ticket WHERE id = ? AND user_id = ?";
        DBUtil.executeUpdate(sql, id, userId);
    }

    public Ticket findById(String userId) {
        String sql = "SELECT * FROM ticket WHERE user_id = ?"; // 이거 JOIN으로 바꿔야할듯

        return DBUtil.executeQueryForObject(sql, this::mapToRow, userId);
    }

    /**
     * 로그인한 유저의 ID를 기준으로 필터링 후 리스트 반환
     * @param userId 유저 ID
     * @return DB 조회 후, 조회 결과 리스트로 반환
     */
    public List<Ticket> ticketList(String userId) {
        String sql = "SELECT * FROM ticket WHERE user_id = ?";
        return DBUtil.executeQueryForList(sql, this::mapToRow, userId);
    }

    /**
     * 로그인한 유저 ID 기준으로 출발지와 도착지로 필터링 해 리스트로 반환
     * @param userId 유저 ID
     * @param departure 출발지
     * @param arrival 도착지
     * @return 조회 결과 리스트로 반환
     */
    public List<Ticket> ticketList(String userId, String departure, String arrival) {
        String sql = "SELECT * FROM ticket WHERE user_id = ? AND departure = ? AND arrival = ?";
        return DBUtil.executeQueryForList(sql, this::mapToRow, userId, departure, arrival);
    }

    /**
     * ID 기준 조회, 도착지로 필터링
     * @param userId 유저 ID
     * @param arrival 도착지
     * @return 리스트로 반환
     */
    public List<Ticket> ticketList(String userId, String arrival) {
        String sql = "SELECT * FROM ticket WHERE user_id = ? AND arrival = ?";
        return DBUtil.executeQueryForList(sql, this::mapToRow, userId, arrival);
    }

    /**
     * 유저 ID + 스케줄 ID로 조회
     * @param userId 유저 ID
     * @param scheduleId 스케줄 ID
     * @return 리스트로
     */
    public List<Ticket> ticketList(String userId, int scheduleId) {
        String sql = "SELECT * FROM ticket WHERE user_id = ? AND 여기 JOIN한 다음에 저거 그거 해야 그거 가능한데 그건 좀 그건가? 아 그건 또 아닌듯 암튼 조인해서 그거 해야 함 = ?";
        return DBUtil.executeQueryForList(sql, this::mapToRow, userId, scheduleId);
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
