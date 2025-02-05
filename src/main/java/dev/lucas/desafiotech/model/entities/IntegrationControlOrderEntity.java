package dev.lucas.desafiotech.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "controle_integracao_pedido")
public class IntegrationControlOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID uuid;

    @Column(name = "controle_integracao_id", updatable = false, insertable = false)
    private Long integrationControlId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "controle_integracao_id", nullable = false)
    private IntegrationControlEntity integrationControl;

    @Column(name = "pedido_id", nullable = false)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", insertable = false, updatable = false)
    private OrderEntity order;

    @PrePersist
    protected void prePersist() {
        this.uuid = UUID.randomUUID();
    }
}
