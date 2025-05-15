/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
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

    public ControllerPesquisa(PesquisaView view) {
        this.view = view;
        this.musicaDAO = new MusicaDAO();
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
            // Aqui você pode adicionar um JOptionPane para avisar o usuário
            System.out.println("Nenhum filtro selecionado.");
            return;
        }

        ArrayList<Musica> resultados = musicaDAO.buscarPorFiltro(termo, filtro);

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
}
