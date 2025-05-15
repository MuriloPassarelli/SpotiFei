/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author unifmpassarelli
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Musica;
import model.Playlist;
import model.Usuario;

public class PlaylistDAO {
    private Connection conn;
    Conexao conexao = new Conexao();
    
        public PlaylistDAO(Connection conn) {
        this.conn = conn;
    }

    public PlaylistDAO() {
         // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void criarPlaylist(Playlist playlist) {
        String sql = "INSERT INTO playlist (tituloplaylist, emailusuario) VALUES (?, ?)";

        try (Connection conn = conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, playlist.getTituloplaylist());
            stmt.setString(2, playlist.getEmailusuario());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
        public void editarPlaylist(Playlist playlist) {
        String sql = "UPDATE playlist SET tituloplaylist = ? WHERE idplaylist = ?";

        try (Connection conn = conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))  {

            stmt.setString(1, playlist.getTituloplaylist());
            stmt.setInt(2, playlist.getIdplaylist());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        
}
