
package controller;

import DAO.UsuarioDAO;
import DAO.Conexao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Usuario;
import view.HomeView;
import view.LoginView;


public class ControllerLogin {
    private LoginView view;

    public ControllerLogin(LoginView view) {
        this.view = view;
    }
    
    public void loginAluno(){
        Usuario usuario = new Usuario(null, 
                                view.getjTextField1().getText(),
                                view.getjPasswordField1().getText());
        Conexao conexao = new Conexao();
        try{
            Connection conn = conexao.getConnection();
            UsuarioDAO dao = new UsuarioDAO(conn);
            ResultSet res = dao.consultar(usuario);
            if(res.next()){
                JOptionPane.showMessageDialog(view, 
                                              "Login efetuado!", 
                                              "Aviso",
                                              JOptionPane.INFORMATION_MESSAGE);
                Usuario usuario2 = new Usuario(res.getString("nomeUsuario"), 
                                         res.getString("emailUsuario"), 
                                         res.getString("senhaUsuario"));
                HomeView aec = new HomeView(usuario2);
                aec.setVisible(true);
                view.dispose();
            } else{
                JOptionPane.showMessageDialog(view, 
                                              "Login NÃO efetuado!\nEmail e/ou senha incorretos!", 
                                              "Aviso",
                                              JOptionPane.ERROR_MESSAGE);
            }
        } catch(SQLException e){    
            JOptionPane.showMessageDialog(view, 
                                              "Erro de conexão!", 
                                              "Aviso",
                                              JOptionPane.ERROR_MESSAGE);
        }
    }
}
