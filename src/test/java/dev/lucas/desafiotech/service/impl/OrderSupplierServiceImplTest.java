package dev.lucas.desafiotech.service.impl;

import dev.lucas.desafiotech.controller.mock.SupplierRequest;
import dev.lucas.desafiotech.controller.mock.SupplierResponse;
import dev.lucas.desafiotech.client.SupplierClient;
import dev.lucas.desafiotech.mappers.OrderSupplierMapper;
import dev.lucas.desafiotech.model.domain.Address;
import dev.lucas.desafiotech.model.domain.Order;
import dev.lucas.desafiotech.model.domain.Resale;
import dev.lucas.desafiotech.model.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Spy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderSupplierServiceImplTest {

    @Mock
    private SupplierClient supplierClient;

    @Spy
    private OrderSupplierMapper orderSupplierMapper = Mappers.getMapper(OrderSupplierMapper.class);

    @InjectMocks
    private OrderSupplierServiceImpl orderSupplierService;

    private Resale resale;

    @BeforeEach
    void setUp() {
        resale = new Resale(
                1L,
                UUID.randomUUID(),
                "12345678000199",
                "Resale Test",
                "Resale LTDA",
                "email@resale.com",
                List.of(),
                List.of(new Address(UUID.randomUUID(), "Rua A", "123", "Bairro X", "12345-678", "Cidade Y", "Estado Z", "Apto 101")),
                List.of(new Order(1L, UUID.randomUUID(), LocalDateTime.now(), OrderStatus.PENDENTE, "test", List.of(new dev.lucas.desafiotech.model.domain.OrderItem("Produto A", 10))))
        );
    }

    @Test
    void shouldCreateOrderSupplierSuccessfully() {
        when(supplierClient.solicitacaoOrder(any(SupplierRequest.class))).thenReturn(new SupplierResponse("test", "201", List.of()));

        orderSupplierService.createOrderSupplier(resale);

        verify(supplierClient, times(1)).solicitacaoOrder(any(SupplierRequest.class));
    }

    @Test
    void shouldThrowExceptionWhenOrderSupplierFails() {
        doThrow(new RuntimeException("Erro no fornecedor"))
                .when(supplierClient).solicitacaoOrder(any(SupplierRequest.class));

        assertThrows(RuntimeException.class, () -> orderSupplierService.createOrderSupplier(resale));

        verify(supplierClient, times(1)).solicitacaoOrder(any(SupplierRequest.class));
    }


}
