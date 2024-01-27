package br.com.jdevtreinamentos.tf.infrastructure.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Esta classe,<strong> FabricaConexao<strong>, é responsável por encalpusar o
 * código de conexão com o banco de dados, Implementando o padrão de projeto
 * Singleton com o objetivo de manter uma única instancia desta classe
 * disponível para uso e fonecer o objeto de conexão com o banco.
 * 
 * @author Erik Vasconcelos
 * @since 2023-12-18
 * @version 0.2 2023-12-19
 */

public class FabricaConexao {

	private static class InstanceHolder {
		public static FabricaConexao instance = new FabricaConexao();
	}
 
	private static final String URL = System.getenv("DATABASE_URL");
	private static final String USER = System.getenv("DATABASE_USERNAME");
	private static final String PASSWORD = System.getenv("DATABASE_PASSWORD");
	private static Connection connection;

	private FabricaConexao() {
		setConnection();
	}

	public static FabricaConexao getInstance() {
		return InstanceHolder.instance;
	}

	public static Connection getConnection() {
		if (connection == null || connectionIsClosed()) {
			setConnection();
		}

		return connection;
	}

	private static void setConnection() {
		if (connection == null || connectionIsClosed()) {
			try {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
				connection.setAutoCommit(false);

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static boolean connectionIsClosed() {
		if (connection != null) {
			try {
				return connection.isClosed();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	public static void connectionCommit() {
		if (!connectionIsClosed()) {
			try {
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void connectionRollback() {
		if (!connectionIsClosed()) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
