package com.nervenets.general.service;

public interface GlobalOrderService {
    /**
     * @return 剩余库存数量
     */
    long surplus(long storeId, long productId, long priceId);
}
