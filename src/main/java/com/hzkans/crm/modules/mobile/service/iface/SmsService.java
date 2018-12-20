package com.hzkans.crm.modules.mobile.service.iface;

import com.hzkans.crm.common.service.ServiceException;

public interface SmsService {

    Boolean sendSecondsTick(String var1, String var2, String... var3) throws ServiceException;
}