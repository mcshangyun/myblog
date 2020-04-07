package com.mcsy.blog.service;

import com.mcsy.blog.beans.Tag;
import com.mcsy.blog.beans.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author 15199
 * 标签
 */
public interface TagService {
    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    Tag updateTag(Long id, Tag tag);

    void removeTag(Long id);

    Tag getByName(String name);

    List<Tag> listTagTop(Integer size);
}
