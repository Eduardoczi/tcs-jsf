package br.com.tcs.treinamento.bean;

import org.primefaces.PrimeFaces;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "mensagemBean")
@SessionScoped
public class MensagemBean {
    private String mensagem = "Mensagem padrão";

    public void abrirModal() {
        PrimeFaces.current().executeScript("PF('messageDialog').show();");
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}