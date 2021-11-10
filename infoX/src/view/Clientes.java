package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import net.proteanit.sql.DbUtils;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Clientes extends JDialog {
	private JTextField txtPesquisar;
	private JTextField txtIdCli;
	private JTextField txtNomeCli;
	private JTextField txtFoneCli;
	private JTextField txtCep;
	private JTextField txtEndereco;
	private JTextField txtNumero;
	private JTextField txtComplemento;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JComboBox cboUf;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Clientes dialog = new Clientes();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Clientes() {
		setTitle("Clientes");
		setResizable(false);
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Clientes.class.getResource("/img/pc.png")));
		setBounds(150, 150, 738, 482);
		getContentPane().setLayout(null);

		txtPesquisar = new JTextField();
		txtPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarCliente();
			}
		});
		txtPesquisar.setBounds(25, 36, 220, 20);
		getContentPane().add(txtPesquisar);
		txtPesquisar.setColumns(10);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Clientes.class.getResource("/img/pesquisar.png")));
		lblNewLabel.setBounds(267, 24, 32, 32);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("*Campos Obrigatorios*");
		lblNewLabel_1.setBounds(485, 39, 180, 14);
		getContentPane().add(lblNewLabel_1);

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(25, 172, 619, -79);
		getContentPane().add(desktopPane);

		JDesktopPane desktopPane_1 = new JDesktopPane();
		desktopPane_1.setBackground(Color.GRAY);
		desktopPane_1.setBounds(25, 193, 619, -88);
		getContentPane().add(desktopPane_1);

		JDesktopPane desktopPane_2 = new JDesktopPane();
		desktopPane_2.setBounds(21, 209, 623, -109);
		getContentPane().add(desktopPane_2);

		JDesktopPane desktopPane_3 = new JDesktopPane();
		desktopPane_3.setBounds(25, 91, 629, 107);
		getContentPane().add(desktopPane_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 629, 107);
		desktopPane_3.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarCampos();
			}
		});
		scrollPane.setViewportView(table);

		JLabel lblNewLabel_2 = new JLabel("ID");
		lblNewLabel_2.setBounds(25, 233, 32, 14);
		getContentPane().add(lblNewLabel_2);

		txtIdCli = new JTextField();
		txtIdCli.setBounds(60, 230, 86, 20);
		getContentPane().add(txtIdCli);
		txtIdCli.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel((String) null);
		lblNewLabel_3.setBounds(25, 258, 46, 14);
		getContentPane().add(lblNewLabel_3);

		txtNomeCli = new JTextField();
		txtNomeCli.setBounds(217, 230, 251, 20);
		getContentPane().add(txtNomeCli);
		txtNomeCli.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("* Nome");
		lblNewLabel_4.setBounds(156, 233, 51, 14);
		getContentPane().add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("* Fone");
		lblNewLabel_5.setBounds(485, 233, 37, 14);
		getContentPane().add(lblNewLabel_5);

		txtFoneCli = new JTextField();
		txtFoneCli.setBounds(532, 230, 122, 20);
		getContentPane().add(txtFoneCli);
		txtFoneCli.setColumns(10);

		JLabel lblNewLabel_6 = new JLabel("CEP");
		lblNewLabel_6.setBounds(25, 273, 32, 14);
		getContentPane().add(lblNewLabel_6);

		txtCep = new JTextField();
		txtCep.setBounds(60, 270, 102, 20);
		getContentPane().add(txtCep);
		txtCep.setColumns(10);

		JButton btnCep = new JButton("Buscar");
		btnCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		btnCep.setBounds(183, 269, 89, 23);
		getContentPane().add(btnCep);

		JLabel lblNewLabel_7 = new JLabel("* Endere\u00E7o");
		lblNewLabel_7.setBounds(282, 278, 66, 14);
		getContentPane().add(lblNewLabel_7);

		txtEndereco = new JTextField();
		txtEndereco.setBounds(358, 272, 307, 20);
		getContentPane().add(txtEndereco);
		txtEndereco.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel("* N\u00FAmero");
		lblNewLabel_8.setBounds(25, 315, 54, 14);
		getContentPane().add(lblNewLabel_8);

		txtNumero = new JTextField();
		txtNumero.setBounds(89, 312, 73, 20);
		getContentPane().add(txtNumero);
		txtNumero.setColumns(10);

		JLabel lblNewLabel_9 = new JLabel("Complemento");
		lblNewLabel_9.setBounds(180, 315, 79, 14);
		getContentPane().add(lblNewLabel_9);

		txtComplemento = new JTextField();
		txtComplemento.setBounds(267, 312, 73, 20);
		getContentPane().add(txtComplemento);
		txtComplemento.setColumns(10);

		JLabel lblNewLabel_10 = new JLabel("* Bairro");
		lblNewLabel_10.setBounds(357, 315, 48, 14);
		getContentPane().add(lblNewLabel_10);

		txtBairro = new JTextField();
		txtBairro.setBounds(413, 312, 262, 20);
		getContentPane().add(txtBairro);
		txtBairro.setColumns(10);

		JLabel lblNewLabel_11 = new JLabel("* Cidade");
		lblNewLabel_11.setBounds(25, 366, 54, 14);
		getContentPane().add(lblNewLabel_11);

		txtCidade = new JTextField();
		txtCidade.setBounds(92, 363, 167, 20);
		getContentPane().add(txtCidade);
		txtCidade.setColumns(10);

		JLabel lblNewLabel_12 = new JLabel("* UF");
		lblNewLabel_12.setBounds(267, 366, 32, 14);
		getContentPane().add(lblNewLabel_12);

		cboUf = new JComboBox();
		cboUf.setModel(new DefaultComboBoxModel(
				new String[] { "", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
						"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		cboUf.setBounds(296, 362, 55, 22);
		getContentPane().add(cboUf);

		btnAdicionar = new JButton("");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarCliente();
			}
		});
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setBorder(null);
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setIcon(new ImageIcon(Clientes.class.getResource("/img/create.png")));
		btnAdicionar.setBounds(403, 362, 64, 64);
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarCliente();
			}
		});
		btnEditar.setBorder(null);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setToolTipText("Editar");
		btnEditar.setIcon(new ImageIcon(Clientes.class.getResource("/img/update.png")));
		btnEditar.setBounds(499, 362, 64, 64);
		getContentPane().add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirCliente();
			}
		});
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setIcon(new ImageIcon(Clientes.class.getResource("/img/delete.png")));
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setBorder(null);
		btnExcluir.setBounds(588, 362, 64, 64);
		getContentPane().add(btnExcluir);

	}// fim do construtor

	/**
	 * buscarCep
	 */
	private void buscarCep() {
		String logradouro = "";
		String tipoLogradouro = "";
		String resultado = null;
		String cep = txtCep.getText();
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();
				if (element.getQualifiedName().equals("cidade")) {
					txtCidade.setText(element.getText());
				}
				if (element.getQualifiedName().equals("bairro")) {
					txtBairro.setText(element.getText());
				}
				if (element.getQualifiedName().equals("uf")) {
					cboUf.setSelectedItem(element.getText());
				}
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					tipoLogradouro = element.getText();
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}
				if (element.getQualifiedName().equals("resultado")) {
					resultado = element.getText();
					if (resultado.equals("1")) {
						//lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/check.png")));
					} else {
						JOptionPane.showMessageDialog(null, "CEP não encontrado");
					}
				}
			}
			txtEndereco.setText(tipoLogradouro + " " + logradouro);
		} catch (Exception e) {
			System.out.println(e);
		}
	} /// fim do metodo
	
	DAO dao = new DAO();
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	
	private void pesquisarCliente() {
		// ? -> parametro
		String read = "select idcli as ID, nome as Cliente, cep as Cep, endereço as Endereço, numero as Nº, complemento as Complemento,\r\n"
				+ " bairro as Bairro, uf as UF, fone as Fone, cidade as Cidade from clientes where nome like ?";
		try {
			// abrir a conexao com o banco
			Connection con = dao.conectar();
			// preparar a query(instrucao sql) para pesquisar no banco
			PreparedStatement pst = con.prepareStatement(read);
			// substituir o parametro(?) Atencao ao % para completar a query
			// 1 -> parametro (?)
			pst.setString(1, txtPesquisar.getText() + "%");
			// Executar a Query e obter os dados do banco (resultado)
			ResultSet rs = pst.executeQuery();
			// popular(preencher) a tabela com os dados do banco
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			System.out.println(e);
		}
	} /// Fim do Metodo
	
	private void adicionarCliente()

	{
		if (txtNomeCli.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome!", "Atenção!!", JOptionPane.ERROR_MESSAGE);
			txtNomeCli.requestFocus();
		} else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Endereço Completo!", "Atenção!!",
					JOptionPane.ERROR_MESSAGE);
			txtCep.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Endereço Completo!", "Atenção!!",
					JOptionPane.ERROR_MESSAGE);
			txtEndereco.requestFocus();
		//} else if (txtComplemento.getText().isEmpty()) {
			//JOptionPane.showMessageDialog(null, "Preencha a Endereço Completo!", "Atenção!!",
					//JOptionPane.ERROR_MESSAGE);
			//txtComplemento.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Endereço Completo!", "Atenção!!",
					JOptionPane.ERROR_MESSAGE);
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Endereço Completo!", "Atenção!!",
					JOptionPane.ERROR_MESSAGE);
			txtCidade.requestFocus();
		} else if (txtFoneCli.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Telefone!", "Atenção!!", JOptionPane.ERROR_MESSAGE);
			txtFoneCli.requestFocus();

		} else {
			String create = "insert into clientes(nome,cep,endereço,numero,complemento,bairro,uf,fone,cidade) values (?,?,?,?,?,?,?,?,?)";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(create);
				pst.setString(1, txtNomeCli.getText());
				pst.setString(2, txtCep.getText());
				pst.setString(3, txtEndereco.getText());
				pst.setString(4, txtNumero.getText());
				pst.setString(5, txtComplemento.getText());
				pst.setString(6, txtBairro.getText());
				pst.setString(7, cboUf.getSelectedItem().toString());
				pst.setString(8, txtFoneCli.getText());
				pst.setString(9, txtCidade.getText());

				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
				}
				con.close();
				limpar();
			}  catch (Exception e) {
				System.out.println(e);
			}
		}
	} // FIm do Metodo
	
	private void setarCampos() {
		int setar = table.getSelectedRow();
		txtIdCli.setText(table.getModel().getValueAt(setar, 0).toString());
		txtNomeCli.setText(table.getModel().getValueAt(setar, 1).toString());
		txtCep.setText(table.getModel().getValueAt(setar, 2).toString());
		txtEndereco.setText(table.getModel().getValueAt(setar, 3).toString());
		txtNumero.setText(table.getModel().getValueAt(setar, 4).toString());
		txtComplemento.setText(table.getModel().getValueAt(setar, 5).toString());
		txtBairro.setText(table.getModel().getValueAt(setar, 6).toString());
		cboUf.setSelectedItem(table.getModel().getValueAt(setar, 7).toString());
		txtFoneCli.setText(table.getModel().getValueAt(setar, 8).toString());
		txtCidade.setText(table.getModel().getValueAt(setar, 9).toString());

		// Geren Btn
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(true);
		btnExcluir.setEnabled(true);

	} // Fim do Metodo
	

	private void editarCliente() {

		if (txtNomeCli.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome!", "Atenção!!", JOptionPane.ERROR_MESSAGE);
			txtNomeCli.requestFocus();
		} else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Endereço Completo!", "Atenção!!",
					JOptionPane.ERROR_MESSAGE);
			txtCep.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Endereço Completo!", "Atenção!!",
					JOptionPane.ERROR_MESSAGE);
			txtEndereco.requestFocus();
		}/* else if (txtComplemento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Endereço Completo!", "Atenção!!",
					JOptionPane.ERROR_MESSAGE);
			txtComplemento.requestFocus();
		}*/ else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Endereço Completo!", "Atenção!!",
					JOptionPane.ERROR_MESSAGE);
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Endereço Completo!", "Atenção!!",
					JOptionPane.ERROR_MESSAGE);
			txtCidade.requestFocus();
		} else if (txtFoneCli.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Telefone!", "Atenção!!", JOptionPane.ERROR_MESSAGE);
			txtFoneCli.requestFocus();

		} else {
			String update = "update clientes set nome=?,cep=?,endereço=?,numero=?,complemento=?,bairro=?,uf=?,fone=?,cidade=? where idcli=?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(update);
				pst.setString(1, txtNomeCli.getText());
				pst.setString(2, txtCep.getText());
				pst.setString(3, txtEndereco.getText());
				pst.setString(4, txtNumero.getText());
				pst.setString(5, txtComplemento.getText());
				pst.setString(6, txtBairro.getText());
				pst.setString(7, cboUf.getSelectedItem().toString());
				pst.setString(8, txtFoneCli.getText());
				pst.setString(9, txtCidade.getText());
				pst.setString(10, txtIdCli.getText());

				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Dados do Cliente Atualizado com Sucesso!!", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
				}
				con.close();
				limpar();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	} // FIm do Metodo
	
	private void excluirCliente() {
		// Confirmação de Exclusão
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste cliente?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			// codigo principal
			String delete = "delete from clientes where idcli=?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtIdCli.getText());
				int excluir = pst.executeUpdate();
				if (excluir == 1) {
					limpar();
					JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso!", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
				}

				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException ex) {
				JOptionPane.showMessageDialog(null, "Exclusão Negada. \nCliente Serviço pedido em aberto.");

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	} // Fim Do Metodo
	
	private void limpar()

	{
		txtPesquisar.setText(null);
		txtIdCli.setText(null);
		txtNomeCli.setText(null);
		txtCep.setText(null);
		txtEndereco.setText(null);
		txtNumero.setText(null);
		txtComplemento.setText(null);
		txtBairro.setText(null);
		cboUf.setSelectedItem(null);
		txtFoneCli.setText(null);
		txtCidade.setText(null);

		// Limpar a tabela
		//((DefaulTableModel) table.getModel().setRowCount(0));
		// geren btn
		btnAdicionar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
	}

	
	
}


