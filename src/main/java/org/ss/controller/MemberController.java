package org.ss.controller;

import org.ss.common.ConsoleView;
import org.ss.dao.MemberDAO;
import org.ss.entity.Member;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.Properties;

public class MemberController {
    private final Member member = Member.getInstance();
    private final MemberDAO memberDAO = new MemberDAO();
    private static String pepper;

    public MemberController() {
        try {
            Properties props = new Properties();
            try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
                if (input == null) {
                    throw new RuntimeException("db.properties 파일을 찾을 수 없습니다.");
                }
                props.load(input);
            }

            // pepper 가져오기
            this.pepper = props.getProperty("crypto.pepper");

        } catch (Exception e){
            ConsoleView.error(e.getMessage());
        }
    }

    public void user(String[] cmd){
        Command.validLength(cmd, 3, 7);
        String action = cmd[2];

        if(action.isEmpty()) return;

        if(action.equals("login")){
            Command.validLength(cmd, 5);

            String id = cmd[3];
            String password = cmd[4];

            login(id, password);

        } else if(action.equals("signup")){
            Command.validLength(cmd, 7);

            String id = cmd[3];
            String name = cmd[4];
            String password = cmd[5];
            String phone = cmd[6];

            signUp(id, name, password, phone);

        } else throw new IllegalArgumentException(action + " is not a valid action"); // 잘못된 명령어
    }


    public void login(String id, String userInputPassword) {
        if (ConsoleView.checkEmpty(id, userInputPassword)) return;

        Member memberDAOById = memberDAO.findById(id);
        if(memberDAOById == null){ // db에 있는 id인지 (없으면 return)
            throw new NoSuchElementException("Login Failed : User not found");
        }
        this.member.setSalt(memberDAOById.getSalt());

        String password = memberDAOById.getPassword();
        userInputPassword = hash(userInputPassword);

        if(password.equals(userInputPassword)){
            ConsoleView.successful();

            this.member.setId(memberDAOById.getId());
            this.member.setName(memberDAOById.getName());
            this.member.setPassword(memberDAOById.getPassword());
            this.member.setPhone(memberDAOById.getPhone());
            this.member.setManager(memberDAOById.isManager());
        } else {
            this.member.setId(null);
            this.member.setName("");
            this.member.setPassword("");
            this.member.setPhone("");
            this.member.setManager(false);
            this.member.setSalt(null);

            throw new IllegalArgumentException("Login Failed : Incorrect password Or Id");
        }
    }

    public void signUp(String id, String name, String password, String phone) {
        if(ConsoleView.checkEmpty(id, password)) return;

        Member memberDAOById = memberDAO.findById(id);
        if(memberDAOById != null){ // db에 있는 id인지 (있으면 return)
            throw new IllegalStateException("SignUp Failed : Already Exists");
        }

        String newSalt = generateSalt();
        member.setSalt(newSalt);

        String hashedPassword = hash(password);

        member.setId(id);
        member.setName(name);
        member.setPassword(hashedPassword);
        member.setPhone(phone);
        member.setManager(false);

        // DB 저장
        memberDAO.save(member);
        ConsoleView.successful();
    }

    private String hash(String password) {
        String salt = member.getSalt();
        password += salt + pepper;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashB = md.digest(password.getBytes());
            password = Base64.getEncoder().encodeToString(hashB);

            return password;
        } catch (NoSuchAlgorithmException e){
            ConsoleView.error("Failed to hash password");
        } return null;
    }

    private String generateSalt() { // 솔트값 생성
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }
}
