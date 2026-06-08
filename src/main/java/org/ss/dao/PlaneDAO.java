package org.ss.dao;

import lombok.SneakyThrows;
import org.ss.common.ConsoleView;
import org.ss.db.DBUtil;
import org.ss.entity.Plane;

import java.sql.ResultSet;
import java.util.List;

public class PlaneDAO {
    public void save(Plane plane){
        String sql = "INSERT INTO plane(airline, model, max_seat) VALUES (?, ?, ?)";

        DBUtil.executeUpdate(
                sql,
                plane.getAirline(),
                plane.getModel(),
                plane.getMaxSeat()
        );
    }
    public void remove(int id) {
        String sql = "DELETE FROM plane WHERE id = ?";

        DBUtil.executeUpdate(sql, id);
    }

    public Plane findById(int id){
        String sql = "SELECT * FROM plane WHERE id = ?";

        return DBUtil.executeQueryForObject(sql, this::mapToRow, id);
    }
    public List<Plane> getList(){
        String sql = "SELECT * FROM plane";

        return DBUtil.executeQueryForList(sql, this::mapToRow);
    }
    public List<Plane> getList(String airline, String model){
        String sql = "SELECT * FROM plane WHERE airline = ? AND model = ?";

        return DBUtil.executeQueryForList(sql, this::mapToRow,  airline, model);
    }

    private Plane mapToRow(ResultSet rs){
        try{
            return new Plane(
                    rs.getInt("id"),
                    rs.getString("airline"),
                    rs.getString("model"),
                    rs.getInt("max_seat")
            );
        } catch(Exception e){
            ConsoleView.error("데이터를 불러오는 중 오류가 발생했습니다.");
            return null;
        }
    }
}
