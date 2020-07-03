package com.nervenets.general.service;

import com.nervenets.general.model.GeneralCompany;
import com.nervenets.general.model.GeneralServer;

public interface GlobalSystemService {
    GeneralCompany findCompanyById(long companyId);

    GeneralServer findServerById(long serverId);

    void updateStoreCount(long serverId, long count);
}
