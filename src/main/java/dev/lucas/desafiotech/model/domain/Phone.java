package dev.lucas.desafiotech.model.domain;

import dev.lucas.desafiotech.model.enums.PhoneType;

import java.util.UUID;

public record Phone(UUID uuid,
                    String ddd,
                    String numero,
                    PhoneType tipo) {
}
