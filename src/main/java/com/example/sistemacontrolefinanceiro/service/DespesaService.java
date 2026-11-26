package com.example.sistemacontrolefinanceiro.service;

import com.example.sistemacontrolefinanceiro.enums.StatusPagamentoEnum;
import com.example.sistemacontrolefinanceiro.model.Despesa;
import com.example.sistemacontrolefinanceiro.repository.DespesaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Service
public class DespesaService {


    //Onde Ocorre toda a regra de negocio
    private DespesaRepository despesaRepository;
    private UsuarioService usuarioService;

    public DespesaService(DespesaRepository despesaRepository, UsuarioService usuarioService) {
        this.despesaRepository = despesaRepository;
        this.usuarioService = usuarioService;
    }

    public Despesa salvarDespesa(Despesa despesa) {
        if (despesa.getUsuario() == null || despesa.getUsuario().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario da despesa deve ser informado");
        }
        validarDataVencimento(despesa);

        usuarioService.listarUsuarioPorId(despesa.getUsuario().getId());
        atualizarStatusPagamento(despesa);
        return despesaRepository.save(despesa);
    }

    public Despesa atualizarDespesa(Despesa despesa) {
        if (despesa.getId() == null ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id da despesa precisa ser informada");
        }

        if (despesa.getUsuario() == null || despesa.getUsuario().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario da despesa deve ser informado");
        }

        validarDataVencimento(despesa);

        if (despesa.getValor() == null || despesa.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valor da despesa deve ser maior que zero");
        }

        Despesa existente = despesaRepository.findById(despesa.getId())
                .orElseThrow(() -> new DespesaNaoEncontradaException("Despesa não encontrada!"));
        if (despesa.getStatusPagamentoEnum() == null
                && existente.getStatusPagamentoEnum() == StatusPagamentoEnum.PAGO) {
            despesa.setStatusPagamentoEnum(StatusPagamentoEnum.PAGO);
        }

        usuarioService.listarUsuarioPorId(despesa.getUsuario().getId());
        atualizarStatusPagamento(despesa);
        return despesaRepository.save(despesa);
    }

    public void remover(Despesa despesa) {
        boolean despesaExiste = despesaRepository.existsById(despesa.getId());
        if (!despesaExiste) {
            throw new DespesaNaoEncontradaException("Despesa não encontrada!");
        }
        despesaRepository.deleteById(despesa.getId());
    }

    @Transactional
    public List<Despesa> listarDespesas(long usuarioId) {
        usuarioService.listarUsuarioPorId(usuarioId);
        List<Despesa> despesas = despesaRepository.findByUsuarioId(usuarioId);
        despesas.forEach(this::atualizarStatusPagamento);
        return despesas;
    }

    public BigDecimal calcularTotalDespesas(Long usuarioId) {
        usuarioService.listarUsuarioPorId(usuarioId);
        return despesaRepository.somarValoresPorUsuarioId(usuarioId);

    }

    @Transactional
    public Despesa buscarPorId(Long id) {
        boolean despesaExiste = despesaRepository.existsById(id);
        if (!despesaExiste) {
            throw new DespesaNaoEncontradaException("Despesa não encontrada!");
        }
        Despesa despesa = despesaRepository.findById(id).get();
        atualizarStatusPagamento(despesa);
        return despesa;
    }

    private void validarDataVencimento(Despesa despesa) {
        if (despesa.getDataVencimento() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Data de vencimento da despesa deve ser informada");
        }
    }

    private void atualizarStatusPagamento(Despesa despesa) {
        if (despesa.getStatusPagamentoEnum() == StatusPagamentoEnum.PAGO
                || despesa.getDataVencimento() == null) {
            return;
        }

        if (despesa.getDataVencimento().isBefore(LocalDate.now())) {
            despesa.setStatusPagamentoEnum(StatusPagamentoEnum.ATRASADO);
        } else {
            despesa.setStatusPagamentoEnum(StatusPagamentoEnum.PENDENTE);
        }
    }
}
