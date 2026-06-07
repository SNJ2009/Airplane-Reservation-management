package org.ss.dao;


import lombok.*;
import org.ss.db.DBUtil;
import org.ss.entity.Member;

public class MemberDAO {
    @SneakyThrows
    public void save(Member member){
        String sql = "INSERT INTO user VALUES (?, ?, ?, ?, ?, ?)";

        DBUtil.executeUpdate(
                sql,
                member.getId(),
                member.getName(),
                member.getPassword(),
                member.getPhone(),
                member.isManager(),
                member.getSalt()
        );
    }

    @SneakyThrows
    public Member findById(String id){
        String sql = "SELECT * FROM user WHERE id = ?";

        return DBUtil.executeQueryForObject(sql, rs -> {
            Member member = Member.getInstance();

            member.setId(rs.getString("id"));
            member.setName(rs.getString("name"));
            member.setPassword(rs.getString("password"));
            member.setPhone(rs.getString("phone"));
            member.setManager(rs.getBoolean("isManager"));
            member.setSalt(rs.getString("salt"));

            return member;
        }, id);
    }

    // CRUD : Completed(CR) NOT YET (UD)
}
