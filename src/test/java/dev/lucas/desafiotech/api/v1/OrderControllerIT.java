package dev.lucas.desafiotech.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.lucas.desafiotech.model.dto.ItemOrderRequest;
import dev.lucas.desafiotech.model.dto.OrderRequest;
import dev.lucas.desafiotech.model.entities.OrderEntity;
import dev.lucas.desafiotech.model.entities.ResaleEntity;
import dev.lucas.desafiotech.repository.OrderRepository;
import dev.lucas.desafiotech.repository.ResaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class OrderControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ResaleRepository resaleRepository;

    private UUID resaleUuid;
    private Long resaleId;

    @BeforeEach
    void setup() {
        orderRepository.deleteAll();
        resaleRepository.deleteAll();

        ResaleEntity resale = new ResaleEntity();
        resale.setUuid(UUID.randomUUID());
        resale.setCnpj("12345678000199");
        resale.setRazaoSocial("Resale Test");
        resale.setNomeFantasia("Resale LTDA");
        resale.setEmail("email@resale.com");
        resaleRepository.save(resale);

        resaleUuid = resale.getUuid();
        resaleId = resale.getId();
    }

    @Test
    void shouldCreateOrderSuccessfully() throws Exception {
        OrderRequest orderRequest = new OrderRequest("Cliente Teste",
                List.of(new ItemOrderRequest("Produto A", 2)));

        mockMvc.perform(post("/api/v1/orders/" + resaleUuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

        List<OrderEntity> orders = orderRepository.findAll();
        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getCliente()).isEqualTo("Cliente Teste");
    }

    @Test
    void shouldFindOrderByUUID() throws Exception {
        OrderEntity order = new OrderEntity();
        order.setUuid(UUID.randomUUID());
        order.setCliente("Cliente Teste");
        order.setResaleId(resaleId);
        orderRepository.save(order);

        mockMvc.perform(get("/api/v1/orders/" + order.getUuid())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(order.getUuid().toString()))
                .andExpect(jsonPath("$.cliente").value("Cliente Teste"));
    }

    @Test
    void shouldFindOrdersByResaleUUID() throws Exception {
        OrderEntity order1 = new OrderEntity();
        order1.setUuid(UUID.randomUUID());
        order1.setCliente("Cliente 1");
        order1.setResaleId(resaleId);
        orderRepository.save(order1);

        OrderEntity order2 = new OrderEntity();
        order2.setUuid(UUID.randomUUID());
        order2.setCliente("Cliente 2");
        order2.setResaleId(resaleId);
        orderRepository.save(order2);

        mockMvc.perform(get("/api/v1/orders/resale/" + resaleUuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].uuid").value(order1.getUuid().toString()))
                .andExpect(jsonPath("$[1].uuid").value(order2.getUuid().toString()));
    }
}
