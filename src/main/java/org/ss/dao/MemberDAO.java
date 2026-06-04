package org.ss.dao;


import lombok.*;
import org.ss.DBConnector;
import org.ss.entity.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberDAO {
    @SneakyThrows
    public void save(Member member){
        String sql = "INSERT INTO user VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = DBConnector.getInstance().getConnection();

        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, member.getId());
            ps.setString(2, member.getName());
            ps.setString(3, member.getPassword());
            ps.setString(4, member.getPhone());
            ps.setBoolean(5, member.isManager());
            ps.setString(6, member.getSalt());

            ps.executeUpdate();
        }

        conn.close();
    }

    @SneakyThrows
    public Member findById(String id){
        String sql = "SELECT * FROM user WHERE id = ?";
        Connection conn = DBConnector.getInstance().getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()){
                Member member = Member.getInstance();

                member.setId(rs.getString("id"));
                member.setName(rs.getString("name"));
                member.setPassword(rs.getString("password"));
                member.setPhone(rs.getString("phone"));
                member.setManager(rs.getBoolean("isManager"));
                member.setSalt(rs.getString("salt"));

                conn.close();
                return member;
            }
        }
    }

    // CRUD : Completed(CR) NOT YET (UD)
}
