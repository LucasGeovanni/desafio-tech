package dev.lucas.desafiotech.mappers;

import dev.lucas.desafiotech.model.domain.Order;
import dev.lucas.desafiotech.model.domain.Resale;
import dev.lucas.desafiotech.model.dto.OrderRequest;
import dev.lucas.desafiotech.model.dto.OrderResponse;
import dev.lucas.desafiotech.model.dto.ResaleRequest;
import dev.lucas.desafiotech.model.entities.OrderEntity;
import dev.lucas.desafiotech.model.entities.ResaleEntity;
import org.mapstruct.*;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    OrderEntity to(Order order);

    Order to(OrderEntity order);

    List<Order> to(List<OrderEntity> order);

    Order to(OrderRequest order);

    OrderResponse toResponse(Order order);

    List<OrderResponse> toResponse(List<Order> order);

    @Mapping(target = "resaleId", source = "resaleId")
    OrderEntity to(Order order, Long resaleId);

    @AfterMapping
    default void setOrderInRelations(@MappingTarget OrderEntity orderEntity) {
        if (Objects.nonNull(orderEntity.getItens())) {
            orderEntity.getItens().forEach(x-> x.setOrder(orderEntity));
        }
    }
}
