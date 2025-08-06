package com.eq.doc.domain;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EasyAssertMessage;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.eq.doc.domain.proxy.CategoryProxy;
import lombok.Data;

/**
 * create time 2025/8/4 22:55
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("t_category")
@EntityProxy
@EasyAlias("t_category")
@EasyAssertMessage("未找到对应的类目信息")
public class Category implements ProxyEntityAvailable<Category , CategoryProxy> {
    @Column(primaryKey = true,comment = "类目id")
    private String id;
    @Column(comment = "类目姓名")
    private String name;
    @Column(comment = "类目排序")
    private Integer sort;
}
