/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 * Classe padrão de P.O.O. para dados das playlists.
 * @author unifmpassarelli
 */

public class Playlist {
    private int idplaylist;
    private String tituloplaylist;
    private String emailusuario;
    private ArrayList<Musica> Musicas = new ArrayList<>();

    public Playlist(int idplaylist, String tituloplaylist, String emailusuario) {
        this.idplaylist = idplaylist;
        this.tituloplaylist = tituloplaylist;
        this.emailusuario = emailusuario;
    }
    
    public Playlist(String tituloplaylist, String emailusuario) {
        this.tituloplaylist = tituloplaylist;
        this.emailusuario = emailusuario;
    }

    public Playlist() {
    }

    public int getIdplaylist() {
        return idplaylist;
    }

    public void setIdplaylist(int idplaylist) {
        this.idplaylist = idplaylist;
    }

    public String getTituloplaylist() {
        return tituloplaylist;
    }

    public void setTituloplaylist(String tituloplaylist) {
        this.tituloplaylist = tituloplaylist;
    }

    public String getEmailusuario() {
        return emailusuario;
    }

    public void setEmailusuario(String emailusuario) {
        this.emailusuario = emailusuario;
    }

    public ArrayList<Musica> getMusicas() {
        return Musicas;
    }

    public void setMusicas(ArrayList<Musica> Musicas) {
        this.Musicas = Musicas;
    }
    
    @Override
    public String toString() {
        return this.tituloplaylist;
    }
}
