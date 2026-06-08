package org.ss.db;

import lombok.SneakyThrows;
import org.ss.common.ConsoleView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {
    private static final Connection conn = DBConnector.getInstance().getConnection();

    public static void executeUpdate(String sql, Object...params){
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            ConsoleView.error("필수 입력 정보가 누락되었거나 유효하지 않습니다");
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
