package org.ss.db;

import org.ss.common.ConsoleView;

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
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pwd = props.getProperty("db.password");

            this.connection = DriverManager.getConnection(url, user, pwd);
        } catch (Exception e) {
            ConsoleView.error("DB Connection Fail : " + e);
        }
    }

    public static DBConnector getInstance(){ // singleton
        return instance;
    }

    static{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            ConsoleView.error("JDBC Driver Not Found [Please Restart]");
        }
    }

    public Connection getConnection(){
        return connection;
    }
}
