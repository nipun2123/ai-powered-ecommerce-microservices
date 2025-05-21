package com.devicehub.mapper;

import com.devicehub.dto.request.PhoneRequestDto;
import com.devicehub.dto.request.TabletRequestDto;
import com.devicehub.dto.response.PhoneResponseDto;
import com.devicehub.dto.response.ProductListItemResponseDto;
import com.devicehub.dto.response.TabletResponseDto;
import com.devicehub.entity.PhoneSpec;
import com.devicehub.entity.Product;
import com.devicehub.entity.TabletSpec;


public class ProductMapper {

    public static Product phoneRequestDtoToProduct(PhoneRequestDto phoneRequestDto){

        PhoneSpec phoneSpec = PhoneSpec.builder()
                .hasEsim(phoneRequestDto.isHasEsim())
                .ipRating(phoneRequestDto.getIpRating())
                .ultraWideCamMp(phoneRequestDto.getUltraWideCamMp())
                .build();

        Product product = Product.builder()
                .sku(phoneRequestDto.getSku())
                .name(phoneRequestDto.getName())
                .brand(phoneRequestDto.getBrand())
                .os(phoneRequestDto.getOs())
                .barcode(phoneRequestDto.getBarcode())
                .batteryMah(phoneRequestDto.getBatteryMah())
                .category(phoneRequestDto.getCategory())
                .chipset(phoneRequestDto.getChipset())
                .price(phoneRequestDto.getPrice())
                .ramGb(phoneRequestDto.getRamGb())
                .description(phoneRequestDto.getDescription())
                .frontCamMp(phoneRequestDto.getFrontCamMp())
                .mainCamMp(phoneRequestDto.getMainCamMp())
                .storageGb(phoneRequestDto.getStorageGb())
                .refreshRateHz(phoneRequestDto.getRefreshRateHz())
                .screenSizeInches(phoneRequestDto.getScreenSizeInches())
                .phoneSpec(phoneSpec)
                .build();

        phoneSpec.setProduct(product);

        return product;

    }

    public static Product tabletRequestDtoToProduct(TabletRequestDto tabletRequestDto){

        TabletSpec tabletSpec = TabletSpec.builder()
                .hasCellular(tabletRequestDto.isHasCellular())
                .stylusSupported(tabletRequestDto.isStylusSupported())
                .keyboardSupport(tabletRequestDto.isKeyboardSupport())
                .build();

        Product product = Product.builder()
                .sku(tabletRequestDto.getSku())
                .name(tabletRequestDto.getName())
                .brand(tabletRequestDto.getBrand())
                .os(tabletRequestDto.getOs())
                .barcode(tabletRequestDto.getBarcode())
                .batteryMah(tabletRequestDto.getBatteryMah())
                .category(tabletRequestDto.getCategory())
                .chipset(tabletRequestDto.getChipset())
                .price(tabletRequestDto.getPrice())
                .ramGb(tabletRequestDto.getRamGb())
                .description(tabletRequestDto.getDescription())
                .frontCamMp(tabletRequestDto.getFrontCamMp())
                .mainCamMp(tabletRequestDto.getMainCamMp())
                .storageGb(tabletRequestDto.getStorageGb())
                .refreshRateHz(tabletRequestDto.getRefreshRateHz())
                .screenSizeInches(tabletRequestDto.getScreenSizeInches())
                .tabletSpec(tabletSpec)
                .build();

        tabletSpec.setProduct(product);

        return product;

    }

    public static PhoneResponseDto productToPhoneResponseDto(Product product){

        PhoneResponseDto phoneResponseDto = PhoneResponseDto.builder()
                .sku(product.getSku())
                .name(product.getName())
                .brand(product.getBrand())
                .os(product.getOs())
                .barcode(product.getBarcode())
                .batteryMah(product.getBatteryMah())
                .category(product.getCategory())
                .chipset(product.getChipset())
                .price(product.getPrice())
                .ramGb(product.getRamGb())
                .description(product.getDescription())
                .frontCamMp(product.getFrontCamMp())
                .mainCamMp(product.getMainCamMp())
                .storageGb(product.getStorageGb())
                .refreshRateHz(product.getRefreshRateHz())
                .screenSizeInches(product.getScreenSizeInches())
                .isActive(product.isActive())
                .hasEsim(product.getPhoneSpec().isHasEsim())
                .ipRating(product.getPhoneSpec().getIpRating())
                .ultraWideCamMp(product.getPhoneSpec().getUltraWideCamMp())
                .build();


        return phoneResponseDto;

    }


    public static TabletResponseDto productToTabletResponseDto(Product product){

        TabletResponseDto tabletResponseDto = TabletResponseDto.builder()
                .sku(product.getSku())
                .name(product.getName())
                .brand(product.getBrand())
                .os(product.getOs())
                .barcode(product.getBarcode())
                .batteryMah(product.getBatteryMah())
                .category(product.getCategory())
                .chipset(product.getChipset())
                .price(product.getPrice())
                .ramGb(product.getRamGb())
                .description(product.getDescription())
                .frontCamMp(product.getFrontCamMp())
                .mainCamMp(product.getMainCamMp())
                .storageGb(product.getStorageGb())
                .refreshRateHz(product.getRefreshRateHz())
                .screenSizeInches(product.getScreenSizeInches())
                .isActive(product.isActive())
                .hasCellular(product.getTabletSpec().isHasCellular())
                .stylusSupported(product.getTabletSpec().isStylusSupported())
                .keyboardSupport(product.getTabletSpec().isKeyboardSupport())
                .build();


        return tabletResponseDto;

    }

    public static ProductListItemResponseDto productToProductListItemResponseDto(Product product){

        ProductListItemResponseDto productListItemResponseDto = ProductListItemResponseDto.builder()
                .sku(product.getSku())
                .name(product.getName())
                .brand(product.getBrand())
                .os(product.getOs())
                .barcode(product.getBarcode())
                .batteryMah(product.getBatteryMah())
                .category(product.getCategory())
                .chipset(product.getChipset())
                .price(product.getPrice())
                .ramGb(product.getRamGb())
                .description(product.getDescription())
                .frontCamMp(product.getFrontCamMp())
                .mainCamMp(product.getMainCamMp())
                .storageGb(product.getStorageGb())
                .refreshRateHz(product.getRefreshRateHz())
                .screenSizeInches(product.getScreenSizeInches())
                .isActive(product.isActive())
                .build();

        if(product.getPhoneSpec() != null){
            productListItemResponseDto.setHasEsim(product.getPhoneSpec().isHasEsim());
            productListItemResponseDto.setIpRating(product.getPhoneSpec().getIpRating());
            productListItemResponseDto.setUltraWideCamMp(product.getPhoneSpec().getUltraWideCamMp());
        }

        if(product.getTabletSpec() != null){
            productListItemResponseDto.setHasCellular(product.getTabletSpec().isHasCellular());
            productListItemResponseDto.setStylusSupported(product.getTabletSpec().isStylusSupported());
            productListItemResponseDto.setKeyboardSupport(product.getTabletSpec().isKeyboardSupport());
        }


        return productListItemResponseDto;

    }
}
