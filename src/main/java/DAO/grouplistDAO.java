package DAO;

import Utils.DBUtils;
import com.alibaba.fastjson.JSONArray;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class grouplistDAO {

    Connection con;
    Statement stmt;
    ResultSet rs;
    public int createGroup(Integer userID, String groupName, String members){
        int ret=-1;
        Integer groupid=-1;
        List<String> memberlist= JSONArray.parseArray(members,String.class);
        con= DBUtils.getConnection();
        try {
            stmt = con.createStatement();
            String sql = "INSERT INTO grouplist(userID,groupName,members) VALUES ("+userID.toString()+",\""+groupName+"\",\""+"ss"+"\")";
            System.out.println(sql);
            ret = stmt.executeUpdate(sql);
            sql = "SELECT @@IDENTITY";
            rs =stmt.executeQuery(sql);
            while (rs.next())
            {
                groupid= rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DBUtils.close_all(con,stmt,rs);
        for (int i=0;i<memberlist.size();i++)
        {
            membersDAO membersdao=new membersDAO();
            int tmp=membersdao.addRel(groupid,Integer.valueOf(memberlist.get(i)));
            System.out.println("addRel:"+tmp);
        }
        return ret;
    }
    public String queryNamebyID(Integer groupID){
        String ret=null;
        con= DBUtils.getConnection();
        try {
            stmt = con.createStatement();
            String sql = "SELECT groupName FROM grouplist where groupID="+groupID.toString();
            System.out.println(sql);
            rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                ret=rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DBUtils.close_all(con,stmt,rs);
        return ret;
    }
}
