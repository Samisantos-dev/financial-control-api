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
        despesa.setDataVencimento(dto.getDataVencimento());
        despesa.setCategoriaEnum(dto.getCategoriaEnum());
        despesa.setFormaPagamentoEnum(dto.getFormaPagamentoEnum());
        despesa.setStatusPagamentoEnum(dto.getStatusPagamentoEnum());

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
        resposta.setDataVencimento(despesa.getDataVencimento());
        resposta.setCategoriaEnum(despesa.getCategoriaEnum());
        resposta.setFormaPagamentoEnum(despesa.getFormaPagamentoEnum());
        resposta.setStatusPagamentoEnum(despesa.getStatusPagamentoEnum());
        resposta.setId(despesa.getId());

        return resposta;
    }

}
