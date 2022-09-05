package DAO;

import Utils.DBUtils;
import com.alibaba.fastjson.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class membersDAO {

    Connection con;
    Statement stmt;
    ResultSet rs;
    public int addRel(Integer groupID,Integer userID){
        int ret=-1;
        con= DBUtils.getConnection();
        try {
            stmt = con.createStatement();
            String sql = "INSERT INTO members VALUES ("+groupID.toString()+","+userID.toString()+")";
            ret = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBUtils.close_all(con,stmt,rs);
        return ret;
    }
    public JSONObject queryGroup(Integer userID){
        JSONObject ret=null;
        con= DBUtils.getConnection();
        try {
            stmt = con.createStatement();
            String sql = "SELECT groupID FROM members where userID="+userID.toString();
            rs = stmt.executeQuery(sql);
            List<String> groupIDlist=new ArrayList<String>();
            while(rs.next()){
                Integer id  = rs.getInt("groupID");
                groupIDlist.add(id.toString());
            }
            ret=new JSONObject();
            ret.put("groupIDlist",groupIDlist);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBUtils.close_all(con,stmt,rs);
        return ret;
    }

    public List<String> queryMembers(Integer groupID){
        List<String>ret=new ArrayList<String>();
        con= DBUtils.getConnection();
        try {
            stmt = con.createStatement();
            String sql = "SELECT userID FROM members where groupID="+groupID.toString();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                ret.add(rs.getString("userID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBUtils.close_all(con,stmt,rs);
        return ret;
    }
}
