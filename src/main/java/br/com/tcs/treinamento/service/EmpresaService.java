package br.com.tcs.treinamento.service;

import br.com.tcs.treinamento.entity.Empresa;
import br.com.tcs.treinamento.entity.Pessoa;
import java.util.List;

public interface EmpresaService {
    void cadastrar(Empresa empresa);
    Empresa buscarPorId(Long id);
    List<Empresa> listar();
    void atualizar(Empresa empresa);
    void excluir(Empresa empresa);
}