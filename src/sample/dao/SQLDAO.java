package sample.dao;
import java.sql.*;

public class SQLDAO {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/EMP";

    //  Database credentials
    static final String USER = "username";
    static final String PASS = "password";

    public Connection connect(){
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            return conn;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



//    public static void connect(){
//        Connection conn = null;
//        PreparedStatement stmt = null;
//
//        try {
//            Class.forName(JDBC_DRIVER);
//            System.out.println("Connecting to database...");
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
//            stmt = conn.prepareStatement();
//
//            stmt.exe
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//
//        //STEP 4: Execute a query
//        System.out.println("Creating statement...");
//        stmt = conn.createStatement();
//        String sql;
//        sql = "SELECT id, first, last, age FROM Employees";
//        ResultSet rs = stmt.executeQuery(sql);

}


