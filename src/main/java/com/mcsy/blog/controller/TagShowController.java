package com.mcsy.blog.controller;

import com.mcsy.blog.beans.Tag;
import com.mcsy.blog.beans.Type;
import com.mcsy.blog.service.BlogService;
import com.mcsy.blog.service.TagService;
import com.mcsy.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author 15199
 */
@Controller
public class TagShowController {
    @Autowired
    TagService tagService;
    @Autowired
    BlogService blogService;
    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, @PathVariable("id") Long id, Model model){
        List<Tag> tags = tagService.listTagTop(1000);
        if (id == -1) {
            id=tags.get(0).getId();
        }
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlog(id,pageable));
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}
