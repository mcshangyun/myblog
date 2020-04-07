package com.mcsy.blog.controller;

import com.mcsy.blog.beans.Type;
import com.mcsy.blog.service.BlogService;
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

import java.util.List;

/**
 * @author 15199
 */
@Controller
public class TypeShowController {
    @Autowired
    TypeService typeService;

    @Autowired
    BlogService blogService;
    @GetMapping("types/{id}")
    public String types(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, @PathVariable("id") Long id, Model model) {
        List<Type> types = typeService.listTypeTop(1000);
        if (id == -1) {
            id=types.get(0).getId();
        }
        BlogQuery query=new BlogQuery();
        query.setTypeId(id);
        model.addAttribute("types",types);
        model.addAttribute("page",blogService.listBlog(pageable,query));
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
