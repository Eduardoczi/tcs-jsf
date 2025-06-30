package br.com.tcs.treinamento.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.tcs.treinamento.entity.Empresa;
import br.com.tcs.treinamento.model.EmpresaVO;
import br.com.tcs.treinamento.service.EmpresaService;
import br.com.tcs.treinamento.service.impl.EmpresaServiceImpl;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "cadastroEmpresa")
@ViewScoped
public class CadastroEmpresa  implements Serializable {
    private static final long serialVersionUID = 3450069247988201468L;

    private EmpresaVO cadastrarEmpresa = new EmpresaVO();

    private String errorMessage;

    private transient EmpresaService empresaService = new EmpresaServiceImpl();

    public void confirmar() {
        Empresa empresa = new Empresa();
        empresa.setNome(cadastrarEmpresa.getNome());
        empresa.setEmail(cadastrarEmpresa.getEmail());
        empresa.setData(cadastrarEmpresa.getData());
        empresa.setNumeroCNPJ(cadastrarEmpresa.getNumeroCNPJ());
        empresa.setAtivo(true);

        try {
            empresaService.cadastrar(empresa);
            PrimeFaces.current().executeScript("PF('successDialog').show();");
        } catch (Exception e) {
            errorMessage = "Erro ao cadastrar empresa: " + e.getMessage();
            PrimeFaces.current().executeScript("PF('errorDialog').show();");
        }
    }

    public void limpar() {
        cadastrarEmpresa.setNome(null);
        cadastrarEmpresa.setEmail(null);
        cadastrarEmpresa.setData(null);
        cadastrarEmpresa.setTipoDocumento(null);
        cadastrarEmpresa.setNumeroCNPJ(null);
        errorMessage = null;
    }

    public void validarCampos() {
        List<String> erros = new ArrayList<>();

        if (cadastrarEmpresa.getNome() == null || cadastrarEmpresa.getNome().trim().isEmpty()) {
            erros.add("Nome não informado.");
        }

        if (cadastrarEmpresa.getEmail() == null || cadastrarEmpresa.getEmail().trim().isEmpty()) {
            erros.add("E-mail não informado.");
        }
        if (cadastrarEmpresa.getData() == null) {
            erros.add("Data de nascimento não informada.");
        }
        if (cadastrarEmpresa.getNumeroCNPJ() == null || cadastrarEmpresa.getNumeroCNPJ().trim().isEmpty() ||
                cadastrarEmpresa.getNumeroCNPJ().trim().length() < 14) {
            erros.add("CNPJ não informado ou incompleto (deve conter 14 dígitos).");
        }

        if (!erros.isEmpty()) {
            errorMessage = String.join("<br/>", erros);
            PrimeFaces.current().executeScript("PF('errorDialog').show();");
        } else {
            PrimeFaces.current().executeScript("PF('confirmDialog').show();");
        }
    }


    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public EmpresaVO getCadastrarEmpresa() {
        return cadastrarEmpresa;
    }

    public void setCadastrarEmpresa(EmpresaVO cadastrarEmpresa) {
        this.cadastrarEmpresa = cadastrarEmpresa;
    }

    public EmpresaService getEmpresaService() {
        return empresaService;
    }

    public void setEmpresaService(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        this.empresaService = new EmpresaServiceImpl();
    }
}
