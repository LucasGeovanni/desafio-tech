package dev.lucas.desafiotech.controller.mock;

public record OrderAddressSupplierRequest(String rua,
                                              String numero,
                                              String bairro,
                                              String cep,
                                              String cidade,
                                              String estado,
                                              String complemento) {
}
