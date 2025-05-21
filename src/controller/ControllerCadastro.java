/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.UsuarioDAO;
import DAO.Conexao;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Usuario;
import view.CadastroView;
/**
 *
 * @author unifmpassarelli
 */
public class ControllerCadastro {
    private CadastroView view;

    public ControllerCadastro(CadastroView view) {
        this.view = view;
    }
    
    public void salvarUsuario(){
        String nome = view.getjTextField1().getText();
        String email = view.getjTextField2().getText();
        String senha = view.getjPasswordField2().getText();
        
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Por favor, preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Usuario usuario = new Usuario(nome, email,senha);
        
        Conexao conexao = new Conexao();
        try {
            Connection conn = conexao.getConnection();
            UsuarioDAO dao = new UsuarioDAO(conn);
            dao.inserir(usuario);
            JOptionPane.showMessageDialog(view, "Usuario Cadastrado!","Aviso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Erro ao cadastrar o Usuário!","Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
