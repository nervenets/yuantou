package com.nervenets.general.service;

import com.nervenets.general.model.GeneralProduct;
import com.nervenets.general.model.GeneralProductType;

public interface GlobalProductService {
    GeneralProduct findProductById(long productId);

    GeneralProductType findProductTypeById(long typeId);

    void updateInventory(long storeId, long productId, long priceId, long inventory);
}
