package org.ss.dao;

import lombok.SneakyThrows;
import org.ss.db.DBUtil;
import org.ss.entity.Plane;

import java.sql.ResultSet;
import java.util.List;

public class PlaneDAO {
    @SneakyThrows
    public void save(Plane plane){
        String sql = "INSERT INTO plane VALUES (?, ?, ?, ?)";

        DBUtil.executeUpdate(
                sql,
                plane.getId(),
                plane.getAirline(),
                plane.getModel(),
                plane.getMaxSeat()
        );
    }

    @SneakyThrows
    public Plane findById(int id){
        String sql = "SELECT * FROM plane WHERE id = ?";

        return DBUtil.executeQueryForObject(sql, this::mapToRow, id);
    }
    @SneakyThrows
    public List<Plane> getPlaneList(){
        String sql = "SELECT * FROM plane";

        return DBUtil.executeQueryForList(sql, this::mapToRow);
    }

    @SneakyThrows
    private Plane mapToRow(ResultSet rs){
        return new Plane(
                rs.getInt("id"),
                rs.getString("airline"),
                rs.getString("model"),
                rs.getInt("max_seat")
        );
    }
}
