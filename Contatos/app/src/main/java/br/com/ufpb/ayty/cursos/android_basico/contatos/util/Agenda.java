package br.com.ufpb.ayty.cursos.android_basico.contatos.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raphael on 03/05/2015.
 * SDK ${VERSION_SDK}
 */
public class Agenda {

    private List<Contato> contatos;
    private static Agenda instance = null;

    private Agenda(){
        contatos = new ArrayList<>();
    }

    public static Agenda getInstance(){
        if(instance == null){
            instance = new Agenda();
        }
        return instance;
    }

    public void addContato(Contato contato){
        contatos.add(contato);
    }

    public void removerContato(Contato contato){
        contatos.remove(contato);
    }

    public List<Contato> getContatos(){
        return contatos;
    }

    public Contato[] searchContact(String s){
        List<Contato> contatosSearch = new ArrayList<>();
        for (Contato c : contatos){
            String nome = c.getNome().toLowerCase() + " " +c.getSobrenome().toLowerCase();
            if(nome.startsWith(s.toLowerCase())){
                contatosSearch.add(c);
            }
        }
        Contato[] contatos1 = new Contato[contatosSearch.size()];
        for(int i = 0; i < contatos1.length; i++){
            contatos1[i] = contatosSearch.get(i);
        }
        return contatos1;
    }

}
