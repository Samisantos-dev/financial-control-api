package com.example.sistemacontrolefinanceiro.repository;

import com.example.sistemacontrolefinanceiro.enums.CategoriaEnum;
import com.example.sistemacontrolefinanceiro.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

        List<Categoria> findByUsuarioId(Long usuarioId);

        boolean existsByNomeAndUsuarioId(String nome, Long usuarioId);
    }


