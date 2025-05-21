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
import model.Artista;
import model.Data;
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
    
    public ArrayList<Playlist> listarPlaylists(String emailUsuario) {
        ArrayList<Playlist> playlists = new ArrayList<>();
        String sql = "SELECT * FROM playlist WHERE emailusuario = ?";

        try (Connection conn = conexao.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, emailUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                playlists.add(new Playlist(
                    rs.getInt("idplaylist"),
                    rs.getString("tituloplaylist"),
                    rs.getString("emailusuario")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return playlists;
    }
    
    public boolean criarPlaylist(Playlist playlist, String emailusuario) {
        String sql = "INSERT INTO playlist (tituloplaylist, emailusuario) VALUES (?, ?)";

        try (Connection conn = conexao.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, playlist.getTituloplaylist());
            stmt.setString(2, emailusuario);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletarPlaylist(int idPlaylist) {
        String sql = "DELETE FROM playlist WHERE idplaylist = ?";

        try (Connection conn = conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPlaylist);
            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean atualizarTituloPlaylist(int idPlaylist, String novoTitulo) {
        String sql = "UPDATE playlist SET tituloplaylist = ? WHERE idplaylist = ?";

        try (Connection conn = conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoTitulo);
            stmt.setInt(2, idPlaylist);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public ArrayList<Musica> listarMusicasDaPlaylist(int idPlaylist) {
        ArrayList<Musica> musicas = new ArrayList<>();
        String sql = "SELECT m.idmusica, m.titulomusica, m.generomusica, "
                + "m.datalancamento, a.emailartista, a.nomeartista " +
                    "FROM musica m " +
                    "JOIN artistamusica am ON m.idmusica = am.idmusica " +
                    "JOIN artista a ON a.emailartista = am.emailartista " +
                    "JOIN musicaplaylist mp ON m.idmusica = mp.idmusica " +
                    "WHERE mp.idplaylist = ?";

        try (Connection conn = conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPlaylist);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Musica m = new Musica();
                m.setIdmusica(rs.getInt("idmusica"));
                m.setTitulomusica(rs.getString("titulomusica"));
                m.setGeneromusica(rs.getString("generomusica"));
                
                String dataSql = rs.getString("datalancamento");
                String[] partesData = dataSql.split("-");
                int ano = Integer.parseInt(partesData[0]);
                int mes = Integer.parseInt(partesData[1]);
                int dia = Integer.parseInt(partesData[2]);
                
                Data data = new Data();
                data.setDia(dia);
                data.setMes(mes);
                data.setAno(ano);
                m.setDatalancamento(data);
                
                Artista artista = new Artista();
                artista.setNome(rs.getString("nomeartista"));
                artista.setEmail(rs.getString("emailartista"));
                m.setArtista(artista);
                
                musicas.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return musicas;
    }
    
    public ArrayList<Musica> buscarPorTitulo(String termo,
            String emailUsuario) throws SQLException {
        ArrayList<Musica> listaMusicas = new ArrayList<>();

        String sql = "SELECT m.idmusica, m.titulomusica, m.generomusica, "
                + "m.datalancamento, a.emailartista, a.nomeartista " +
                "FROM musica m " +
                "JOIN artistamusica am ON m.idmusica = am.idmusica " +
                "JOIN artista a ON am.emailartista = a.emailartista " +
                "WHERE m.titulomusica ILIKE ?";
        
        try (Connection conn = conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + termo + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Musica m = new Musica();
                m.setIdmusica(rs.getInt("idmusica"));
                m.setTitulomusica(rs.getString("titulomusica"));
                m.setGeneromusica(rs.getString("generomusica"));

                String dataSql = rs.getString("datalancamento");
                String[] partesData = dataSql.split("-");
                int ano = Integer.parseInt(partesData[0]);
                int mes = Integer.parseInt(partesData[1]);
                int dia = Integer.parseInt(partesData[2]);

                Data data = new Data();
                data.setDia(dia);
                data.setMes(mes);
                data.setAno(ano);
                m.setDatalancamento(data);

                Artista artista = new Artista();
                artista.setNome(rs.getString("nomeartista"));
                artista.setEmail(rs.getString("emailartista"));
                m.setArtista(artista);

                listaMusicas.add(m);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaMusicas;
    }
    
    public boolean limparMusicasDaPlaylist(int idPlaylist) {
        String sql = "DELETE FROM musicaplaylist WHERE idplaylist = ?";
        try (Connection conn = conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idPlaylist);
            int linhas = stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean adicionarMusicaNaPlaylist(int idPlaylist, int idMusica) {
        String sql = "INSERT INTO musicaplaylist(idplaylist, idmusica) VALUES (?, ?)";
        try (Connection conn = conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idPlaylist);
            stmt.setInt(2, idMusica);
            int linhas = stmt.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
