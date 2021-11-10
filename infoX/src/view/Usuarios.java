package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Atxy2k.CustomTextField.RestrictedTextField;
import model.DAO;
import net.proteanit.sql.DbUtils;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;

public class Usuarios extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtPesquisar;
	private JTable table;
	private JTextField txtId;
	private JTextField txtUsuario;
	private JTextField txtLogin;
	private JPasswordField txtSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Usuarios dialog = new Usuarios();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Usuarios() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuarios.class.getResource("/img/pc.png")));
		setTitle("Usuarios");
		setResizable(false);
		setBounds(100, 100, 450, 385);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			txtPesquisar = new JTextField();
			txtPesquisar.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					pesquisarUsuario();
				}
			});
			txtPesquisar.setBounds(10, 24, 139, 20);
			contentPanel.add(txtPesquisar);
			txtPesquisar.setColumns(10);
		}
		{
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon(Usuarios.class.getResource("/img/pesquisar.png")));
			lblNewLabel.setBounds(168, 18, 32, 32);
			contentPanel.add(lblNewLabel);
		}
		{
			JDesktopPane desktopPane = new JDesktopPane();
			desktopPane.setBounds(10, 64, 414, 58);
			contentPanel.add(desktopPane);
			{
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(0, 0, 414, 58);
				desktopPane.add(scrollPane);
				{
					table = new JTable();
					table.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							setarCampos();
							setarSenha();
						}
					});
					scrollPane.setViewportView(table);
				}
			}
		}
		{
			JLabel ID = new JLabel("ID");
			ID.setBounds(10, 135, 32, 14);
			contentPanel.add(ID);
		}
		{
			txtId = new JTextField();
			txtId.setEditable(false);
			txtId.setBounds(52, 132, 86, 20);
			contentPanel.add(txtId);
			txtId.setColumns(10);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Usuario");
			lblNewLabel_2.setBounds(142, 133, 46, 14);
			contentPanel.add(lblNewLabel_2);
		}
		{
			txtUsuario = new JTextField();
			txtUsuario.setBounds(191, 133, 233, 20);
			contentPanel.add(txtUsuario);
			txtUsuario.setColumns(10);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("Login");
			lblNewLabel_3.setBounds(10, 160, 46, 14);
			contentPanel.add(lblNewLabel_3);
		}
		{
			txtLogin = new JTextField();
			txtLogin.setBounds(52, 163, 182, 20);
			contentPanel.add(txtLogin);
			txtLogin.setColumns(10);
		}
		{
			JLabel lblNewLabel_4 = new JLabel("Senha");
			lblNewLabel_4.setBounds(244, 164, 46, 14);
			contentPanel.add(lblNewLabel_4);
		}
		{
			txtSenha = new JPasswordField();
			txtSenha.setText("");
			txtSenha.setBounds(282, 164, 142, 20);
			contentPanel.add(txtSenha);
		}
		{
			JLabel lblNewLabel_5 = new JLabel("Perfil");
			lblNewLabel_5.setBounds(10, 205, 46, 14);
			contentPanel.add(lblNewLabel_5);
		}
		{
			btnAdicionar = new JButton("");
			btnAdicionar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					adicionarUsuario();
				}
			});
			btnAdicionar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/create.png")));
			btnAdicionar.setBounds(62, 255, 80, 80);
			contentPanel.add(btnAdicionar);
		}
		{
			cboPerfil = new JComboBox();
			cboPerfil.setModel(new DefaultComboBoxModel(new String[] {"", "Administrador", "Operador"}));
			cboPerfil.setBounds(52, 201, 172, 22);
			contentPanel.add(cboPerfil);
		}
		{
			btnEditar = new JButton("");
			btnEditar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (chkSenha.isSelected()) {
						editarUsuario();
					} else {
						editarUsuarioSemSenha();
					}

				}
			});
			btnEditar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/update.png")));
			btnEditar.setBounds(170, 255, 80, 80);
			contentPanel.add(btnEditar);
		}
		{
			btnExcluir = new JButton("");
			btnExcluir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					excluirUsuario();
				}
			});
			btnExcluir.setIcon(new ImageIcon(Usuarios.class.getResource("/img/delete.png")));
			btnExcluir.setBounds(282, 255, 80, 80);
			contentPanel.add(btnExcluir);
		
			
		}
		RestrictedTextField usuario = new RestrictedTextField(this.txtUsuario);
		usuario.setLimit(50);
		RestrictedTextField login = new RestrictedTextField(this.txtLogin);
		login.setLimit(50);
		RestrictedTextField senha = new RestrictedTextField(this.txtSenha);
		{
			chkSenha = new JCheckBox("Senha");
			chkSenha.setBounds(265, 201, 97, 23);
			contentPanel.add(chkSenha);
		}
		senha.setLimit(250);
		
	}// Fim do Construtor
	
	DAO dao = new DAO();
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JComboBox cboPerfil;
	private JCheckBox chkSenha;
	
	private void pesquisarUsuario() {
		// ? -> parametro
		String read = "select id as ID, usuario as Usuario, login as Login, senha as Senha, perfil as Perfil from usuarios where usuario like ?";
		try {
			// abrir a conexao com o banco
			Connection con = dao.conectar();
			// preparar a query(instrucao sql) para pesquisar no banco
			PreparedStatement pst = con.prepareStatement(read);
			// substituir o parametro(?) Atencao ao % para completar a query
			// 1 -> parametro (?)
			pst.setString(1, txtUsuario.getText() + "%");
			// Executar a Query e obter os dados do banco (resultado)
			ResultSet rs = pst.executeQuery();
			// popular(preencher) a tabela com os dados do banco
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	private void adicionarUsuario() {

		// validação de campos obrigatorios
		if (txtUsuario.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome do novo Usuario!", "Atenção!!",
					JOptionPane.ERROR_MESSAGE);
			txtUsuario.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o novo Login!", "Atenção!!", JOptionPane.ERROR_MESSAGE);
			txtLogin.requestFocus();
		} else if (txtSenha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe a Nova Senha!", "Atenção!!", JOptionPane.ERROR_MESSAGE);
			txtSenha.requestFocus();
		} else if (cboPerfil.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Selecione o Perfil!", "Atenção!!", JOptionPane.ERROR_MESSAGE);
			cboPerfil.requestFocus();

		} else {
			// inserir o cliente no banco de dados
			String create = "insert into usuarios (usuario,login,senha,perfil) values (?,?,md5(?),?)";
			try {
				// Abrir conexão com o banco
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(create);
				pst.setString(1, txtUsuario.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, txtSenha.getText());
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				// Criando uma variavel que irá executar a query e receber o valor 1 em caso
				// positivo (inserção do cliente do banco)
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Usuario criado com sucesso", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
				}
				con.close();
				limpar();
				// o catch abaixo se refere ao valor duplicado no e-mail(UNIQUE)
			} catch (java.sql.SQLIntegrityConstraintViolationException ex) {
				JOptionPane.showMessageDialog(null, "Login já cadastrado!\n Favor escolher outro login para o Usuário!",
						"Mensagem", JOptionPane.WARNING_MESSAGE);
				txtLogin.setText(null);
				txtLogin.requestFocus();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	private void setarCampos() {
		// a linha abaixo obtem o conteudo da linha da tabela
		// int (indice = colunas) [0] [1] [2] [3] ...
		int setar = table.getSelectedRow();
		// setar os campos
		txtId.setText(table.getModel().getValueAt(setar, 0).toString());
		txtUsuario.setText(table.getModel().getValueAt(setar, 1).toString());
		txtLogin.setText(table.getModel().getValueAt(setar, 2).toString());
		txtSenha.setText(table.getModel().getValueAt(setar, 3).toString());
		cboPerfil.setSelectedItem(table.getModel().getValueAt(setar, 4));

		// Gerenciar os botões
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(true);
		btnExcluir.setEnabled(true);
	}
	
	private void setarSenha() {
		String read2 = "select senha from usuarios where id=?";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(read2);
			pst.setString(1, txtId.getText());
			// A linha abaixo executa a instrução sql e armazena o resultado no
			// objeto(ResultSet)
			ResultSet rs = pst.executeQuery();
			// a linha abaixo verifica se existe uma senha correspondente para o id
			if (rs.next()) {
				txtSenha.setText(rs.getString(1));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	} // FIm do Metodo
	
	private void editarUsuario() {

		// validação de campos obrigatorios
		if (txtUsuario.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome!", "Atenção!!", JOptionPane.ERROR_MESSAGE);
			txtUsuario.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Login!", "Atenção!!", JOptionPane.ERROR_MESSAGE);
			txtLogin.requestFocus();
		} else if (txtSenha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Senha!", "Atenção!!", JOptionPane.ERROR_MESSAGE);
			txtSenha.requestFocus();
		} else if (cboPerfil.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Selecione o Perfil!", "Atenção!!", JOptionPane.ERROR_MESSAGE);
			cboPerfil.requestFocus();
		} else {
			// Editar os dados do cliente no banco
			String update = "update usuarios set usuario=?,login=?,senha=md5(?), perfil=? where id=?";

			try {
				// Abrir conexão com o banco
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(update);
				pst.setString(1, txtUsuario.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, txtSenha.getText());
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				pst.setString(5, txtId.getText());

				// Criando uma variavel que irá executar a query e receber o valor 1 em caso
				// positivo (edição do cliente do banco)
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Dados do Usuario Atualizados com Sucesso!!", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
				}
				con.close();
				limpar();
				// o catch abaixo se refere ao valor duplicado no e-mail(UNIQUE)
			} catch (java.sql.SQLIntegrityConstraintViolationException ex) {
				JOptionPane.showMessageDialog(null, "Login já cadastrado!\n Favor escolher outro Login para cadastrar!",
						"Mensagem", JOptionPane.WARNING_MESSAGE);
				txtLogin.setText(null);
				txtLogin.requestFocus();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	private void editarUsuarioSemSenha() {
		// validação de campos obrigatórios
		if (txtUsuario.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o usuário", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtUsuario.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtLogin.requestFocus();
		} else if (txtSenha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a senha", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			txtSenha.requestFocus();
		} else if (cboPerfil.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha a UF", "Atenção!", JOptionPane.WARNING_MESSAGE);
			// posicionar o cursor no campo após fechar a mensagem
			cboPerfil.requestFocus();
		} else {
			// inserir o cliente no banco
			String update = "update usuarios set usuario = ?, login = ?, perfil = ? where id = ?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(update);
				pst.setString(1, txtUsuario.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, cboPerfil.getSelectedItem().toString());
				pst.setString(4, txtId.getText());
				// criando uma variável que irá executar a query e receber o valor1 em caso
				// positivo (inserção do cliente no banco de dados)
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Dados do usuário editado com sucesso", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
					limpar();
				}
				con.close();
				// o catch abaixo se refere ao valor duplicado de email(UNIQUE)
			} catch (java.sql.SQLIntegrityConstraintViolationException ex) {
				JOptionPane.showMessageDialog(null, "Login já existente\nCadastre outro Login", "Atenção!",
						JOptionPane.WARNING_MESSAGE);
				txtLogin.setText(null);
				txtLogin.requestFocus();
			} catch (Exception e) {
				System.out.println(e);
				JOptionPane.showMessageDialog(null, "Erro ao editar os dados do usuário", "Atenção!",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private void excluirUsuario() {
		// Confirmação de Exclusão
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste Usuario?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			// codigo principal
			String delete = "delete from usuarios where id=?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtId.getText());
				int excluir = pst.executeUpdate();
				if (excluir == 1) {
					limpar();
					JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso!", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
				}

				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException ex) {
				JOptionPane.showMessageDialog(null, "Exclusão Negada. \nFavor Verificar com Administrador do sistema.");

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * Limpar os campos
	 */
	private void limpar() {
		txtUsuario.setText(null);
		txtId.setText(null);
		txtLogin.setText(null);
		txtSenha.setText(null);
		cboPerfil.setSelectedItem(null);
		// Limpar a tabela
		((DefaultTableModel) table.getModel()).setRowCount(0);
		// gerenciar os botões
		btnAdicionar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
	}

}
