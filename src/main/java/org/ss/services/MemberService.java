package org.ss.services;

import org.ss.Logger;
import org.ss.dao.MemberDAO;
import org.ss.entity.Member;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class MemberService {
    private final Member member = Member.getInstance();
    private final MemberDAO memberDAO = new MemberDAO();
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


    public void login(String id, String userInputPassword) {
        if (checkIdPwdEmpty(id, userInputPassword)) return;

        Member memberDAOById = memberDAO.findById(id);
        if(memberDAOById == null){ // db에 있는 id인지 (없으면 return)
            Logger.error("Login Failed : User not found");
            return;
        }

        String password = memberDAOById.getPassword();
        userInputPassword = hash(userInputPassword);

        if(password.equals(userInputPassword)){
            Logger.info("Login Successful");
        } else {
            Logger.error("Login Failed");
        }
    }

    public void signUp(String id, String password) {
        if(checkIdPwdEmpty(id, password)) return;

        Member memberDAOById = memberDAO.findById(id);
        if(memberDAOById != null){ // db에 있는 id인지 (있으면 return)
            Logger.error("SignUp Failed : Already Exists");
            return;
        }
        member.setId(id);

        String newSalt = generateSalt();
        member.setSalt(newSalt);

        String hashedPassword = hash(password);
        member.setPassword(hashedPassword);

        // DB 저장
        memberDAO.save(member);
        Logger.info("SignUp Successful! Welcome, " + id);
    }

    private String hash(String password) {
        String salt = member.getSalt();
        password += salt + pepper;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashB = md.digest(password.getBytes());
            password = Base64.getEncoder().encodeToString(hashB);// hashBytes 배열 보기 편하게
            //System.out.println("hashed password : "+pwd); // 확인용

            return password;
        } catch (NoSuchAlgorithmException e){
            Logger.error("Failed to hash password");
        } return null;
    }

    private String generateSalt() { // 솔트값 생성
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    public boolean checkIdPwdEmpty(String id, String pwd){
        if(id.isEmpty() || pwd.isEmpty()){
            Logger.error("id or password is empty");
            return true;
        }
        return false;
    }
}
