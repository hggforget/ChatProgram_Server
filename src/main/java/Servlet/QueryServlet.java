package Servlet;

import Utils.DBUtils;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "QueryServlet", value = "/QueryServlet")
public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        Connection con= DBUtils.getConnection();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT friendID FROM friendlist ";
            ResultSet rs = stmt.executeQuery(sql);
            JSONObject object = new JSONObject();
            List<String>idlist=new ArrayList<String>();
            while(rs.next()){
                Integer id  = rs.getInt("friendID");
                idlist.add(id.toString());
            }
            rs.close();
            object.put("friends",idlist);
            PrintWriter out = response.getWriter();
            //out.write(json);
            out.print(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
