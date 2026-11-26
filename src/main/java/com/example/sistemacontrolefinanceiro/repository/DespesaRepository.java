package com.example.sistemacontrolefinanceiro.repository;

import com.example.sistemacontrolefinanceiro.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findByUsuarioId(Long usuarioId);

    // Soma as despesas do usuário; COALESCE retorna zero quando não há despesas.
    @Query("SELECT COALESCE(SUM(d.valor), 0) FROM Despesa d WHERE d.usuario.id = :usuarioId")
    BigDecimal somarValoresPorUsuarioId(@Param("usuarioId") Long usuarioId);
}