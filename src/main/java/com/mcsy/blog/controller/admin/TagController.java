package com.mcsy.blog.controller.admin;

import com.mcsy.blog.beans.Tag;
import com.mcsy.blog.service.TagService;
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
public class TagController {
    @Autowired
    TagService tagService;

    /**
     *  查询tags
     * @param pageable 分页的参数 使用注解进行默认配置
     * @param model 返回的数据放在这里面
     * @return 返回页面
     */
    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 6,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        /*查询的数据 返回*/
        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tags";
    }

    /**
     * 进入分类新增页面
     * @return 返回页面
     */
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    /**
     * 修改页面
     * @param id 根据id修改
     * @param model 返回的数据
     * @return 返回页面
     */
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable("id") Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }

    /**
     * 添加tag 路径一样时 参数决定执行那个方法
     * @param tag 输入的参数tag
     * @param attributes 重定向的数据
     * @return 返回页面
     */
    @PostMapping("/tags")
    public String post(Tag tag, RedirectAttributes attributes){
        Tag byName = tagService.getByName(tag.getName());
        if (byName != null){
            attributes.addFlashAttribute("message","名称重复，请修改");
            return "redirect:/admin/tags";
        }
        Tag newTag = tagService.saveTag(tag);
        if (newTag==null){
            attributes.addFlashAttribute("message","操作失败");
        }else{
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 修改
     * @param tag  输入的参数tag
     * @param id 根据id修改
     * @param attributes 返回信息
     * @return 返回页面
     */
    @PostMapping("/tags/{id}")
    public String editPost(Tag tag, @PathVariable("id") Long id,RedirectAttributes attributes){
        Tag newTag = tagService.saveTag(tag);
        if (newTag==null){
            attributes.addFlashAttribute("message","操作失败");
        }else{
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 删除
     * @param id 根据id删除
     * @param attributes 返回信息
     * @return 返回页面
     */
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable("id") Long id,RedirectAttributes attributes){
        tagService.removeTag(id);
        attributes.addFlashAttribute("message","操作成功");
        return "redirect:/admin/tags";
    }
}
