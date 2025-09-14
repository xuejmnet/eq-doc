package com.eq.doc.configuration;

import com.easy.query.core.basic.extension.generated.PrimaryKeyGenerator;
import com.easy.query.core.metadata.ColumnMetadata;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.UUID;

/**
 * create time 2025/9/11 22:29
 * 文件说明
 *
 * @author xuejiaming
 */
@Component
public class UUIDPrimaryKey implements PrimaryKeyGenerator {
    @Override
    public Serializable getPrimaryKey() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 如果需要判断之前是否有值
     *
     * @param entity
     * @param columnMetadata
     */
    @Override
    public void setPrimaryKey(Object entity, ColumnMetadata columnMetadata) {
        Object oldValue = columnMetadata.getGetterCaller().apply(entity);
        if (oldValue == null) {
            PrimaryKeyGenerator.super.setPrimaryKey(entity, columnMetadata);
        }
    }
}
