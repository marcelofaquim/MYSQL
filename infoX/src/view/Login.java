package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.DAO;

import java.awt.Toolkit;
import java.sql.Connection;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtSenha;
	private JLabel lblStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
			}
		});
		setTitle("infox - Login");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/pc.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 417, 244);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Usuario");
		lblNewLabel.setBounds(33, 39, 46, 14);
		contentPane.add(lblNewLabel);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(89, 36, 194, 20);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Senha");
		lblNewLabel_1.setBounds(33, 78, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(89, 75, 194, 20);
		contentPane.add(txtSenha);
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.setBounds(33, 132, 89, 23);
		contentPane.add(btnEntrar);
		
		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/dberror.png")));
		lblStatus.setBounds(285, 132, 32, 32);
		contentPane.add(lblStatus);
	} // fim do construtor
	
	/**
	 * Método Responsavel pela exibição do status de conexão
	 */
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
