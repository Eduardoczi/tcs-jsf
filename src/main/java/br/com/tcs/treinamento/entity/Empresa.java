package br.com.tcs.treinamento.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "Empresa")
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;

    // Identificador da entidade
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome da pessoa (obrigatório)
    @Column(nullable = false)
    private String nome;

    // E-mail (obrigatório)
    @Column(nullable = false)
    private String email;

    // Data de fundação
    @Temporal(TemporalType.DATE)
    private Date data;

    // Indica se o documento é CPF ou CNPJ
    private String tipoDocumento;

    // Número do CNPJ (caso tipoDocumento seja CNPJ)
    private String numeroCNPJ;

    // Motivo da manutenção (alteração, exclusão ou desativação)
    private String motivoManutencao;

    // Data da última manutenção (grava data e hora)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataManutencao;

    // Flag para exclusão lógica (soft delete)
    private Boolean ativo = true;

    // Construtor padrão
    public Empresa() {
    }

    // Construtor completo (opcional para facilitar a criação)
    public Empresa(String nome, String email, Date data,String tipoDocumento,
                  String numeroCNPJ, String motivoManutencao, Date dataManutencao, Boolean ativo) {
        this.nome = nome;
        this.email = email;
        this.data = data;
        this.tipoDocumento = tipoDocumento;
        this.numeroCNPJ = numeroCNPJ;
        this.motivoManutencao = motivoManutencao;
        this.dataManutencao = dataManutencao;
        this.ativo = ativo;
    }

    // Getters e Setters


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


    // equals e hashCode baseados no id (único identificador)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empresa empresa = (Empresa) o;
        return Objects.equals(id, empresa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Método toString opcional para facilitar a depuração

    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", data=" + data +
                ", numeroCNPJ='" + numeroCNPJ + '\'' +
                ", motivoManutencao='" + motivoManutencao + '\'' +
                ", dataManutencao=" + dataManutencao +
                ", ativo=" + ativo +
                '}';
    }
}