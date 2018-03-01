package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
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
    }

}
