package org.ss.db;

import org.ss.common.ConsoleView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {
    private static final Connection conn = DBConnector.getInstance().getConnection();

    /**
     * 매개변수로 받은 sql문과 파람을 이용해 sql 문 ?에 값 삽입, 실행 후 PK 반환(INSERT)
     * 또는 DELETE에서 사용
     *
     * @param sql sql문
     * @param params DB에 저장할 데이터
     * @return 실행 후, 생긴? 나온? 생성된? 암튼 PK값 받아서 반환
     */
    public static int executeUpdate(String sql, Object...params){
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // PK
                }
            }
        } catch (SQLException e) {
            ConsoleView.error("필수 입력 정보가 누락되었거나 유효하지 않습니다");
        }
        return -1;
    }
    public static int executeUpdateN(String sql, Object...params){
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            return ps.executeUpdate(); // 수정된 행 개수

        } catch (SQLException e) {
            ConsoleView.error("필수 입력 정보가 누락되었거나 유효하지 않습니다");
            return -1;
        }
    }

    public static <T> T executeQueryForObject(String sql, RowMapper<T> rm, Object... params) { // 하나만 조회
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rm.mapRow(rs);
                }
            }
        } catch (SQLException e) {
            ConsoleView.error("조회 요청 정보가 올바르지 않습니다");
        }
        return null;
    }

    public static <T> List<T> executeQueryForList(String sql, RowMapper<T> rm, Object... params) { // 여러 개 조회
        List<T> list = new ArrayList<>(); // 결과 담아둘 리스트 생성

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rm.mapRow(rs)); // 리스트에 담기
                }
            }
        } catch (SQLException e) {
            ConsoleView.error("조회 요청 정보가 올바르지 않습니다");
        }
        return list;
    }

    // 데이터 조립용
    public interface RowMapper<T> {
        T mapRow(ResultSet rs) throws SQLException;
    }
}
