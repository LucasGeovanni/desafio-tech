package dev.lucas.desafiotech.model.entities;

import dev.lucas.desafiotech.model.enums.PhoneType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "telefone")
public class PhoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private UUID uuid;

    private String ddd;
    private String numero;

    @Enumerated(EnumType.STRING)
    private PhoneType tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "revenda_id")
    private ResaleEntity resale;

    @PrePersist
    public void prePersist() {
        this.uuid = UUID.randomUUID();
    }

}