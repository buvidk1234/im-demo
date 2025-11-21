package com.it.imdemo.infrastructure.persistence.mapper;

import com.it.imdemo.infrastructure.persistence.entity.ReadReceiptEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 15324
* @description 针对表【read_receipts(群聊消息阅读回执)】的数据库操作Mapper
* @createDate 2025-11-11 01:48:25
* @Entity com.it.imdemo.infrastructure.repository.entity.ReadReceiptEntity
*/
@Mapper
public interface ReadReceiptMapper extends BaseMapper<ReadReceiptEntity> {

}




