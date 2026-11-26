package com.example.sistemacontrolefinanceiro.mapper;


import com.example.sistemacontrolefinanceiro.dto.ReceitaRequestDTO;
import com.example.sistemacontrolefinanceiro.dto.ReceitaResponseDTO;
import com.example.sistemacontrolefinanceiro.model.Receita;
import com.example.sistemacontrolefinanceiro.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class ReceitaMapper {

    // Entrada: converte o DTO recebido da entidade
    public Receita toEntity(ReceitaRequestDTO dto) {
        Receita receita = new Receita();
        receita.setDescricao(dto.getDescricao());
        receita.setValor(dto.getValor());
        receita.setData(dto.getData());

        Usuario usuario = new Usuario();
        usuario.setId(dto.getUsuarioId());
        receita.setUsuario(usuario);

        return receita;
    }

    // Saída: converte a entidade em DTO de resposta (sem os atributos do UsuarioID)
    public ReceitaResponseDTO toResponse (Receita receita) {
        ReceitaResponseDTO resposta = new ReceitaResponseDTO();

        resposta.setId(receita.getId());
        resposta.setData(receita.getData());
        resposta.setDescricao(receita.getDescricao());
        resposta.setValor(receita.getValor());

        return resposta;
    }
}