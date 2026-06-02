package org.ss.services;

import org.ss.Logger;

public class MemberService {
    private String username;
    private String hashedPassword;


    public void user(String action, String username, String password){
        if(username.isEmpty() && password.isEmpty()){
            return;
        } else {
            this.username = username;
            this.hashedPassword = hash(password);
        }

        if(action.equals("login")){
            login();
        } else if(action.equals("signup")){
            signUp();
        } else Logger.error(username + " is not a valid action"); // 잘못된 명령어
    }


    public void login() {

    }
    public void signUp(){

    }

    private String hash(String password) {
        return "";
    }
}
