package com.mcsy.blog.service.impl;

import com.mcsy.blog.beans.Type;
import com.mcsy.blog.dao.TypeRepository;
import com.mcsy.blog.exception.NotFoundException;
import com.mcsy.blog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author 15199
 * 类型实现
 */
@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeRepository repository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return repository.save(type);
    }

    /**
     * springboot2.x find one方法发生变化 所以使用find by id方法
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Type getType(Long id) {
        Optional<Type> byId = repository.findById(id);
        return byId.isPresent() ? byId.get() : null;
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return repository.findAll(pageable);
    }
    @Transactional
    @Override
    public List<Type> listType() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort=Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable=PageRequest.of(0,size,sort);
        return repository.findTop(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Optional<Type> byId = repository.findById(id);
        Type newType = null;
        if (byId.isPresent()) {
            newType = byId.get();
        } else {
            throw new NotFoundException("该类型不存在");
        }
        //使用BeanUtils工具类进行赋值 将type赋值给newType
        BeanUtils.copyProperties(type, newType);
        return repository.save(newType);
    }

    @Transactional
    @Override
    public void removeType(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public Type getByName(String name) {
        return repository.findByName(name);
    }
}
