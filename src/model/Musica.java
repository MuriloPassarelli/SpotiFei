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
public class Musica {
    private int idmusica;
    private String titulomusica;
    private String generomusica;
    private Data datalancamento;
    private Artista artista;

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public int getIdmusica() {
        return idmusica;
    }

    public void setIdmusica(int idmusica) {
        this.idmusica = idmusica;
    }

    public String getTitulomusica() {
        return titulomusica;
    }

    public void setTitulomusica(String titulomusica) {
        this.titulomusica = titulomusica;
    }

    public String getGeneromusica() {
        return generomusica;
    }

    public void setGeneromusica(String generomusica) {
        this.generomusica = generomusica;
    }

    public Data getDatalancamento() {
        return datalancamento;
    }

    public void setDatalancamento(Data datalancamento) {
        this.datalancamento = datalancamento;
    }
    
    
}
