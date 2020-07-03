package com.nervenets.general.service;

import com.nervenets.general.model.GeneralCustomer;
import com.nervenets.general.model.GeneralStore;
import com.nervenets.general.model.GeneralTable;

public interface GlobalMerchantService {
    GeneralStore findStoreById(long storeId);

    GeneralCustomer findCustomerById(long customerId);

    GeneralTable findTableById(long tableId);
}
