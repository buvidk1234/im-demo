package com.it.imdemo.domain.message;

import com.it.imdemo.domain.message.model.ReadReceipt;

public interface ReadReceiptRepository {
    void save(ReadReceipt receipt);
}
