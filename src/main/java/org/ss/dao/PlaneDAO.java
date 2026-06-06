package org.ss.dao;

import lombok.SneakyThrows;
import org.ss.common.DBUtil;
import org.ss.entity.Plane;

import java.util.ArrayList;
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
    public List<Plane> getPlaneList(){
        String sql = "SELECT * FROM plane";

        return DBUtil.executeQueryForList(sql, rs -> {
            Plane plane = new Plane();

            plane.setId(rs.getInt("id"));
            plane.setAirline(rs.getString("airline"));
            plane.setModel(rs.getString("model"));
            plane.setMaxSeat(rs.getInt("max_seat"));

            return plane;
        });
    }
}
