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
import model.Usuario;

public class UsuarioDAO {
    private Connection conn;

    public UsuarioDAO(Connection conn) {
        this.conn = conn;
    }

    public UsuarioDAO() {
         // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void inserir(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario(nomeUsuario, emailUsuario, senhaUsuario) "
                + "VALUES ('"+ usuario.getNome() +"',"
                + " '"+ usuario.getEmail() +"',"
                + " '"+ usuario.getSenha() +"')";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.execute();
        conn.close();
    }
    
    public ResultSet consultar(Usuario usuario) throws SQLException{
        String sql = "select * from usuario where emailUsuario = ? and senhaUsuario = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, usuario.getEmail());
        statement.setString(2, usuario.getSenha());
        statement.execute();
        ResultSet resultado = statement.getResultSet();
        return resultado;
    }

    // Outros métodos como findById, update, delete...
}

