package top.xujm.core.base;

/**
 * @author Xujm
 * 2018/10/24
 */
public class WebBaseController {

    public String getTemplate(String template){
        return String.format("default/%s",template);
    }


}
