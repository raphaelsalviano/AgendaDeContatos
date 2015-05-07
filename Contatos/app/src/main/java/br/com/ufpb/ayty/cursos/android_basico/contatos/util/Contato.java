package br.com.ufpb.ayty.cursos.android_basico.contatos.util;

import java.io.Serializable;

/**
 * Created by Raphael on 03/05/2015.
 * SDK ${VERSION_SDK}
 */
public class Contato implements Serializable{

    private String nome;
    private String sobrenome;
    private String numero;
    private String email;

    public Contato(String nome, String sobrenome, String numero, String email) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.numero = numero;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
