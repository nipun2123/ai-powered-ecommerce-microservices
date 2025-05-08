package com.devicehub.service;

import com.devicehub.dto.request.PhoneRequestDto;
import com.devicehub.dto.request.TabletRequestDto;
import com.devicehub.mapper.ProductMapper;
import com.devicehub.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ProductService implements IProductService{

    private ProductRepository productRepository;

    @Override
    public void savePhone(PhoneRequestDto phoneRequestDto) {
        productRepository.save(ProductMapper.phoneRequestDtoToProduct(phoneRequestDto));
    }

    @Override
    public void saveTablet(TabletRequestDto tabletRequestDto) {
        productRepository.save(ProductMapper.tabletRequestDtoToProduct(tabletRequestDto));
    }
}
