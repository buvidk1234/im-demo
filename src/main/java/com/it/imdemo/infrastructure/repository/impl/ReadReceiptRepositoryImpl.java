package com.it.imdemo.infrastructure.repository.impl;

import com.it.imdemo.domain.message.ReadReceiptRepository;
import com.it.imdemo.domain.message.model.ReadReceipt;
import com.it.imdemo.infrastructure.repository.entity.ReadReceiptEntity;
import com.it.imdemo.infrastructure.repository.mapper.ReadReceiptMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

@Repository
public class ReadReceiptRepositoryImpl implements ReadReceiptRepository {

    @Resource
    private ReadReceiptMapper readReceiptMapper;

    @Override
    public void save(ReadReceipt receipt) {
        ReadReceiptEntity readReceiptEntity = new ReadReceiptEntity();
        BeanUtils.copyProperties(receipt, readReceiptEntity);
        readReceiptMapper.insertOrUpdate(readReceiptEntity);
    }
}
