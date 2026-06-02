package org.ss.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Member {
    private String id;
    private String name;
    private String password;
    private String phone;
    private boolean isManager;
    private String salt;

    private static Member member = null;
    public static Member getInstance() { return member; }

    public void toEntity(String id, String password) {
        generateMember();
        this.id = id;
        this.password = password;
    }

    public void generateMember(){
        member = new Member();
    }
}
