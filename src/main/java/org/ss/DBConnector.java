package org.ss;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnector {
    private Connection connection;
    private static final DBConnector instance = new DBConnector();

    private DBConnector(){
        try {
            Properties props = new Properties();
            try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
                if (input == null) {
                    throw new RuntimeException("db.properties 파일을 찾을 수 없습니다.");
                }
                props.load(input);
            }

            // url, user, pwd 가져오기
            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String pwd = props.getProperty("pwd");

            this.connection = DriverManager.getConnection(url, user, pwd);

        } catch (Exception e) {
            Logger.error("DB Connection Fail : " + e);
        }
    }

    public static DBConnector getInstance(){ // singleton
        return instance;
    }

    static{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            Logger.error("JDBC Driver Not Found [Please Restart]");
        }
    }

    public Connection getConnection(){
        return connection;
    }
}
