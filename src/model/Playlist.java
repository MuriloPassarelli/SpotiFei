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

public class Playlist {
    private int idplaylist;
    private String tituloplaylist;
    private String emailusuario;

    public Playlist(int idplaylist, String tituloplaylist) {
        this.idplaylist = idplaylist;
        this.tituloplaylist = tituloplaylist;
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

    
}
