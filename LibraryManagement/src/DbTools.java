
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbTools {
	Connection conn;

	public Connection getConn() {

		try {
			Class.forName("com.mysql.jdbc.Driver"); // 加载数据库驱动
			String url = "jdbc:mysql://localhost:3306/library?useUnicode=true&characterEncoding=utf8"; //jdbc:mysql://localhost:3306/database?useUnicode=true&amp;characterEncoding=UTF-8 
			String user = "root"; // 数据库用户名
			String password = "15071337013"; // 数据库密码
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
