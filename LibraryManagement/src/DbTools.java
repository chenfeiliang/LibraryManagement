
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbTools {
	Connection conn;

	public Connection getConn() {

		try {
			Class.forName("com.mysql.jdbc.Driver"); // �������ݿ�����
			String url = "jdbc:mysql://localhost:3306/library?useUnicode=true&characterEncoding=utf8"; //jdbc:mysql://localhost:3306/database?useUnicode=true&amp;characterEncoding=UTF-8 
			String user = "root"; // ���ݿ��û���
			String password = "15071337013"; // ���ݿ�����
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return conn;
	}
	
	
	
}
