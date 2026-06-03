package org.ss.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Member {
    private String id;
    private String name;
    private String password;
    private String phone;
    private boolean isManager;
    private String salt;

    private static Member member = new Member();

    public static Member getInstance() { // Member 클래스만 Singleton
        return member;
    }
}
