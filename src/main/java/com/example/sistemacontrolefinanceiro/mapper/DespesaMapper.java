package com.example.sistemacontrolefinanceiro.mapper;

import com.example.sistemacontrolefinanceiro.dto.DespesaRequestDTO;
import com.example.sistemacontrolefinanceiro.dto.DespesaResponseDTO;
import com.example.sistemacontrolefinanceiro.model.Despesa;
import com.example.sistemacontrolefinanceiro.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class DespesaMapper {

    // Entrada: converte o DTO recebido da entidade

    public Despesa toEntity(DespesaRequestDTO dto) {
        Despesa despesa = new Despesa();
        despesa.setDescricao(dto.getDescricao());
        despesa.setValor(dto.getValor());
        despesa.setData(dto.getData());
        despesa.setCategoriaEnum(dto.getCategoriaEnum());

        Usuario usuario = new Usuario();
        usuario.setId(dto.getUsuarioId());
        despesa.setUsuario(usuario);

        return despesa;
    }
    // Saída: converte a entidade em DTO de resposta (sem os atributos do UsuarioID)
    public DespesaResponseDTO toResponse(Despesa despesa) {
        DespesaResponseDTO resposta  = new DespesaResponseDTO();
        resposta.setDescricao(despesa.getDescricao());
        resposta.setValor(despesa.getValor());
        resposta.setData(despesa.getData());
        resposta.setCategoriaEnum(despesa.getCategoriaEnum());

        return resposta;
    }

}
