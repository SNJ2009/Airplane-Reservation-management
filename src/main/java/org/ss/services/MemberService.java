package org.ss.services;

import org.ss.controller.Command;
import org.ss.common.ConsoleView;
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

    public void user(String[] cmd){
        if(Command.isInvalidLength(cmd, 3, 7)) return;
        String action = cmd[2];

        if(action.isEmpty()) return;

        if(action.equals("login")){
            if(Command.isInvalidLength(cmd, 5)) return;

            String id = cmd[3];
            String password = cmd[4];

            login(id, password);

        } else if(action.equals("signup")){
            if(Command.isInvalidLength(cmd, 7)) return;

            String id = cmd[3];
            String name = cmd[4];
            String password = cmd[5];
            String phone = cmd[6];

            signUp(id, name, password, phone);

        } else ConsoleView.error(action + " is not a valid action"); // 잘못된 명령어
    }


    public void login(String id, String userInputPassword) {
        if (checkIdPwdEmpty(id, userInputPassword)) return;

        Member memberDAOById = memberDAO.findById(id);
        if(memberDAOById == null){ // db에 있는 id인지 (없으면 return)
            ConsoleView.error("Login Failed : User not found");
            return;
        }
        this.member.setSalt(memberDAOById.getSalt());

        String password = memberDAOById.getPassword();
        userInputPassword = hash(userInputPassword);

        if(password.equals(userInputPassword)){
            ConsoleView.info("Login Successful");

            this.member.setId(memberDAOById.getId());
            this.member.setName(memberDAOById.getName());
            this.member.setPassword(memberDAOById.getPassword());
            this.member.setPhone(memberDAOById.getPhone());
            this.member.setManager(memberDAOById.isManager());
        } else {
            ConsoleView.error("Login Failed");

            this.member.setSalt(null);
        }
    }

    public void signUp(String id, String name, String password, String phone) {
        if(checkIdPwdEmpty(id, password)) return;

        Member memberDAOById = memberDAO.findById(id);
        if(memberDAOById != null){ // db에 있는 id인지 (있으면 return)
            ConsoleView.error("SignUp Failed : Already Exists");
            return;
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
        ConsoleView.info("SignUp Successful! Welcome, " + id);
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
            ConsoleView.error("Failed to hash password");
        } return null;
    }

    private String generateSalt() { // 솔트값 생성
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    /**
     * id와 비밀번호가 null이 아닌지 검사 <br>
     * null이라면 "id or password is empty" 출력
     *
     * @param id 입력받은 아이디
     * @param pwd 입력받은 비밀번호
     * @return 이이디 또는 비밀번호 둥 중 하나라도 {@code null}이라면 {@code true} 반환
     */
    public boolean checkIdPwdEmpty(String id, String pwd){
        if(id.isEmpty() || pwd.isEmpty()){
            ConsoleView.error("id or password is empty");
            return true;
        }
        return false;
    }
}
