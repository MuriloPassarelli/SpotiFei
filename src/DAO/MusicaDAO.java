/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 * Classe DAO responsável por gerenciar a conexão com o banco de dados
 * relacionada às funções de manipulação de musicas
 * como pesquisar, listar, curtir e descurtir.
 * @author unifmpassarelli
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Musica;
import model.Artista;
import model.Data;

public class MusicaDAO {
    private Connection conn;
    Conexao conexao = new Conexao();

    public MusicaDAO(Connection conn) {
        this.conn = conn;
    }

    public MusicaDAO() {
         // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public ArrayList<Musica> buscarPorFiltro(String termo, String filtro,
            String emailUsuario) {
        ArrayList<Musica> listaMusicas = new ArrayList<>();

        String sqlBase = "SELECT m.idmusica, m.titulomusica, m.generomusica, "
                + "m.datalancamento, a.emailartista, a.nomeartista " +
                "FROM musica m " +
                "JOIN artistamusica am ON m.idmusica = am.idmusica " +
                "JOIN artista a ON am.emailartista = a.emailartista ";

        String condicao = switch (filtro) {
            case "nome" -> "WHERE m.titulomusica ILIKE ?";
            case "genero" -> "WHERE m.generomusica ILIKE ?";
            case "artista" -> "WHERE a.nomeartista ILIKE ?";
            default -> throw new IllegalArgumentException("Filtro inválido: " + filtro);
        };
        
        try (Connection conn = conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlBase + condicao)) {

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
                
                registrarNoHistorico(emailUsuario, termo);

                listaMusicas.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaMusicas;
    }
    
    private ArrayList<Musica> buscarPorQuery(String sql, String emailUsuario) {
        ArrayList<Musica> lista = new ArrayList<>();
        try (Connection conn = conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, emailUsuario);
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

                lista.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public void curtirMusica(String emailUsuario, int idMusica) {
        try (Connection conn = conexao.getConnection()) {
            PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM "
                    + "MusicasDescurtidas WHERE emailusuario = ? "
                    + "AND idmusica = ?");
            stmt1.setString(1, emailUsuario);
            stmt1.setInt(2, idMusica);
            stmt1.executeUpdate();

            PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO "
                    + "MusicasCurtidas (emailusuario, idmusica) VALUES (?, ?) "
                    + "ON CONFLICT DO NOTHING");
            stmt2.setString(1, emailUsuario);
            stmt2.setInt(2, idMusica);
            stmt2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void descurtirMusica(String emailUsuario, int idMusica) {
        try (Connection conn = conexao.getConnection()) {
            PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM "
                    + "MusicasCurtidas WHERE emailusuario = ? AND idmusica = ?");
            stmt1.setString(1, emailUsuario);
            stmt1.setInt(2, idMusica);
            stmt1.executeUpdate();

            PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO "
                    + "MusicasDescurtidas (emailusuario, idmusica) VALUES (?, ?)"
                    + " ON CONFLICT DO NOTHING");
            stmt2.setString(1, emailUsuario);
            stmt2.setInt(2, idMusica);
            stmt2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }   
    
    public void removerDasCurtidas(String emailUsuario, int idmusica) {
        try (Connection conn = conexao.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM "
                    + "musicascurtidas WHERE emailusuario = ? "
                    + "AND idmusica = ?");
            stmt.setString(1, emailUsuario);
            stmt.setInt(2, idmusica);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void removerDasDescurtidas(String emailUsuario, int idmusica) {
        try (Connection conn = conexao.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM "
                    + "musicasdescurtidas WHERE emailusuario = ? "
                    + "AND idmusica = ?");
            stmt.setString(1, emailUsuario);
            stmt.setInt(2, idmusica);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Musica> listarMusicasCurtidas(String emailUsuario) {
        String sql = """
            SELECT m.idmusica, m.titulomusica, m.generomusica, m.datalancamento,
                   a.nomeartista, a.emailartista
            FROM musica m
            JOIN artistamusica am ON m.idmusica = am.idmusica
            JOIN artista a ON am.emailartista = a.emailartista
            JOIN musicascurtidas mc ON m.idmusica = mc.idmusica
            WHERE mc.emailusuario = ?
        """;
        return buscarPorQuery(sql, emailUsuario);
    }

    public ArrayList<Musica> listarMusicasDescurtidas(String emailUsuario) {
        String sql = """
            SELECT m.idmusica, m.titulomusica, m.generomusica, m.datalancamento,
                   a.nomeartista, a.emailartista
            FROM musica m
            JOIN artistamusica am ON m.idmusica = am.idmusica
            JOIN artista a ON am.emailartista = a.emailartista
            JOIN musicasdescurtidas md ON m.idmusica = md.idmusica
            WHERE md.emailusuario = ?
        """;
        return buscarPorQuery(sql, emailUsuario);
    }
    
    private void registrarNoHistorico(String emailUsuario, String termo) {
        String deleteDuplicata = "DELETE FROM historico WHERE emailusuario = ? "
                + "AND pesquisa = ?";
        String contarPesquisas = "SELECT COUNT(*) FROM historico WHERE emailusuario = ?";
        String deletarMaisAntiga = "DELETE FROM historico WHERE emailusuario = ? " +
                                    "AND datapesquisa = "
                + "(SELECT datapesquisa FROM historico " +
                                    "WHERE emailusuario = ? "
                + "ORDER BY datapesquisa ASC LIMIT 1)";
        String inserir = "INSERT INTO historico (emailusuario, pesquisa, "
                + "datapesquisa) VALUES (?, ?, now())";

        try (Connection conn = conexao.getConnection()) {
            conn.setAutoCommit(false); 
            
            try (PreparedStatement stmt = conn.prepareStatement(deleteDuplicata)) {
                stmt.setString(1, emailUsuario);
                stmt.setString(2, termo);
                stmt.executeUpdate();
            }

            int count = 0;
            try (PreparedStatement stmt = conn.prepareStatement(contarPesquisas)) {
                stmt.setString(1, emailUsuario);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }

            if (count >= 10) {
                try (PreparedStatement stmt = conn.prepareStatement(deletarMaisAntiga)) {
                    stmt.setString(1, emailUsuario);
                    stmt.setString(2, emailUsuario);
                    stmt.executeUpdate();
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(inserir)) {
                stmt.setString(1, emailUsuario);
                stmt.setString(2, termo);
                stmt.executeUpdate();
            }

            conn.commit(); 
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
