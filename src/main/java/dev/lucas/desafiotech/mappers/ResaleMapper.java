package dev.lucas.desafiotech.mappers;

import dev.lucas.desafiotech.model.domain.Resale;
import dev.lucas.desafiotech.model.dto.ResaleRequest;
import dev.lucas.desafiotech.model.dto.ResaleResponse;
import dev.lucas.desafiotech.model.entities.ResaleEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResaleMapper {

    @Mapping(target = "phones", source = "phones")
    @Mapping(target = "address", source = "address")
    ResaleEntity to(Resale resale);

    Resale to(ResaleEntity resale);

    Resale to(ResaleRequest resale);

    ResaleResponse toResponse(Resale resale);

    @AfterMapping
    default void setResaleInRelations(@MappingTarget ResaleEntity resaleEntity) {
        if (resaleEntity.getPhones() != null) {
            resaleEntity.getPhones().forEach(phone -> phone.setResale(resaleEntity));
        }
        if (resaleEntity.getAddress() != null) {
            resaleEntity.getAddress().forEach(address -> address.setResale(resaleEntity));
        }
    }
}
