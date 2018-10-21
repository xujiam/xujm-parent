package top.xujm.modules.blog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@RequestMapping("index")
@Controller
public class IndexController {

    @GetMapping("index")
    public String index(@ApiIgnore ModelMap model){
        model.addAttribute("siteName","随风逸");
        model.addAttribute("template","default");
        model.addAttribute("articleList",null);
        model.addAttribute("sliderList",null);
        return "default/index";
    }

}
