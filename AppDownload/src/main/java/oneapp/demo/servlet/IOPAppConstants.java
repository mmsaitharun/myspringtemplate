package oneapp.demo.servlet;

/**
 * @author INC00718
 *
 */
public interface IOPAppConstants {

//	String JDBC_MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	String JDBC_MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
	String JDBC_MYSQL_HOST = "localhost";
	String JDBC_MYSQL_PORT = "3306";
	String JDBC_MYSQL_URL_IDENTIFIER = "mysql";
	String JDBC_MYSQL_SCHEMA = "spring";
	String JDBC_MYSQL_URL = "jdbc:"+JDBC_MYSQL_URL_IDENTIFIER+"://"+JDBC_MYSQL_HOST+":"+JDBC_MYSQL_PORT+"/"+JDBC_MYSQL_SCHEMA+"";
	String JDBC_MYSQL_USER = "root";
	String JDBC_MYSQL_PASS = "root";
	
	String JDBC_HANA_DRIVER = "com.sap.db.jdbc.Driver";
//	String JDBC_HANA_HOST = "vadbhh004fu.phx.od.sap.biz";
	String JDBC_HANA_HOST = "localhost";
	String JDBC_HANA_PORT = "30015";
	String JDBC_HANA_URL_IDENTIFIER = "sap";
	String JDBC_HANA_SCHEMA = "WORKBOX";
	String JDBC_HANA_URL = "jdbc:"+JDBC_HANA_URL_IDENTIFIER+"://"+JDBC_HANA_HOST+":"+JDBC_HANA_PORT+"/"+JDBC_HANA_SCHEMA+"";
	String JDBC_HANA_USER = "WORKBOX";
	String JDBC_HANA_PASS = "Incture1234567891013";
	
	String JDBC_ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
	String JDBC_ORACLE_HOST = "34.213.118.108";
	String JDBC_ORACLE_PORT = "1521";
	String JDBC_ORACLE_URL_IDENTIFIER = "oracle:thin";
	String JDBC_ORACLE_SCHEMA = "xe";
	String JDBC_ORACLE_URL = "jdbc:"+JDBC_ORACLE_URL_IDENTIFIER+":/@"+JDBC_ORACLE_HOST+":"+JDBC_ORACLE_PORT+"/"+JDBC_ORACLE_SCHEMA+"";
	String JDBC_ORACLE_USER = "SPOTLIGHT";
	String JDBC_ORACLE_PASS = "SPOTLIGHT";
	
	/* Application Constants */
	
	String FILE_TYPE_APK = "APK";
	String FILE_TYPE_IPA = "IPA";
	String FILE_TYPE_PLIST = "PLIST";
	String FILE_TYPE_PNG = "PNG";
	
	String FILE_UPLOAD_TYPE_UPDATE = "UPDATE";
	String FILE_UPLOAD_TYPE_CREATE = "CREATE";
	
	String JDBC_URL_PARAMS = "&maxAllowedPacket=268435456&useSSL=false";
	
	String TABLE_PROD = "APP_FILE";
	String TABLE_QA = "APP_FILE_QA";
	
}
