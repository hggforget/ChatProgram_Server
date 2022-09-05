package Utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

public class DBUtils {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Context context = new InitialContext();

            //使用jndi技术，Tomcat往往会在name值面前加上java:comp/env/jdbc
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/mysql"); //查找数据源
            conn = dataSource.getConnection();//获取连接
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("conn==null?" + (conn == null)); //打印输出，是否获取到连接对象。
        return conn;
    }

    //关闭连接方法
    public static void close_all(Connection conn, Statement stmt, ResultSet rs) {
        close_con(conn);
        close_stmt(stmt);
        close_rs(rs);
    }

    public static void close_con(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    public static void close_stmt(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    public static void close_rs(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
