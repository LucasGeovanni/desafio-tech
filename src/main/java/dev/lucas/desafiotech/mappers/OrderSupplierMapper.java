package dev.lucas.desafiotech.mappers;

import dev.lucas.desafiotech.api.v1.mock.OrderAddressSupplierRequest;
import dev.lucas.desafiotech.model.domain.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderSupplierMapper {

    OrderAddressSupplierRequest to(Address address);

}
