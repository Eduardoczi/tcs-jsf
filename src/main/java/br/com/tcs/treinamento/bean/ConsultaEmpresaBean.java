package br.com.tcs.treinamento.bean;

import br.com.tcs.treinamento.entity.Empresa;
import br.com.tcs.treinamento.service.EmpresaService;
import br.com.tcs.treinamento.service.impl.EmpresaServiceImpl;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "consultaEmpresaBean")
@ViewScoped
public class ConsultaEmpresaBean implements Serializable {

    private List<Empresa> empresas;

    private Empresa empresaSelecionada;
    private String errorMessage;
    private Long empresaId;
    private Boolean tpManutencao;

    private transient EmpresaService empresaService = new EmpresaServiceImpl();

    @PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap();
        String idParam = params.get("empresaId");
        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                empresaId = Long.valueOf(idParam);
                empresaSelecionada = empresaService.buscarPorId(empresaId);
            } catch (NumberFormatException e) {
                errorMessage = "ID inválido da empresa.";
            }
        }
        String tpParam = params.get("tpManutencao");
        if (tpParam != null && !tpParam.trim().isEmpty()) {
            setTpManutencao(Boolean.valueOf(tpParam));
        } else {
            setTpManutencao(true);
        }
        empresas = empresaService.listar();
    }

    public String prepararEdicao(Empresa empresa) {
        this.empresaSelecionada = empresa;
        return "alterarEmpresa?faces-redirect=true&empresaId=" + empresa.getId() + "&tpManutencao=true";
    }

    public void prepararEdicaoArea(Empresa empresa) {
        this.empresaSelecionada = empresa;
    }

    public String prepararExclusao(Empresa empresa) {
        this.empresaSelecionada = empresa;
        return "excluirEmpresa?faces-redirect=true&empresaId=" + empresa.getId() + "&tpManutencao=false";
    }

    public String atualizarConsulta() {
        empresaService.atualizar(empresaSelecionada);
        empresas = empresaService.listar();
        return "consultaEmpresas?faces-redirect=true";
    }

    public void limparAlteracoes() {
        if (empresaSelecionada != null) {
            empresaSelecionada = empresaService.buscarPorId(empresaSelecionada.getId());
        }
    }

    public void confirmar() {
        Empresa empresa = mapEmpresaEntity();
        try {
            empresaService.atualizar(empresa);
            PrimeFaces.current().executeScript("PF('successDialog').show();");
        } catch (Exception e) {
            errorMessage = "Erro ao cadastrar empresa: " + e.getMessage();
            PrimeFaces.current().executeScript("PF('errorDialog').show();");
        }
    }

    private Empresa mapEmpresaEntity() {
        Empresa empresa = new Empresa();
        empresa.setId(empresaSelecionada.getId());
        empresa.setNome(empresaSelecionada.getNome());
        empresa.setEmail(empresaSelecionada.getEmail());
        empresa.setData(empresaSelecionada.getData());
        empresa.setNumeroCNPJ(empresaSelecionada.getNumeroCNPJ());
        empresa.setDataManutencao(new Date());
        empresa.setAtivo(getTpManutencao());
        return empresa;
    }

    public void confirmarExclusao() {
        Empresa empresa = mapEmpresaEntity();
        try {
            empresaService.atualizar(empresa); // Exclusão lógica
            PrimeFaces.current().executeScript("PF('successDialog').show();");
        } catch (Exception e) {
            errorMessage = "Erro ao excluir empresa: " + e.getMessage();
            PrimeFaces.current().executeScript("PF('errorDialog').show();");
        }
    }

    public void validarCampos() {
        List<String> erros = new ArrayList<>();

        if (empresaSelecionada == null) {
            erros.add("Empresa não selecionada.");
        } else {
            if (empresaSelecionada.getNome() == null || empresaSelecionada.getNome().trim().isEmpty()) {
                erros.add("Nome não informado.");
            }
            if (empresaSelecionada.getEmail() == null || empresaSelecionada.getEmail().trim().isEmpty()) {
                erros.add("E-mail não informado.");
            }
            if (empresaSelecionada.getData() == null) {
                erros.add("Data de fundação não informada.");
            }
        }

        if (!erros.isEmpty()) {
            errorMessage = String.join("<br/>", erros);
            PrimeFaces.current().executeScript("PF('errorDialog').show();");
        } else {
            PrimeFaces.current().executeScript("PF('confirmDialog').show();");
        }
    }

    public void exportarPdf() {
        System.out.println("Implementar metodo para PDF");
    }

    public void exportarExcel() {
        System.out.println("Implementar metodo para Excel");
    }

    // Getters e Setters

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
    }

    public Empresa getEmpresaSelecionada() {
        return empresaSelecionada;
    }

    public void setEmpresaSelecionada(Empresa empresaSelecionada) {
        this.empresaSelecionada = empresaSelecionada;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public EmpresaService getEmpresaService() {
        return empresaService;
    }

    public void setEmpresaService(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Boolean getTpManutencao() {
        return tpManutencao;
    }

    public void setTpManutencao(Boolean tpManutencao) {
        this.tpManutencao = tpManutencao;
    }

}
