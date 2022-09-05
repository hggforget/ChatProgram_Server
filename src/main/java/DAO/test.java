package DAO;

import Utils.DBUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test {
    public static void main(String[]args){
        Connection con=DBUtils.getConnection();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT userID FROM user";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int id  = rs.getInt("UserID");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
