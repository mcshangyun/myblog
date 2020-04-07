package com.mcsy.blog.controller.admin;

import com.mcsy.blog.beans.Type;
import com.mcsy.blog.service.TypeService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author 15199
 */
@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    TypeService typeService;

    /**
     *  查询types
     * @param pageable 分页的参数 使用注解进行默认配置
     * @param model 返回的数据放在这里面
     * @return 返回页面
     */
    @GetMapping("/types")
    public String types(@PageableDefault(size = 6,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        /*查询的数据 返回*/
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    /**
     * 进入分类新增页面
     * @return 返回页面
     */
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    /**
     * 修改页面
     * @param id 根据id修改
     * @param model 返回的数据
     * @return 返回页面
     */
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable("id") Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }

    /**
     * 添加type 路径一样时 参数决定执行那个方法
     * @param type 输入的参数type
     * @param attributes 重定向的数据
     * @return 返回页面
     */
    @PostMapping("/types")
    public String post(Type type, RedirectAttributes attributes){
        Type byName = typeService.getByName(type.getName());
        if (byName != null){
            attributes.addFlashAttribute("message","名称重复，请修改");
            return "redirect:/admin/types";
        }
        Type newType = typeService.saveType(type);
        if (newType==null){
            attributes.addFlashAttribute("message","操作失败");
        }else{
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 修改
     * @param type  输入的参数type
     * @param id 根据id修改
     * @param attributes 返回信息
     * @return 返回页面
     */
    @PostMapping("/types/{id}")
    public String editPost(Type type, @PathVariable("id") Long id,RedirectAttributes attributes){
        Type newType = typeService.saveType(type);
        if (newType==null){
            attributes.addFlashAttribute("message","操作失败");
        }else{
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 删除
     * @param id 根据id删除
     * @param attributes 返回信息
     * @return 返回页面
     */
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable("id") Long id,RedirectAttributes attributes){
        typeService.removeType(id);
        attributes.addFlashAttribute("message","操作成功");
        return "redirect:/admin/types";
    }
}
