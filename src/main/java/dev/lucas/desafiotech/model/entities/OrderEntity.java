package dev.lucas.desafiotech.model.entities;

import dev.lucas.desafiotech.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pedido")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID uuid;

    @Column(name = "revenda_id", nullable = false)
    private Long resaleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "revenda_id", insertable = false, updatable = false)
    private ResaleEntity resale;

    private String cliente;

    @Column(name = "data_pedido")
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> itens;

    @PrePersist
    public void prePersist() {
        this.uuid = UUID.randomUUID();
        this.date = LocalDateTime.now();
        this.status = OrderStatus.PENDENTE;
    }
}
