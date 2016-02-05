package smartcity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * this is jdbc's sample
 * 	
 * 
 * */
public class SampleofJDBC { 
	public static void main(String[] argv){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/new_project_utra","s13113241","hs9m322x");
			
			Statement stmt = conn.createStatement();
			String SQL = "SELECT * FROM `disease`";
			
			ResultSet rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				System.out.println(rs.getString("disName")+"\t"+rs.getString("info"));	
			}
			stmt.close();
			if (!conn.isClosed()){
				conn.close();
			}
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("SQL Error");
		}
	}
}
