package br.com.tcs.treinamento.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class EmpresaVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nome;
    private String email;
    private Date data;
    private String tipoDocumento; // "CPF" ou "CNPJ"
    private String numeroCNPJ;
    private String motivoManutencao;
    private Date dataManutencao;
    private Boolean ativo = true;

    public EmpresaVO(Long id,String nome,String email, Date data,String tipoDocumento, String numeroCNPJ, Date dataManutencao, String motivoManutencao, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.data = data;
        this.tipoDocumento = tipoDocumento;
        this.numeroCNPJ = numeroCNPJ;
        this.dataManutencao = dataManutencao;
        this.motivoManutencao = motivoManutencao;
        this.ativo = ativo;
    }

    public EmpresaVO() {
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getNumeroCNPJ() {
        return numeroCNPJ;
    }

    public void setNumeroCNPJ(String numeroCNPJ) {
        this.numeroCNPJ = numeroCNPJ;
    }

    public String getMotivoManutencao() {
        return motivoManutencao;
    }

    public void setMotivoManutencao(String motivoManutencao) {
        this.motivoManutencao = motivoManutencao;
    }

    public Date getDataManutencao() {
        return dataManutencao;
    }

    public void setDataManutencao(Date dataManutencao) {
        this.dataManutencao = dataManutencao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EmpresaVO empresaVO = (EmpresaVO) o;
        return Objects.equals(id, empresaVO.id) && Objects.equals(nome, empresaVO.nome) && Objects.equals(email, empresaVO.email) && Objects.equals(data, empresaVO.data)&& Objects.equals(numeroCNPJ, empresaVO.numeroCNPJ) && Objects.equals(motivoManutencao, empresaVO.motivoManutencao) && Objects.equals(dataManutencao, empresaVO.dataManutencao) && Objects.equals(ativo, empresaVO.ativo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, email, data, numeroCNPJ, motivoManutencao, dataManutencao, ativo);
    }
}
