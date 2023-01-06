package DAO;

import Utils.DBUtils;
import com.alibaba.fastjson.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class userDAO {
    Connection con;
    Statement stmt;
    ResultSet rs;
    public String QueryNamebyuserID(Integer userID){
        con=DBUtils.getConnection();
        String ret=null;
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT userName FROM user where userID="+userID.toString();
            rs = stmt.executeQuery(sql);
            rs.last(); // 游标移到最后, 获得rs长度
            int length = rs.getRow();
            rs.first(); // 还原游标到rs开头
            if(length==1)
            {
                ret = rs.getString("userName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBUtils.close_all(con,stmt,rs);
        return ret;
    }
    public JSONObject QueryDatabyuserID(Integer userID){
        con=DBUtils.getConnection();
        JSONObject ret=null;
        try {
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT userName,img FROM user where userID="+userID.toString();
            rs = stmt.executeQuery(sql);
            rs.last(); // 游标移到最后, 获得rs长度
            int length = rs.getRow();
            rs.first(); // 还原游标到rs开头
            if(length==1)
            {
                String userName = rs.getString("userName");
                String img=rs.getString("img");
                ret=new JSONObject();
                ret.put("userName",userName);
                ret.put("img",img);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBUtils.close_all(con,stmt,rs);
        return ret;
    }
    public JSONObject QueryAlluser(){
        con=DBUtils.getConnection();
        JSONObject ret=null;
        try {
            stmt = con.createStatement();
            String sql = "SELECT userID,userName,img FROM user";
            rs = stmt.executeQuery(sql);
            List<String> idlist=new ArrayList<String>();
            List<String> namelist=new ArrayList<String>();
            List<String> imglist=new ArrayList<String>();
            while (rs.next())
            {
                String name=rs.getString("userName");
                String id=rs.getString("userID");
                String img=rs.getString("img");
                //System.out.println(name+"    "+id);
                namelist.add(name);
                idlist.add(id);
                imglist.add(img);
            }
            ret=new JSONObject();
            ret.put("idlist",idlist);
            ret.put("namelist",namelist);
            ret.put("imglist",imglist);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBUtils.close_all(con,stmt,rs);
        return ret;
    }

    public int AddUser(String userName,String img,String pwd){
        con=DBUtils.getConnection();
        int ret=-1;
        try {
            stmt = con.createStatement();
            String sql = "INSERT INTO user(userName,Password,img) VALUES(\""+userName+"\",\""+pwd+"\",\""+img+"\")";
            ret = stmt.executeUpdate(sql);
            sql = "SELECT @@IDENTITY";
            rs =stmt.executeQuery(sql);
            while (rs.next())
            {
                ret= rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBUtils.close_all(con,stmt,rs);
        return ret;
    }
}
