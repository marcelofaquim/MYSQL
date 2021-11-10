package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.DAO;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginProvisorio extends JFrame {
	private JTextField txtLogin;
	private JPasswordField txtSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginProvisorio frame = new LoginProvisorio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginProvisorio() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginProvisorio.class.getResource("/img/pc.png")));
		setTitle("Login - Infox");
		setResizable(false);
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setBounds(10, 46, 46, 14);
		getContentPane().add(lblNewLabel);

		txtLogin = new JTextField();
		txtLogin.setBounds(60, 43, 261, 20);
		getContentPane().add(txtLogin);
		txtLogin.setColumns(10);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(60, 90, 261, 20);
		getContentPane().add(txtSenha);

		JLabel lblNewLabel_1 = new JLabel("Senha");
		lblNewLabel_1.setBounds(10, 93, 46, 14);
		getContentPane().add(lblNewLabel_1);

		JButton btnEntra = new JButton("Entrar");
		btnEntra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});
		btnEntra.setBounds(146, 146, 89, 23);
		getContentPane().add(btnEntra);
		
		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(LoginProvisorio.class.getResource("/img/dberror.png")));
		lblStatus.setBounds(352, 146, 32, 32);
		getContentPane().add(lblStatus);

	} // FIm do Construtor

	DAO dao = new DAO();
	private JLabel lblStatus;
	

	private void logar() {
		if(txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Login", " Atenção!",JOptionPane.WARNING_MESSAGE);
			txtLogin.requestFocus();
		} else if (txtSenha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, " Preencha a Senha", " Atenção!", JOptionPane.WARNING_MESSAGE);
		} else {
			try {
				String read = "select * from usuarios where login=? and senha=md5(?)";
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(read);
				pst.setString(1, txtLogin.getText());
				pst.setString(2, txtSenha.getText());
				
				ResultSet rs = pst.executeQuery();
				
				if (rs.next()) {
					
					String perfil = rs.getString(5);
					System.out.println(perfil);
					
					
					if (perfil.equals("Administrador")) {
						Principal principal = new Principal();
						principal.setVisible(true);
						
						principal.btnRelatorios.setEnabled(true);
						principal.btnUsuarios.setEnabled(true);
						
						
						this.dispose();
					}	else  {
						Principal principal = new Principal();
						principal.setVisible(true);
						this.dispose();
					}
							
					
				}else {
						JOptionPane.showMessageDialog(null, " Login e/ou senha inválido(s)", " Atenção!", JOptionPane.WARNING_MESSAGE);
					}
					con.close();
			} catch (Exception e) {
					System.out.println(e);
			}
		}
}

private void status() {
	// criar um objeto de modo DAO para acessar com método na classe DAO
	DAO dao = new DAO();
	try {
		// Abrir a conexão com o banco
		Connection con = dao.conectar();
		// A linha abaixo exibe o retorno da conexão
		System.out.println(con);
		// mudando o icone do rodapé no caso do banco de dados estar disponivel
		if(con==null) {
			lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dberror.png")));
		} else {
			lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dbok.png")));
		}
		

		// IMPORTANTE!! Sempre fechar a conexão
		con.close();
	} catch (Exception e) {
		System.out.println(e);
	}
}
}



