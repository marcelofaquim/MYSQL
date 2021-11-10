package model;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Conexão com o Banco de dados
 * 
 * @author Marcelo Faquim Dos Anjos
 * @version 1.0
 */
public class DAO {
// paramentros de conexão
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://10.26.45.113:3306/dbinfox";
	private String user = "marcelo";
	private String password = "123@Senac";

	/**
	 * Método responsavel pela conexão com o banco
	 * 
	 * @return con
	 */
	public Connection conectar() {
		// a linha abaixo cria um objeto de nome con
		Connection con = null;
		// Tratamento de exceções
		try {
			// as duas linhas abaixo estabelecem a conexão
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

}
