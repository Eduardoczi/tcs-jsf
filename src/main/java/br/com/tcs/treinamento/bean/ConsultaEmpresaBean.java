package br.com.tcs.treinamento.bean;

import br.com.tcs.treinamento.entity.Empresa;
import br.com.tcs.treinamento.entity.Pessoa;
import br.com.tcs.treinamento.service.EmpresaService;
import br.com.tcs.treinamento.service.PessoaService;
import br.com.tcs.treinamento.service.impl.EmpresaServiceImpl;
import br.com.tcs.treinamento.service.impl.PessoaServiceImpl;
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
    private Empresa empresaSelecionado;
    private String errorMessage;
    private Long empresaId;
    private Boolean tpManutencao;

    private transient EmpresaService empresaService = new EmpresaServiceImpl();

    @PostConstruct
    public void init() {
        // Recupera parâmetro "pessoaId" da URL
        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap();
        String idParam = params.get("empresaId");
        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                empresaId = Long.valueOf(idParam);
                empresaSelecionado = empresaService.buscarPorId(empresaId);
            } catch (NumberFormatException e) {
                errorMessage = "ID inválido da empresa.";
            }
        }
        // Recupera o parâmetro tpManutencao; se não existir, assume um valor padrão (por exemplo, true para edição)
        String tpParam = params.get("tpManutencao");
        if (tpParam != null && !tpParam.trim().isEmpty()) {
            setTpManutencao(Boolean.valueOf(tpParam));
        } else {
            setTpManutencao(true);
        }
        empresas = empresaService.listar();
    }

    public String prepararEdicao(Empresa empresa) {
        this.empresaSelecionado = empresa;
        return "alterar?faces-redirect=true&pessoaId=" + empresa.getId() + "&tpManutencao=true";
    }

    public void prepararEdicaoArea(Empresa empresa) {
        this.empresaSelecionado = empresa;
    }

    public String prepararExclusao(Empresa empresa) {
        this.empresaSelecionado = empresa;
        return "excluir?faces-redirect=true&pessoaId=" + empresa.getId() + "&tpManutencao=false";
    }

    public String atualizarConsulta() {
        empresaService.atualizar(empresaSelecionado);
        empresas = empresaService.listar();
        return "consultaPessoas?faces-redirect=true";
    }

    public void limparAlteracoes() {
        if (empresaSelecionado != null) {
            empresaSelecionado = empresaService.buscarPorId(empresaSelecionado.getId());
        }
    }

    /**
     * Método que converte o VO para a entidade e chama o service para persistir.
     * Após persistir, exibe o popup de sucesso.
     */
    public void confirmar() {
        // Converte o VO para a entidade Pessoa
        Empresa empresa = mapEmpresaEntity();
        // Chama o service para persistir a entidade
        try {
            empresaService.atualizar(empresa);
            // Exibe o popup de sucesso após a confirmação
            PrimeFaces.current().executeScript("PF('successDialog').show();");
        } catch (Exception e) {
            // Em caso de erro na persistência, exibe o diálogo de erro
            errorMessage = "Erro ao cadastrar pessoa: " + e.getMessage();
            PrimeFaces.current().executeScript("PF('errorDialog').show();");
            return;
        }
    }

    /**
     * mapPessoaEntity
     * Mapeamento da VO para Entity
     */
    private Empresa mapEmpresaEntity() {
        Empresa empresa = new Empresa();
        empresa.setId(empresaSelecionado.getId());
        empresa.setNome(empresaSelecionado.getNome());
        empresa.setEmail(empresaSelecionado.getEmail());
        empresa.setData(empresaSelecionado.getData());
        empresa.setNumeroCNPJ(empresaSelecionado.getNumeroCNPJ());
        empresa.setDataManutencao(new Date());
        empresa.setAtivo(getTpManutencao());
        return empresa;
    }

    public void confirmarExclusao(){
        Empresa empresa = mapEmpresaEntity();
        try {
            empresaService.atualizar(empresa); //Exclusao logica
            //pessoaService.excluir(pessoa); // Exclusao fisica
            // Exibe o popup de sucesso após a confirmação
            PrimeFaces.current().executeScript("PF('successDialog').show();");
        } catch (Exception e) {
            // Em caso de erro na persistência, exibe o diálogo de erro
            errorMessage = "Erro ao cadastrar pessoa: " + e.getMessage();
            PrimeFaces.current().executeScript("PF('errorDialog').show();");
            return;
        }
    }

    public void validarCampos() {
        List<String> erros = new ArrayList<>();

        if (empresaSelecionado.getNome() == null || empresaSelecionado.getNome().trim().isEmpty()) {
            erros.add("Nome não informado.");
        }

        if (empresaSelecionado.getEmail() == null || empresaSelecionado.getEmail().trim().isEmpty()) {
            erros.add("E-mail não informado.");
        }
        if (empresaSelecionado.getData() == null) {
            erros.add("Data de fundação não informada.");
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

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
    }

    public Empresa getEmpresaSelecionado() {
        return empresaSelecionado;
    }

    public void setEmpresaSelecionado(Empresa empresaSelecionado) {
        this.empresaSelecionado = empresaSelecionado;
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