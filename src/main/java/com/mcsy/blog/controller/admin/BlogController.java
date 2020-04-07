package com.mcsy.blog.controller.admin;

import com.mcsy.blog.beans.Blog;
import com.mcsy.blog.beans.User;
import com.mcsy.blog.service.BlogService;
import com.mcsy.blog.service.TagService;
import com.mcsy.blog.service.TypeService;
import com.mcsy.blog.vo.BlogQuery;
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

import javax.servlet.http.HttpSession;


/**
 * @author 15199
 */
@Controller
@RequestMapping("/admin")
public class BlogController {
    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";
    @Autowired
    BlogService blogService;
    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;

    @GetMapping("/blogs")
    public String blog(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blogQuery, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        return LIST;
    }

    /**
     * 搜索
     *
     * @param pageable  分页信息
     * @param blogQuery blog查询的对象
     * @param model     数据
     * @return 返回页面 ：：blogList 渲染页面指定的部分
     */
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blogQuery, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        return "admin/blogs :: blogList";
    }
    @GetMapping("/blogs/input")
    public String input(Model model) {
        /*初始化数据 让前端拿到一些值*/
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("blog", new Blog());
        return INPUT;
    }
    @GetMapping("/blogs/{id}/input")
    public String input(@PathVariable("id") Long id, Model model) {
        /*初始化数据 让前端拿到一些值*/
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blog);
        return INPUT;
    }
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){
        Object user = session.getAttribute("user");
        if (user instanceof User){
            blog.setUser((User)user);
        }
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId()==null) {
            b = blogService.saveBlog(blog);
        }else{
            b=blogService.update(blog.getId(),blog);
        }
        if (b == null){
            attributes.addFlashAttribute("message","操作失败");
        }else{
            attributes.addFlashAttribute("message","操作成功");
        }
        return REDIRECT_LIST;
    }
    /**
     * 删除
     * @param id 根据id删除
     * @param attributes 返回信息
     * @return 返回页面
     */
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable("id") Long id,RedirectAttributes attributes){
        blogService.removeBlog(id);
        attributes.addFlashAttribute("message","操作成功");
        return REDIRECT_LIST;
    }
}
