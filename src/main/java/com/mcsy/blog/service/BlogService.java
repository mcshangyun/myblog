package com.mcsy.blog.service;

import com.mcsy.blog.beans.Blog;
import com.mcsy.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author 15199
 */
public interface BlogService {
    Blog getBlog(Long id);

    Blog getBlogConvert(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Long tagId, Pageable pageable);

    Page<Blog> listBlog(String name, Pageable pageable);

    List<Blog> listRecommendBlog(Integer size);

    Map<String, List<Blog>> archiveBlog();

    Long countBlog();

    Blog saveBlog(Blog blog);

    Blog update(Long id, Blog blog);

    int updateViews(Long id);

    void removeBlog(Long id);
}
