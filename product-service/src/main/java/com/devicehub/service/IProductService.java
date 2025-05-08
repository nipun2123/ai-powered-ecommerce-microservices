package com.devicehub.service;

import com.devicehub.dto.request.PhoneRequestDto;
import com.devicehub.dto.request.TabletRequestDto;

public interface IProductService {

    /**
     * @Param phoneRequestDto - PhoneRequestDto
     */
    void savePhone(PhoneRequestDto phoneRequestDto);

    /**
     * @Param tabletRequestDto - TabletRequestDto
     */
    void saveTablet(TabletRequestDto tabletRequestDto);
}
