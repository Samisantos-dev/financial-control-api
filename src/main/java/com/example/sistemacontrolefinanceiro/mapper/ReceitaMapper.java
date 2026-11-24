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
        receita.setId(dto.getId());
        receita.setDescricao(dto.getDescricao());
        receita.setValor(dto.getValor());
        receita.setData(dto.getData());

        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        receita.setUsuario(usuario);

        return receita;
    }

    // Saída: converte a entidade em DTO de resposta (sem os atributos do UsuarioID)
    public ReceitaResponseDTO toResponse (Receita receita) {
        ReceitaResponseDTO resposta = new ReceitaResponseDTO();

        receita.setData(receita.getData());
        receita.setDescricao(receita.getDescricao());
        receita.setValor(receita.getValor());


        return resposta;
    }
}