/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 * ControllerPesquisa gerencia as ações 
 * relacionadas a tela de pesquisa de musicas
 * e curtir de descurtir musicas.
 * @author unifmpassarelli
 */
import DAO.MusicaDAO;
import model.Musica;
import view.PesquisaView;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ControllerPesquisa {
    private PesquisaView view;
    private MusicaDAO musicaDAO;
    private String emailUsuario;

    public ControllerPesquisa(PesquisaView view, String emailUsuario) {
        this.view = view;
        this.musicaDAO = new MusicaDAO();
        this.emailUsuario = emailUsuario;
        
        view.getjButton3().addActionListener(e -> curtirMusicaSelecionada());
        view.getjButton4().addActionListener(e -> descurtirMusicaSelecionada());
    }

    public void pesquisar() {
        String termo = view.getjTextField1().getText();
        String filtro = "";

        if (view.getjToggleButton1().isSelected()) {
            filtro = "nome";
        } else if (view.getjToggleButton2().isSelected()) {
            filtro = "artista";
        } else if (view.getjToggleButton3().isSelected()) {
            filtro = "genero";
        }

        if (filtro.isEmpty()) {
            System.out.println("Nenhum filtro selecionado.");
            return;
        }

        ArrayList<Musica> resultados = musicaDAO.buscarPorFiltro(termo, filtro, emailUsuario);

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Título", "Gênero", "Data", "Artista"});

        for (Musica m : resultados) {
            String dataFormatada = String.format("%02d/%02d/%04d",
                    m.getDatalancamento().getDia(),
                    m.getDatalancamento().getMes(),
                    m.getDatalancamento().getAno());

            model.addRow(new Object[]{
                    m.getTitulomusica(),
                    m.getGeneromusica(),
                    dataFormatada,
                    m.getArtista().getNome()
            });
        }

        view.getjTable1().setModel(model);
    }

    private void curtirMusicaSelecionada() {
        int row = view.getjTable1().getSelectedRow();
        if (row == -1) {
            view.getjLabel8().setText("Nenhuma música selecionada.");
            return;
        }

        String titulo = (String) view.getjTable1().getValueAt(row, 0);
        ArrayList<Musica> musicas = musicaDAO.buscarPorFiltro(titulo, "nome", emailUsuario);
        if (musicas.isEmpty()) {
            view.getjLabel8().setText("Música não encontrada.");
            return;
        }

        Musica musica = musicas.get(0);
        musicaDAO.curtirMusica(emailUsuario, musica.getIdmusica());
        view.getjLabel8().setText("Música curtida!");
    }

    private void descurtirMusicaSelecionada() {
        int row = view.getjTable1().getSelectedRow();
        if (row == -1) {
            view.getjLabel8().setText("Nenhuma música selecionada.");
            return;
        }

        String titulo = (String) view.getjTable1().getValueAt(row, 0);
        ArrayList<Musica> musicas = musicaDAO.buscarPorFiltro(titulo, "nome", emailUsuario);
        if (musicas.isEmpty()) {
            view.getjLabel8().setText("Música não encontrada.");
            return;
        }

        Musica musica = musicas.get(0);
        musicaDAO.descurtirMusica(emailUsuario, musica.getIdmusica());
        view.getjLabel8().setText("Música descurtida!");
    }

}
