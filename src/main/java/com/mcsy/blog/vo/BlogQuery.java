package com.mcsy.blog.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 15199
 */
@Data
@NoArgsConstructor
public class BlogQuery {
    private String title;
    private Long typeId;
    private boolean recommend;
}
