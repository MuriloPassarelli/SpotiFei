/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import DAO.HistoricoDAO;
import DAO.MusicaDAO;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Musica;
import view.HistoricoView;
/**
 * ControllerHistoricoEInfos gerencia as ações 
 * relacionadas a tela de gerenciar historico de pesquisas
 * e ver musicas curtidas e descurtidas.
 * @author unifmpassarelli
 */
public class ControllerHistoricoEInfos {
    private HistoricoDAO historicoDAO;
    private MusicaDAO musicaDAO;
    private HistoricoView view;
    private ArrayList<Integer> idsMusicasCurtidas;
    private ArrayList<Integer> idsMusicasDescurtidas;

    public int getIdMusicaCurtida(int index) {
        return idsMusicasCurtidas.get(index);
    }

    public int getIdMusicaDescurtida(int index) {
        return idsMusicasDescurtidas.get(index);
    }


    public ControllerHistoricoEInfos(HistoricoView view) {
        this.view = view;
        this.historicoDAO = new HistoricoDAO();
        this.musicaDAO = new MusicaDAO();
    }

    public void atualizarListaHistorico(JList<String> lista, String emailUsuario) {
        ArrayList<String> historico = historicoDAO.listarHistorico(emailUsuario);

        DefaultListModel<String> model = new DefaultListModel<>();
        for (String termo : historico) {
            model.addElement(termo);
        }

        lista.setModel(model);
        
    }

    public void removerPesquisa(String emailUsuario, String termo) {
        historicoDAO.removerDoHistorico(emailUsuario, termo);
    }
    
    public void atualizarTabelaCurtidas(JTable tabela, String emailUsuario) {
        ArrayList<Musica> lista = musicaDAO.listarMusicasCurtidas(emailUsuario);
        DefaultTableModel model = criarModeloTabela(lista);
        tabela.setModel(model);
        
        this.idsMusicasCurtidas = new ArrayList<>();
        for (Musica m : lista) {
            idsMusicasCurtidas.add(m.getIdmusica());
        }
    }

    public void atualizarTabelaDescurtidas(JTable tabela, String emailUsuario) {
        ArrayList<Musica> lista = musicaDAO.listarMusicasDescurtidas(emailUsuario);
        DefaultTableModel model = criarModeloTabela(lista);
        tabela.setModel(model);
        
        this.idsMusicasDescurtidas = new ArrayList<>();
        for (Musica m : lista) {
            idsMusicasDescurtidas.add(m.getIdmusica());
        }
    }
    
    private DefaultTableModel criarModeloTabela(ArrayList<Musica> lista) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Título");
        model.addColumn("Gênero");
        model.addColumn("Data");
        model.addColumn("Artista");

        for (Musica m : lista) {
            model.addRow(new Object[]{
                m.getTitulomusica(),
                m.getGeneromusica(),
                m.getDatalancamento(),
                m.getArtista().getNome()
            });
        }

        return model;
    }
    
    public void removerMusicaCurtida(String emailUsuario, int idMusica) {
        musicaDAO.removerDasCurtidas(emailUsuario, idMusica);
    }

    public void removerMusicaDescurtida(String emailUsuario, int idMusica) {
        musicaDAO.removerDasDescurtidas(emailUsuario, idMusica);
    }

}

