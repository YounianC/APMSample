package Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class IndexController {

    private static TreeMap<Integer, JSONObject> spanMap = new TreeMap<>(new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    });


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public String getData(HttpServletRequest request, Map<String, Object> model) {
        TreeMap<Integer, JSONObject> tmpMap = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        for (Map.Entry<Integer, JSONObject> entry : spanMap.entrySet()) {
            tmpMap.put(entry.getKey(),JSONObject.parseObject(entry.getValue().toJSONString()));
        }
        ///////copy end

        JSONArray roots = new JSONArray();
        for (JSONObject jsonObject : tmpMap.values()) {
            int parent = jsonObject.getIntValue("parentSpanId");

            JSONObject parentObj = tmpMap.get(parent);
            if (parentObj != null) {
                if (!parentObj.containsKey("children")) {
                    parentObj.put("children", new JSONArray());
                }
                parentObj.getJSONArray("children").add(jsonObject);
            }

            if (jsonObject.getIntValue("parentSpanId") == 0) {
                roots.add(jsonObject);
            }
        }

        return roots.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/uploadSpan", method = RequestMethod.POST)
    public void uploadSpan(HttpServletRequest request) {
        JSONObject object = JSONObject.parseObject(request.getParameter("span"));
        spanMap.put(object.getInteger("spanId"), object);
        System.out.println(object);
    }

    @ResponseBody
    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    public void clear(HttpServletRequest request) {
        spanMap.clear();
    }
}
