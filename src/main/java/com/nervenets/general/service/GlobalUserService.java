package com.nervenets.general.service;

import com.nervenets.general.enumeration.*;
import com.nervenets.general.model.GeneralAddress;
import com.nervenets.general.model.GeneralUser;

public interface GlobalUserService {
    GeneralUser findUserById(long userId);

    GeneralAddress findAddressById(long addressId);

    long getSurplus(long sourceId, long userId, WealthType wealthType);

    long wealthChange(long sourceId, long userId, long offset, WealthType wealthType, OperateType operateType, String detail, PayType payType, long ip, long unionId, UnionType unionType, FlowStatus flowStatus) throws Exception;
}
