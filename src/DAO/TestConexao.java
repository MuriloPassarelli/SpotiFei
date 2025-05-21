/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.Connection;

/**
 * Classe DAO criada unicamente para testes de conexão simples.
 * @author unifmpassarelli
 */
public class TestConexao {
    public static void main(String[] args) {
        try{
            Conexao conexao = new Conexao();
            Connection conn = conexao.getConnection();
            System.out.println("Conexxão bem sucedida!");
            conn.close();
        }catch(Exception e){
            System.out.println("Erro ao conectar: " + e.getMessage());
        }
    }
}
