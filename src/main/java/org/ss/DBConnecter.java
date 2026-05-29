package org.ss;

import java.sql.Connection;

public class DBConnecter {
    private Connection connection;
    private static final DBConnecter instance = new DBConnecter();

    private DBConnecter(){
        try {
            // db.properties 에 있는 url, user, pwd 가져와서 해주고
            // Connection 에따 저거 넣어주고
        } catch (Exception e) {

        }
    }

    public static DBConnecter getInstance(){ // 필요할려나
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
