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
    
    
    public ArrayList<Musica> buscarPorFiltro(String termo, String filtro) {
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
                
                listaMusicas.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaMusicas;
    }
}
