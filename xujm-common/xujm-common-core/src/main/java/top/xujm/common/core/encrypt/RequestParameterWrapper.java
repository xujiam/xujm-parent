package top.xujm.common.core.encrypt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author Xujm
 */
public class RequestParameterWrapper extends HttpServletRequestWrapper {

    private Map<String, String[]> params = new HashMap<>();

    RequestParameterWrapper(HttpServletRequest request) {
        super(request);
        //将现有parameter传递给params
        this.params.putAll(request.getParameterMap());
        params.remove("data");
    }

    /**
     * 重载构造函数
     */
    public RequestParameterWrapper(HttpServletRequest request, Map<String, Object> extraParams) {
        this(request);
        addParameters(extraParams);
    }

    public void addParameters(Map<String, Object> extraParams) {
        for (Map.Entry<String, Object> entry : extraParams.entrySet()) {
            addParameter(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 重写getParameter，代表参数从当前类中的map获取
     */
    @Override
    public String getParameter(String name) {
        String[]values = params.get(name);
        if(values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    /**
     * 重写getParameter，代表参数从当前类中的map获取
     */
    @Override
    public Map<String,String[]> getParameterMap() {
        return params;
    }

    /**
     * 同上
     */
    @Override
    public String[] getParameterValues(String name) {
        return params.get(name);
    }

    @Override
    public Enumeration<String> getParameterNames(){
        Vector<String> vector = new Vector<>(params.keySet());
        return vector.elements();
    }

    /**
     * 添加参数
     */
    public void addParameter(String name, Object value) {
        if (value != null) {
            if (value instanceof String[]) {
                params.put(name, (String[]) value);
            } else if (value instanceof String) {
                params.put(name, new String[]{(String) value});
            } else {
                params.put(name, new String[]{String.valueOf(value)});
            }
        }
    }
}
