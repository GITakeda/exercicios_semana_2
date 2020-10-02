package br.com.crud.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionFactory {

	private static final String USERNAME = "admin";
	
	private static final String PASSWORD = "admin";
	
	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/escola?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
	public static Connection createConnectionToMySQL() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		
		Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
		
		return connection;
	}
	
	public static boolean close(PreparedStatement pstm, Connection con) {
		try {
			if(pstm != null) {
				pstm.close();
			}
			
			if(con != null) {
				con.close();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
			return false;
		}
		
		return true;
	}
	
}
