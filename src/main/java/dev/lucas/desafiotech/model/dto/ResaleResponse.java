package dev.lucas.desafiotech.model.dto;

import dev.lucas.desafiotech.model.domain.Address;
import dev.lucas.desafiotech.model.domain.Phone;

import java.util.List;
import java.util.UUID;

public record ResaleResponse(UUID uuid,
                              String cnpj,
                              String razaoSocial,
                              String nomeFantasia,
                              String email,
                              List<Phone> phones,
                              List<Address> address) {
}
