package org.ss.services;

import org.ss.Logger;
import org.ss.entity.Member;

public class MemberService {
    private final Member member = Member.getInstance();
    private final String pepper = "I am a Pepper";

    public void user(String action, String id, String password){
        boolean acIsEmpty = action.isEmpty();
        boolean idIsEmpty = id.isEmpty();
        boolean pIsEmpty = password.isEmpty();

        if(acIsEmpty || idIsEmpty || pIsEmpty){
            return;
        }

        if(action.equals("login")){
            login(id, password);
        } else if(action.equals("signup")){
            signUp(id, password);
        } else Logger.error(action + " is not a valid action"); // 잘못된 명령어
    }


    public void login(String id, String password) {
        password = hash(password);
    }
    public void signUp(String id, String password){
        password = hash(password);
    }

    private String hash(String password) {
        return "";
    }
}
