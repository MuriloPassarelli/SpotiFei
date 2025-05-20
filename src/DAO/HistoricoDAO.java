/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Artista;
import model.Data;
import model.Musica;

/**
 *
 * @author unifmpassarelli
 */
public class HistoricoDAO {
    private Connection conn;
    Conexao conexao = new Conexao();

    public HistoricoDAO(Connection conn) {
        this.conn = conn;
    }

    public HistoricoDAO() {
         // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public ArrayList<String> listarHistorico(String emailUsuario) {
        ArrayList<String> lista = new ArrayList<>();

        String sql = "SELECT pesquisa FROM historico WHERE emailusuario = ? "
               + "ORDER BY datapesquisa DESC";

        try (Connection conn = conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, emailUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(rs.getString("pesquisa"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    public void removerDoHistorico(String emailUsuario, String termo) {
        try (Connection conn = conexao.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM "
                    + "historico WHERE emailusuario = ? "
                    + "AND pesquisa = ?");
            stmt.setString(1, emailUsuario);
            stmt.setString(2, termo);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
