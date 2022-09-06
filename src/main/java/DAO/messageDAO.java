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
    public int Insertmessage(Integer userID1,Integer userID2,Integer timestamp,String message){
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

    public int InsertGroupmessage(Integer userID,Integer groupID,Integer timestamp,String message){
        int ret=-1;
        con= DBUtils.getConnection();
        try {
            stmt = con.createStatement();
            String sql = "INSERT INTO messageGroup VALUES ("+userID.toString()+","+groupID.toString()+","+Integer.toString(timestamp)+",\""+message+"\")";
            ret = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBUtils.close_all(con,stmt,rs);
        return ret;
    }

    public JSONObject queryConversation(Integer userID1,Integer userID2){
        List<Integer>from=new ArrayList<Integer>();
        List<Integer>time=new ArrayList<Integer>();
        List<String>msg=new ArrayList<String>();
        JSONObject object=new JSONObject();
        con= DBUtils.getConnection();
        try {
            stmt = con.createStatement();
            String sql = "SELECT * from message where( userID1="+userID1.toString()
                    +" and userID2="+userID2.toString()+" ) or (userID1="+userID2.toString()+" and userID2="+userID1.toString()+") order by timestamp ";
            rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                from.add(Integer.valueOf(rs.getString("userID1")));
                time.add(Integer.valueOf(rs.getString("timestamp")));
                msg.add(rs.getString("message"));
            }
            object.put("from",from);
            object.put("time",time);
            object.put("msg",msg);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBUtils.close_all(con,stmt,rs);
        return object;
    }

    public JSONObject queryGroupMsg(Integer groupID){
        List<Integer>from=new ArrayList<Integer>();
        List<Integer>time=new ArrayList<Integer>();
        List<String>msg=new ArrayList<String>();
        JSONObject object=new JSONObject();
        con= DBUtils.getConnection();
        try {
            stmt = con.createStatement();
            String sql = "SELECT * from messageGroup where groupID="+groupID.toString()+" order by timestamp";
            rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                from.add(Integer.valueOf(rs.getString("userID")));
                time.add(Integer.valueOf(rs.getString("timestamp")));
                msg.add(rs.getString("message"));
            }
            object.put("from",from);
            object.put("time",time);
            object.put("msg",msg);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBUtils.close_all(con,stmt,rs);
        return object;
    }
}
