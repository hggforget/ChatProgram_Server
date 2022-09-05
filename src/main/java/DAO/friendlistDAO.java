package DAO;

import Utils.DBUtils;
import com.alibaba.fastjson.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class friendlistDAO {

    Connection con;
    Statement stmt;
    ResultSet rs;
    public JSONObject QueryFriendListbyuserID(Integer userID){
        JSONObject ret=null;
        con= DBUtils.getConnection();
        try {
            stmt = con.createStatement();
            String sql = "SELECT friendID FROM friendlist where userID="+userID.toString();
            rs = stmt.executeQuery(sql);
            List<String> idlist=new ArrayList<String>();
            while(rs.next()){
                Integer id  = rs.getInt("friendID");
                idlist.add(id.toString());
            }
            ret=new JSONObject();
            ret.put("idlist",idlist);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBUtils.close_all(con,stmt,rs);
        return ret;
    }
    public int addFriend(Integer userID,Integer friendID){
        con= DBUtils.getConnection();
        int ret=-1;
        try {
            stmt = con.createStatement();
            String sql = "INSERT INTO friendlist VALUES ("+userID.toString()+","+friendID.toString()+")";
            System.out.println(sql);
            ret= stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBUtils.close_all(con,stmt,rs);
        return ret;
    }
}
