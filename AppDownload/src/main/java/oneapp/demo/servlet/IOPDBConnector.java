package oneapp.demo.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author INC00718
 *
 */
public class IOPDBConnector {

	private static IOPDBConnector dbConnector;

	private IOPDBConnector() {

	}

	public static synchronized IOPDBConnector getInstance() {
		if (dbConnector == null) {
			dbConnector = new IOPDBConnector();
		}
		return dbConnector;
	}

	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Connection con = null;
		try {
			Class.forName(IOPAppConstants.JDBC_MYSQL_DRIVER);
			con = DriverManager.getConnection(IOPAppConstants.JDBC_MYSQL_URL+"?user="+IOPAppConstants.JDBC_MYSQL_USER+"&password="+IOPAppConstants.JDBC_MYSQL_USER+IOPAppConstants.JDBC_URL_PARAMS);
			
//			Class.forName(IOPAppConstants.JDBC_HANA_DRIVER);
//			con = DriverManager.getConnection(IOPAppConstants.JDBC_HANA_URL, IOPAppConstants.JDBC_HANA_USER, IOPAppConstants.JDBC_HANA_PASS);
			
//			Class.forName(AppConstants.JDBC_ORACLE_DRIVER);
//			con = DriverManager.getConnection(AppConstants.JDBC_ORACLE_URL, AppConstants.JDBC_ORACLE_USER, AppConstants.JDBC_ORACLE_USER);
			
		} catch (Exception e) {
			System.err.println("Exception : "+e.getMessage());
		}
		return con;
	}

	public PreparedStatement insert(String insertQuery) throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		try {
			con = IOPDBConnector.getInstance().getConnection();
			preparedStatement = con.prepareStatement(insertQuery);
		} catch (Exception e) {
			System.err.println("Exception : "+e.getMessage());
		} finally {
//			con.close();
		}
		return preparedStatement;
	}

	public ResultSet query(String query) throws ClassNotFoundException, SQLException {
		Connection con = null;
		ResultSet resultSet = null;
		try {
			con = IOPDBConnector.getInstance().getConnection();
			resultSet = con.createStatement().executeQuery(query);
//			con.close();
			return resultSet;
		} catch (Exception e) {
			System.err.println("Exception : "+e.getMessage());
//			con.close();
			return resultSet;
		}
	}

}
