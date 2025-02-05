package dev.lucas.desafiotech.mappers;

import dev.lucas.desafiotech.model.domain.Order;
import dev.lucas.desafiotech.model.entities.IntegrationControlOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IntegrationControlOrderMapper {

    @Mapping(target = "uuid", source = "source.order.uuid")
    @Mapping(target = "dataOrder", source = "source.order.date")
    @Mapping(target = "status", source = "source.order.status")
    @Mapping(target = "cliente", source = "source.order.cliente")
    @Mapping(target = "itens", source = "source.order.itens")
    Order to(IntegrationControlOrderEntity source);
}
