package dev.lucas.desafiotech.model.domain;

import java.util.List;
import java.util.UUID;

public record Resale(Long id,
                      UUID uuid,
                      String cnpj,
                      String razaoSocial,
                      String nomeFantasia,
                      String email,
                      List<Phone> phones,
                      List<Address> address,
                      List<Order> orders) {
}
