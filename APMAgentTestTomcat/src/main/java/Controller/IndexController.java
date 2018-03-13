package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.Map;

@Controller
@ResponseBody
public class IndexController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request, Map<String, Object> model) {
        Jedis jedis = new Jedis("localhost");
        System.out.println("服务正在运行: " + jedis.ping());
        jedis.set("key", "" + System.currentTimeMillis());
        test1(jedis);
        System.out.println("value of key:" + jedis.get("key"));
        return "index";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(HttpServletRequest request, Map<String, Object> model) {
        return "test";
    }

    private void test1(Jedis jedis){
        jedis.set("key", "" + System.currentTimeMillis());
        System.out.println("value of key:" + jedis.get("key"));

        testMysql();
    }

    public static void testMysql() {
        Connection con = null;
        Statement stmt = null;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/blog", "root", "root");
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from `t_admin`");
            while (rs.next()) {
                long id = rs.getLong("id");
            }

            int r = stmt.executeUpdate("update `t_admin` set username = 'name' where id = 1");

            PreparedStatement pstmt = con.prepareStatement("update `t_admin` set username = ? where id = 1");
            pstmt.setString(1, "admin");
            int rowcount = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ignored) {
            }
        }
    }
}
