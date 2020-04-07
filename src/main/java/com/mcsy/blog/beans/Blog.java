package com.mcsy.blog.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author 15199
 * 博客表
 */
@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "t_blog")
public class Blog {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    /**
     * 使用lob 说明这是一个长的字段，Basic指定为懒加载  在使用的时候才会去加载
     */
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;
    private String firstPicture;
    private String flag;
    private Integer views;
    /*是否分享*/
    private boolean shareStatement;
    /*是否发布*/
    private boolean published;
    /**
     * 是否推荐
     */
    private boolean recommend;
    /**
     * 指定数据库时间的类型
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    /**
     * 该注解不会让属性进行入库操作
     */
    @Transient
    private String tagIds;
    @ManyToOne
    private Type type;
    /**
     * 级联新增 blog新增标签的时候 这个也会进行新增
     */
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();


    @ManyToOne
    private User user;


    private String description;

    public void init() {
        this.tagIds = tagsToIds(this.getTags());
    }

    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (int i = 0; i < tags.size(); i++) {
                if (flag) {
                    ids.append(",");
                }else{
                    flag=true;
                }
                ids.append(tags.get(i).getId());
            }
            return ids.toString();
        }else{
            return tagIds;
        }
    }
}
