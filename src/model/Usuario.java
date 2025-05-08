/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author unifmpassarelli
 */
public class Usuario extends Pessoa {
    private String senha;
    ArrayList<Playlist> Playlists = new ArrayList<>();

    public Usuario(String nome, String email, String senha) {
        super(nome, email);
        this.senha = senha;
    }


    
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
}
