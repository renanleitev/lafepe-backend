package io.bootify.lafepe.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Estoque {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String lote;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private String unidade;

    @Column
    private Integer saldoOriginal;

    @Column
    private Integer saldoAtual;

    @Column(nullable = false)
    private Integer quarentena;

    @Column(nullable = false)
    private LocalDate validade;

    // ManyToOne = Passar o FetchType.EAGER se quiser receber o objeto completo na resposta da API
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    // OneToMany = Passar o JsonBackReference se quiser receber o objeto completo na resposta da API
    @OneToMany(mappedBy = "estoque")
    @JsonBackReference
    private Set<Registro> registro;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
