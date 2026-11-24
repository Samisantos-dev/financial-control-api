package com.example.sistemacontrolefinanceiro.service;

import com.example.sistemacontrolefinanceiro.exception.DespesaNaoEncontradaException;
import com.example.sistemacontrolefinanceiro.model.Despesa;
import com.example.sistemacontrolefinanceiro.repository.DespesaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
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

        usuarioService.listarUsuarioPorId(despesa.getUsuario().getId());
        return despesaRepository.save(despesa);
    }

    public Despesa atualizarDespesa(Despesa despesa) {
        if (despesa.getId() == null ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id da despesa precisa ser informada");
        }

        if (despesa.getUsuario() == null || despesa.getUsuario().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario da despesa deve ser informado");
        }

        if (despesa.getValor() == null || despesa.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valor da despesa deve ser maior que zero");
        }

        boolean despesaExiste = despesaRepository.existsById(despesa.getId());
        if (!despesaExiste) {
            throw new DespesaNaoEncontradaException("Despesa não encontrada!");
        }

        usuarioService.listarUsuarioPorId(despesa.getUsuario().getId());
        return despesaRepository.save(despesa);
    }

    public void remover(Despesa despesa) {
        boolean despesaExiste = despesaRepository.existsById(despesa.getId());
        if (!despesaExiste) {
            throw new DespesaNaoEncontradaException("Despesa não encontrada!");
        }
        despesaRepository.deleteById(despesa.getId());
    }

    public List<Despesa> listarDespesas(long usuarioId) {
        usuarioService.listarUsuarioPorId(usuarioId);
        return despesaRepository.findByUsuarioId(usuarioId);
    }

    public Despesa buscarPorId(Long id) {
        boolean despesaExiste = despesaRepository.existsById(id);
        if (!despesaExiste) {
            throw new DespesaNaoEncontradaException("Despesa não encontrada!");
        }
        return despesaRepository.findById(id).get();
    }
}
