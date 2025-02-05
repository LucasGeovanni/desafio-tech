package dev.lucas.desafiotech.mappers;

import dev.lucas.desafiotech.model.domain.IntegrationControl;
import dev.lucas.desafiotech.model.domain.Resale;
import dev.lucas.desafiotech.model.dto.IntegrationControlResponse;
import dev.lucas.desafiotech.model.entities.IntegrationControlEntity;
import dev.lucas.desafiotech.model.entities.IntegrationControlOrderEntity;
import dev.lucas.desafiotech.model.enums.RequestStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = IntegrationControlOrderMapper.class)
public interface IntegrationControlMapper {

    @Mapping(target = "resaleCode", source = "integrationControl.resale.uuid")
    IntegrationControl to(IntegrationControlEntity integrationControl);
    IntegrationControlResponse to(IntegrationControl integrationControl);


    default IntegrationControlEntity buildIntegrationControlEntity(Resale resale) {
        IntegrationControlEntity constoleIntegracao = IntegrationControlEntity.builder()
                .status(RequestStatus.PENDENTE)
                .resaleId(resale.id())
                .attempts(1)
                .build();

        List<IntegrationControlOrderEntity> integrationControlOrder = buildIntegrationControlOrderEntity(resale, constoleIntegracao);

        constoleIntegracao.setOrders(integrationControlOrder);

        return constoleIntegracao;
    }

    default List<IntegrationControlOrderEntity> buildIntegrationControlOrderEntity(Resale resale, IntegrationControlEntity integrationControl) {
        return resale.orders()
                .stream()
                .map(order -> IntegrationControlOrderEntity.builder()
                        .integrationControl(integrationControl)
                        .orderId(order.id())
                        .build())
                .toList();
    }

}
