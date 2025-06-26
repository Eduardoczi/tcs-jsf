package br.com.tcs.treinamento.service.impl;

import br.com.tcs.treinamento.dao.EmpresaDAO;
import br.com.tcs.treinamento.dao.PessoaDAO;
import br.com.tcs.treinamento.entity.Empresa;
import br.com.tcs.treinamento.entity.Pessoa;
import br.com.tcs.treinamento.service.EmpresaService;
import br.com.tcs.treinamento.service.PessoaService;

import java.util.List;

public class EmpresaServiceImpl implements EmpresaService {

    private EmpresaDAO empresaDAO = new EmpresaDAO();

    @Override
    public void cadastrar(Empresa empresa) {
        empresaDAO.cadastrar(empresa);
    }

    @Override
    public Empresa buscarPorId(Long id){
        return empresaDAO.buscarPorId(id);
    }

    @Override
    public List<Empresa> listar(){
        return empresaDAO.listar();
    }

    @Override
    public void atualizar(Empresa empresa) {
        empresaDAO.atualizar(empresa);
    }

    @Override
    public void excluir(Empresa empresa) {
        empresaDAO.excluir(empresa);
    }
}