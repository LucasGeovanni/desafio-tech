package dev.lucas.desafiotech.model.entities;

import dev.lucas.desafiotech.model.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "controle_integracao")
public class IntegrationControlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID uuid;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime data;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status;

    @Builder.Default
    @Column(nullable = false)
    private Integer attempts = 0;

    @Column(name = "revenda_id", nullable = false)
    private Long resaleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "revenda_id", updatable = false, insertable = false)
    private ResaleEntity resale;

    @OneToMany(mappedBy = "integrationControl", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IntegrationControlOrderEntity> orders;

    @PrePersist
    protected void prePersist() {
        this.uuid = UUID.randomUUID();
        this.data = LocalDateTime.now();
        if (this.status == null) {
            this.status = RequestStatus.PENDENTE;
        }
    }
}