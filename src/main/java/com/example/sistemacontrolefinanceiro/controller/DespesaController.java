package com.example.sistemacontrolefinanceiro.controller;

import com.example.sistemacontrolefinanceiro.dto.DespesaRequestDTO;
import com.example.sistemacontrolefinanceiro.dto.DespesaResponseDTO;
import com.example.sistemacontrolefinanceiro.dto.UsuarioRequestDTO;
import com.example.sistemacontrolefinanceiro.dto.UsuarioResponseDTO;
import com.example.sistemacontrolefinanceiro.mapper.DespesaMapper;
import com.example.sistemacontrolefinanceiro.model.Despesa;
import com.example.sistemacontrolefinanceiro.service.DespesaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

// Controller: recebe as requisições HTTP e devolve as respostas.
// Não tem regra de negócio, só repassa o trabalho para Despesa.

@RestController
@RequestMapping ("/Despesas")

public class DespesaController {

    private final DespesaService despesaService;
    private final DespesaMapper despesaMapper;

    public DespesaController(DespesaService despesaService, DespesaMapper despesaMapper) {
        this.despesaService = despesaService;
        this.despesaMapper = despesaMapper;
    }

    @PostMapping("/salvar")
    public DespesaResponseDTO salvar(@Valid @RequestBody DespesaRequestDTO dto) {
        Despesa despesa = despesaMapper.toEntity(dto);
        Despesa salvo = despesaService.salvarDespesa(despesa);
        return despesaMapper.toResponse(salvo);

    }

    @PutMapping("/atualizar/{id}")
    public DespesaResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody DespesaRequestDTO dto) {
        Despesa despesa = despesaMapper.toEntity(dto);
        despesa.setId(id);

        Despesa atualizada = despesaService.atualizarDespesa(despesa);

        return despesaMapper.toResponse(atualizada);
    }

    @GetMapping("/usuarios/{usuarioId}/")
    public List<Despesa> listar(@PathVariable Long usuarioId) {
        return despesaService.listarDespesas(usuarioId);
    }

    @GetMapping("/usuarios/{usuarioId}/total")
    public BigDecimal calcularTotalDespesas(@PathVariable Long usuarioId) {
        return despesaService.calcularTotalDespesas(usuarioId);
    }

    @GetMapping("/despesas/{id}")
    public Despesa buscarDespesaPorId(@PathVariable Long id) {
        return despesaService.buscarPorId(id);
    }

    @DeleteMapping("/remover")
    public void remover(@RequestBody Despesa despesa) {
        despesaService.remover(despesa);
    }
}
