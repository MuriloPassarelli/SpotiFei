/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import DAO.PessoaDAO;
import DAO.Conexao;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Usuario;
import view.Cadastro;
/**
 *
 * @author unifmpassarelli
 */
public class ControllerCadastro {
    private Cadastro view;

    public ControllerCadastro(Cadastro view) {
        this.view = view;
    }
}
