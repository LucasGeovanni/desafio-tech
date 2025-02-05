package dev.lucas.desafiotech.model.domain;

import java.util.UUID;

public record Address(UUID uuid,
                      String rua,
                      String numero,
                      String bairro,
                      String cep,
                      String cidade,
                      String estado,
                      String complemento) {
}
