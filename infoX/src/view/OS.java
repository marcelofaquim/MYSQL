package view;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import model.DAO;
import net.proteanit.sql.DbUtils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Cursor;
import javax.swing.border.EtchedBorder;
import java.awt.Color;

public class OS extends JDialog {
	private JTextField txtPesquisar;
	private JTextField txtId;
	private JPanel panel_1;
	private JTextField txtOs;
	private JTextField txtData;
	private JTable table;
	private JTextField txtDefeito;
	private JTextField txtTecnico;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField txtValor;
	// Variavel de apoio ao uso do checkBox
	private String tipo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OS dialog = new OS();
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
	public OS() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(OS.class.getResource("/img/pc.png")));
		setTitle("Ordem de Servi\u00E7o");
		setBounds(100, 100, 733, 442);
		getContentPane().setLayout(null);
		
		txtPesquisar = new JTextField();
		txtPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarCliente();
			}
		});
		txtPesquisar.setBounds(399, 14, 111, 20);
		getContentPane().add(txtPesquisar);
		txtPesquisar.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(OS.class.getResource("/img/pesquisar.png")));
		lblNewLabel.setBounds(520, 14, 32, 32);
		getContentPane().add(lblNewLabel);
		
		JLabel ID = new JLabel("ID");
		ID.setBounds(562, 17, 32, 14);
		getContentPane().add(ID);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setBounds(595, 11, 112, 20);
		getContentPane().add(txtId);
		txtId.setColumns(10);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Ordem de Servi\u00E7o", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 11, 379, 113);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		txtOs = new JTextField();
		txtOs.setBounds(10, 30, 86, 20);
		panel_1.add(txtOs);
		txtOs.setColumns(10);
		
		chkOrcamento = new JCheckBox("Or\u00E7amento");
		chkOrcamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipo = "Orçamento";
			}
		});
		buttonGroup.add(chkOrcamento);
		chkOrcamento.setBounds(10, 80, 86, 23);
		panel_1.add(chkOrcamento);
		
		chkServico = new JCheckBox("Servi\u00E7o");
		chkServico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipo = "Serviço";
			}
		});
		buttonGroup.add(chkServico);
		chkServico.setBounds(120, 80, 68, 23);
		panel_1.add(chkServico);
		
		JLabel lblNewLabel_1 = new JLabel("Status");
		lblNewLabel_1.setBounds(255, 61, 46, 14);
		panel_1.add(lblNewLabel_1);
		
		cboStatus = new JComboBox();
		cboStatus.setModel(new DefaultComboBoxModel(new String[] {"", "Aguardando a Aprova\u00E7\u00E3o", "Na Bancada", "Aguardando retirada", "Retirado"}));
		cboStatus.setBounds(213, 80, 156, 22);
		panel_1.add(cboStatus);
		
		JLabel lblNewLabel_2 = new JLabel("Data");
		lblNewLabel_2.setBounds(157, 33, 46, 14);
		panel_1.add(lblNewLabel_2);
		
		txtData = new JTextField();
		txtData.setEditable(false);
		txtData.setBounds(213, 30, 156, 20);
		panel_1.add(txtData);
		txtData.setColumns(10);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(399, 48, 308, 75);
		getContentPane().add(desktopPane);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 308, 75);
		desktopPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setarId();
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_3 = new JLabel("Imagem");
		lblNewLabel_3.setIcon(new ImageIcon(OS.class.getResource("/img/quebrado (1).png")));
		lblNewLabel_3.setBounds(10, 166, 64, 64);
		getContentPane().add(lblNewLabel_3);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Descri\u00E7\u00E3o do Produto", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel.setBounds(81, 166, 626, 126);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("Equipamento");
		lblNewLabel_4.setBounds(52, 28, 82, 14);
		panel.add(lblNewLabel_4);
		
		txtEquipamento = new JTextField();
		txtEquipamento.setBounds(155, 25, 461, 20);
		panel.add(txtEquipamento);
		txtEquipamento.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Defeito");
		lblNewLabel_5.setBounds(62, 53, 46, 14);
		panel.add(lblNewLabel_5);
		
		txtDefeito = new JTextField();
		txtDefeito.setBounds(155, 50, 461, 20);
		panel.add(txtDefeito);
		txtDefeito.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Tecnico");
		lblNewLabel_6.setBounds(62, 78, 46, 14);
		panel.add(lblNewLabel_6);
		
		txtTecnico = new JTextField();
		txtTecnico.setBounds(155, 75, 161, 20);
		panel.add(txtTecnico);
		txtTecnico.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Valor");
		lblNewLabel_7.setBounds(330, 81, 39, 14);
		panel.add(lblNewLabel_7);
		
		txtValor = new JTextField();
		txtValor.setText("0");
		txtValor.setBounds(379, 75, 237, 20);
		panel.add(txtValor);
		txtValor.setColumns(10);
		
		btnAdicionar = new JButton("");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setBorder(null);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarOs();
			
			}
		});
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setIcon(new ImageIcon(OS.class.getResource("/img/create.png")));
		btnAdicionar.setBounds(91, 303, 64, 64);
		getContentPane().add(btnAdicionar);
		
		btnEditar = new JButton("");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarOS();
			}
		});
		btnEditar.setBorder(null);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setIcon(new ImageIcon(OS.class.getResource("/img/update.png")));
		btnEditar.setToolTipText("Editar");
		btnEditar.setBounds(165, 303, 64, 64);
		getContentPane().add(btnEditar);
		
		btnPesquisar = new JButton("");
		btnPesquisar.setToolTipText("Pesquisar");
		btnPesquisar.setBorder(null);
		btnPesquisar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarOs();
			}
		});
		btnPesquisar.setIcon(new ImageIcon(OS.class.getResource("/img/read.png")));
		btnPesquisar.setBounds(250, 303, 64, 64);
		getContentPane().add(btnPesquisar);
		
		btnExcluir = new JButton("");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirOs();
			}
		});
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setBorder(null);
		btnExcluir.setIcon(new ImageIcon(OS.class.getResource("/img/delete.png")));
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setBounds(414, 303, 64, 64);
		getContentPane().add(btnExcluir);
		
		JButton btnImprimir = new JButton("New button");
		btnImprimir.setBorder(null);
		btnImprimir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnImprimir.setIcon(new ImageIcon(OS.class.getResource("/img/print.png")));
		btnImprimir.setBounds(340, 303, 64, 64);
		getContentPane().add(btnImprimir);

	} // FIm do Metodo
	
	DAO dao = new  DAO();
	private JComboBox cboStatus;
	private JCheckBox chkOrcamento;
	private JCheckBox chkServico;
	private JTextField txtEquipamento;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnAdicionar;
	private JButton btnPesquisar;
	
	private void pesquisarCliente () {

		String read = "select idcli as ID, nome as Cliente, fone as Fone from clientes where nome like ?";
		try {

			Connection con = dao.conectar();

			PreparedStatement pst = con.prepareStatement(read);

			pst.setString(1, txtPesquisar.getText() + "%");

			ResultSet rs = pst.executeQuery();

			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			System.out.println(e);
		}
	} // fim do método
		
		
	
	private void setarId() {
		int setar = table.getSelectedRow();
		txtId.setText(table.getModel().getValueAt(setar, 0).toString());
	}
	
	/*
	 * Método Responsavel pela pesquisa da os
	 */
	

	private void pesquisarOs( ) {
		
			// tecnica usada para capturar
			String numOs = JOptionPane.showInputDialog("Número da OS");
			String read = "select * from tbos where os=" + numOs;
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(read);
				// A linha abaixo, ResultSet, trás a info do banco de dados
				ResultSet rs = pst.executeQuery();

				if (rs.next()) {
					if (tipo == "Serviço") {
						chkServico.setSelected(true);
						tipo = "Serviço";
					} else {
						chkOrcamento.setSelected(true);
						tipo = "Orçamento";
					}
					txtId.setText(rs.getString(9));
					txtOs.setText(rs.getString(1));
					txtData.setText(rs.getString(2));
					txtEquipamento.setText(rs.getString(5));
					txtTecnico.setText(rs.getString(7));
					txtValor.setText(rs.getString(8));
					txtDefeito.setText(rs.getString(6));
					cboStatus.setSelectedItem(rs.getString(4).toString());
					txtPesquisar.setEnabled(false);

				} else {
					JOptionPane.showMessageDialog(null, "O.S Não Localizada!!", "Atenção!!", JOptionPane.ERROR_MESSAGE);
					limpar();
				}

			} catch (Exception e) {
				System.out.println(e);

			}

		} /// FIm do Metodo
	
	private void editarOS() {

		if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o Equipamento!", "Atenção!!", JOptionPane.ERROR_MESSAGE);
			txtEquipamento.requestFocus();
		} else if (txtTecnico.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o Técnico.", "Atenção!!", JOptionPane.ERROR_MESSAGE);
			txtTecnico.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o Valor do Serviço.", "Atenção!!", JOptionPane.ERROR_MESSAGE);
			txtValor.requestFocus();
		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Descreva o Defeito.", "Atenção!!", JOptionPane.ERROR_MESSAGE);
			txtDefeito.requestFocus();
		}	else if (txtId.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o campo ID", "Atenção !", JOptionPane.WARNING_MESSAGE);
				txtId.requestFocus();
		
		}

		String update = "update tbos set tipo=?,statusos=?,equipamento=?,defeito=?,tecnico=?,valor=? where idcli=?";

		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(update);
			pst.setString(1, tipo);
			pst.setString(2, cboStatus.getSelectedItem().toString());
			pst.setString(3, txtEquipamento.getText());
			pst.setString(4, txtDefeito.getText());
			pst.setString(5, txtTecnico.getText());
			pst.setString(6, txtValor.getText());
			pst.setString(7, txtId.getText());

			int confirma = pst.executeUpdate();
			if (confirma == 1) {
				JOptionPane.showMessageDialog(null, "O.S Atualizada com Sucesso!!", "Mensagem",
						JOptionPane.INFORMATION_MESSAGE);
			}
			con.close();
			limpar();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	private void adicionarOs() {
		 if (tipo == null) {
			JOptionPane.showMessageDialog(null, "Selecione o tipo de OS", "Atenção !", JOptionPane.WARNING_MESSAGE);
			chkOrcamento.requestFocus();
		} else if (cboStatus.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Selecione qual o status da OS", "Atenção !",
					JOptionPane.WARNING_MESSAGE);
			cboStatus.requestFocus();
		} else if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo Equipamento", "Atenção !",
					JOptionPane.WARNING_MESSAGE);
			txtEquipamento.requestFocus();
		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo Defeito", "Atenção !", JOptionPane.WARNING_MESSAGE);
			chkOrcamento.requestFocus();
		}	else if (txtId.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o campo ID", "Atenção !", JOptionPane.WARNING_MESSAGE);
				txtId.requestFocus();
		} else {
			String create = "insert into tbos (tipo,statusos,equipamento,defeito,tecnico,valor,idcli) values (?,?,?,?,?,?,?)";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(create);
				pst.setString(1, tipo);
				pst.setString(2, cboStatus.getSelectedItem().toString());
				pst.setString(3, txtEquipamento.getText());
				pst.setString(4, txtDefeito.getText());
				pst.setString(5, txtTecnico.getText());
				pst.setString(6, txtValor.getText());
				pst.setString(7, txtId.getText());

				// criando uma variavel que irá executar a query e receber o valor 1 em caso
				// positivo (inserção do cliente no banco)
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "OS emitida com sucesso", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
					con.close();
					limpar();
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}// fim do método emitirOs()
	
	
	private void excluirOs() {
		// Confirmação de Exclusão
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão desta O.S?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			// codigo principal
			String delete = "delete from tbos where os=?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtOs.getText());
				int excluir = pst.executeUpdate();
				if (excluir == 1) {
					limpar();
					JOptionPane.showMessageDialog(null, "O.S excluída com sucesso!", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
				}

				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException ex) {
				JOptionPane.showMessageDialog(null, "Exclusão Negada. \nFavor Verificar com Administrador do sistema.");

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	} //// Fim do Metodo

	

	private void limpar() {
		txtEquipamento.setText(null);
		txtTecnico.setText(null);
		txtValor.setText(null);
		txtData.setText(null);
		txtOs.setText(null);
		txtId.setText(null);
		txtDefeito.setText(null);
		cboStatus.setSelectedItem(null);
		buttonGroup.clearSelection();
		// Limpar a tabela
		//((DefaultTableModel) table.getModel()).setRowCount(0);
		
		btnAdicionar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		tipo = null;

	}
}
	
	

