/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.PlaylistDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import model.Musica;
import model.Playlist;
import view.GerenciarPlaylistsView;

/**
 * ControllerPlaylist gerencia as ações 
 * relacionadas a tela de gerenciar playlists
 * podendo criar, excluir, renomear e adicionar e remover musicas.
 * @author unifmpassarelli
 */
public class ControllerPlaylist {
    private PlaylistDAO playlistDAO;
    private GerenciarPlaylistsView view;    
    private String novoTituloTemporario = null;
    private ArrayList<Playlist> playlistsDoUsuario = new ArrayList<>();
    private ArrayList<Musica> ultimosResultadosBusca = new ArrayList<>();
    private ArrayList<Musica> musicasEmEdicao = new ArrayList<>();

    
    public ControllerPlaylist(GerenciarPlaylistsView view) {
        this.view = view;
        this.playlistDAO = new PlaylistDAO();
    }
        
    public ArrayList<Musica> getUltimosResultadosBusca() {
        return ultimosResultadosBusca;
    }
    
    public ArrayList<Musica> getMusicasEmEdicao() {
        return new ArrayList<>(musicasEmEdicao);
    }
    
    public ArrayList<Playlist> atualizarSuasPlaylists(
            JList<String> lista, String emailUsuario) {
        playlistsDoUsuario = playlistDAO.listarPlaylists(emailUsuario);
        DefaultListModel<String> model = new DefaultListModel<>();

        for (Playlist p : playlistsDoUsuario) { 
            model.addElement(p.getTituloplaylist());
        }

        lista.setModel(model);
        return playlistsDoUsuario;
    }

    public boolean criarPlaylist(String titulo, String emailUsuario) {
        if (titulo == null || titulo.trim().isEmpty()) {
            return false;
        }

        Playlist novaPlaylist = new Playlist(titulo.trim(), emailUsuario);
        return playlistDAO.criarPlaylist(novaPlaylist, emailUsuario);
    }
    
    public boolean deletarPlaylist(int idPlaylist) {
        return playlistDAO.deletarPlaylist(idPlaylist);
    }

    public void aplicarNovoTituloTemporario(String novoTitulo) {
        this.novoTituloTemporario = novoTitulo;
    }

    public boolean salvarAlteracoes(int idPlaylist) {
        boolean sucessoTitulo = true;
        if (novoTituloTemporario != null) {
            sucessoTitulo = playlistDAO.atualizarTituloPlaylist(idPlaylist, novoTituloTemporario);
            if (sucessoTitulo) {
                novoTituloTemporario = null;
            } else {
                return false;
            }
        }

        boolean sucessoMusicas = playlistDAO.limparMusicasDaPlaylist(idPlaylist);
        
        if (!sucessoMusicas) {
            return false;
        }

        for (Musica m : musicasEmEdicao) {
            boolean inseriu = playlistDAO.adicionarMusicaNaPlaylist(idPlaylist, m.getIdmusica());
            if (!inseriu) {
                return false; 
            }
        }

        return true; 
    }
    
    public void carregarMusicasDaPlaylist(int idPlaylist, JList<String> lista) {
        ArrayList<Musica> musicas = playlistDAO.listarMusicasDaPlaylist(idPlaylist);

        musicasEmEdicao.clear();
        musicasEmEdicao.addAll(musicas);
        
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Musica m : musicas) {
            model.addElement(m.getTitulomusica());
        }

        lista.setModel(model);
    }
    
    public int getIdPlaylistSelecionada(int indexSelecionado) {
        if (indexSelecionado >= 0 && indexSelecionado < playlistsDoUsuario.size()) {
            return playlistsDoUsuario.get(indexSelecionado).getIdplaylist();
        }
        return -1;
    }
    
    public void atualizarListaMusicas(JList<String> lista, 
            String emailUsuario) throws SQLException {
        String termo = view.getjTextField6().getText().trim();
                
        ultimosResultadosBusca = playlistDAO.buscarPorTitulo(termo, emailUsuario);

        DefaultListModel<String> model = new DefaultListModel<>();
        for (Musica m : ultimosResultadosBusca) {
            model.addElement(m.getTitulomusica());
        }

        lista.setModel(model); 
    }
    
    public void adicionarMusicaNaEdicao(Musica musica, DefaultListModel<String> modelLista) {
        if (musica != null && !musicasEmEdicao.contains(musica)) {
            musicasEmEdicao.add(musica);
            modelLista.addElement(musica.getTitulomusica());
        }
    }

    public void removerMusicaNaEdicao(int index, DefaultListModel<String> modelLista) {
        if (index >= 0 && index < musicasEmEdicao.size()) {
            musicasEmEdicao.remove(index);
            modelLista.remove(index);
        }
    }

}
