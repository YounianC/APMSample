package Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class IndexController {

    private static TreeMap<String, Map<Integer, JSONObject>> spanMap = new TreeMap<>(new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    });


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public String getData(HttpServletRequest request, Map<String, Object> model) {
        JSONArray roots = new JSONArray();

        for (Map.Entry<String, Map<Integer, JSONObject>> entry : spanMap.entrySet()) {
            TreeMap<Integer, JSONObject> tmpMap = new TreeMap<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1 - o2;
                }
            });

            for (Map.Entry<Integer, JSONObject> span : entry.getValue().entrySet()) {
                tmpMap.put(span.getKey(), JSONObject.parseObject(span.getValue().toJSONString()));
            }
            ///////copy end

            for (JSONObject jsonObject : tmpMap.values()) {
                int parent = jsonObject.getIntValue("parentSpanId");

                JSONObject parentObj = tmpMap.get(parent);
                if (parentObj != null && parent != jsonObject.getIntValue("spanId")) {
                    if (!parentObj.containsKey("children")) {
                        parentObj.put("children", new JSONArray());
                    }
                    parentObj.getJSONArray("children").add(jsonObject);
                }

                if (jsonObject.getIntValue("parentSpanId") == 0) {
                    roots.add(jsonObject);
                }
            }
        }

        return roots.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/uploadSpan", method = RequestMethod.POST)
    public void uploadSpan(HttpServletRequest request) {
        JSONObject object = JSONObject.parseObject(request.getParameter("span"));

        String traceId = object.getString("traceId");
        if (!spanMap.containsKey(traceId)) {
            Map<Integer, JSONObject> map = new HashMap<>();
            spanMap.put(traceId, map);
        }

        spanMap.get(traceId).put(object.getInteger("spanId"), object);
        System.out.println(object);
    }

    @ResponseBody
    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    public void clear(HttpServletRequest request) {
        spanMap.clear();
    }
}
