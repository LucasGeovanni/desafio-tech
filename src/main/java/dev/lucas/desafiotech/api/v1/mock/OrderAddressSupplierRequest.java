package dev.lucas.desafiotech.api.v1.mock;

public record OrderAddressSupplierRequest(String rua,
                                              String numero,
                                              String bairro,
                                              String cep,
                                              String cidade,
                                              String estado,
                                              String complemento) {
}
