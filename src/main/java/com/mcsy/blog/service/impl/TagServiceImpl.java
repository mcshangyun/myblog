package com.mcsy.blog.service.impl;

import com.mcsy.blog.beans.Tag;
import com.mcsy.blog.dao.TagRepository;
import com.mcsy.blog.exception.NotFoundException;
import com.mcsy.blog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author 15199
 * 标签的实现
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository repository;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return repository.save(tag);
    }

    /**
     * springboot2.x find one方法发生变化 所以使用find by id方法
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Tag getTag(Long id) {
        Optional<Tag> byId = repository.findById(id);
        return byId.isPresent() ? byId.get() : null;
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return repository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {
        return repository.findAllById(convertToList(ids));
    }

    /**
     * 把ids转成long类型的List
     *
     * @param ids 标签id
     * @return 返回List
     */
    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idArray = ids.split(",");
            for (int i = 0; i < idArray.length; i++) {
                list.add(new Long(idArray[i]));
            }
        }
        return list;
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Optional<Tag> byId = repository.findById(id);
        Tag newTag = null;
        if (byId.isPresent()) {
            newTag = byId.get();
        } else {
            throw new NotFoundException("该类型不存在");
        }
        BeanUtils.copyProperties(tag, newTag);
        return repository.save(newTag);
    }

    @Transactional
    @Override
    public void removeTag(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public Tag getByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort=Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable= PageRequest.of(0,size,sort);
        return repository.findTop(pageable);
    }
}
