package DAO;

import Utils.DBUtils;
import com.alibaba.fastjson.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class messageDAO {

    Connection con;
    Statement stmt;
    ResultSet rs;
    public int Insertmessage(Integer userID1,Integer userID2,int timestamp,String message){
        int ret=-1;
        con= DBUtils.getConnection();
        try {
            stmt = con.createStatement();
            String sql = "INSERT INTO message VALUES ("+userID1.toString()+","+userID2.toString()+","+Integer.toString(timestamp)+",\""+message+"\")";
            ret = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBUtils.close_all(con,stmt,rs);
        return ret;
    }
}
