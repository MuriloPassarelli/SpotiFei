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
import model.Pessoa;

public class PessoaDAO {
    private Connection conn;

    public PessoaDAO(Connection conn) {
        this.conn = conn;
    }

    public void inserir(Pessoa pessoa) throws SQLException {
        String sql = "INSERT INTO pessoas(nome, sobrenome, senha, email) "
                + "VALUES ('"+ pessoa.getNome() +"',"
                + " '"+ pessoa.getSobrenome() +"',"
                + " '"+ pessoa.getEmail() +"',"
                + " '"+ pessoa.getNascimento() +"')";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.execute();
        conn.close();
    }

    // Outros métodos como findById, update, delete...
}

