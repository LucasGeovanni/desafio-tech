package dev.lucas.desafiotech.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "revenda")
public class ResaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID uuid;

    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resale", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<OrderEntity> orders;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resale", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PhoneEntity> phones;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resale", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<AddressEntity> address;

    @PrePersist
    public void prePersist() {
        this.uuid = UUID.randomUUID();
    }
}
