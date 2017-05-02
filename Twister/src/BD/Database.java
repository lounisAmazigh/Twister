package BD;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.naming.NamingException;
public class Database {
	
	private DataSource dataSource;
	private static Database database ;
	
	public Database(String jndiname) throws SQLException {
		try{
			dataSource = (DataSource) new InitialContext().lookup("java:comp/env/" + jndiname);
		}catch (NamingException e){
			throw new SQLException(jndiname + "is missing in JNDI ! :"+e.getMessage()); 
			}
		}
	
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	
	
	public static Connection getMysqlConnection() throws InstantiationException, IllegalAccessException, SQLException {
		System.out.println("debut getMySqlConnection");
			if( DBStatic.mysql_pooling == false){
				try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					}              
					catch(java.lang.ClassNotFoundException e) {
					System.err.print("Exception: ");
					System.err.println(e.getMessage());
					}
				String url = "jdbc:mysql://"+ DBStatic.mysql_host + "/" + DBStatic.mysql_db ;
				Connection co = DriverManager.getConnection(url,DBStatic.mysql_username ,DBStatic.mysql_password);
				
				return(co);
			}
			else {
				System.out.println("pooling = true");
				if(database==null){
					System.out.println("database null");
					database=new Database("jdbc/db");
				}
				return(database.getConnection());
			}
	}
}
