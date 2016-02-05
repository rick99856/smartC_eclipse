package smartcity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

import com.csvreader.CsvReader;
 
/*
 * 存csv格式的檔案
 * this is for tainan for view
 * 
 * 
 * */ 
public class hospitalofnanincsv {
	public static void main(String[] args) {
		try {
			
//			CsvReader products = new CsvReader("C:/Users/STU/Downloads/view_tainan.csv");
			CsvReader products = new CsvReader("hospital_nan.csv");
			products.readHeaders();

			while (products.readRecord())
			{
//				 for(int i=0;i<products.getColumnCount();i++){
					 
//			         String big5str = new String(products.get(i).getBytes("ISO-8859-1"),"UTF-8");
//			         System.out.print(big5str+"@@");
//				 }
//				int a[] = {2,3,4,6,7,10,11,15,16};
//				System.out.print(products.get);
				 String name = new String(products.get(0).getBytes("ISO-8859-1"),"UTF-8");
				 String tel = new String(products.get(1).getBytes("ISO-8859-1"),"UTF-8");
				 String addr = new String(products.get(2).getBytes("ISO-8859-1"),"UTF-8");
				 String lat = new String(products.get(3).getBytes("ISO-8859-1"),"UTF-8");
				 String lng = new String(products.get(4).getBytes("ISO-8859-1"),"UTF-8");
//				 String Addr = new String(products.get(a[5]).getBytes("ISO-8859-1"),"UTF-8");
//				 String Tel = new String(products.get(a[6]).getBytes("ISO-8859-1"),"UTF-8");
//				 String Ticket = new String(products.get(a[7]).getBytes("ISO-8859-1"),"UTF-8");
//				 String PS = new String(products.get(a[8]).getBytes("ISO-8859-1"),"UTF-8");
				
				 
				 try {
					Class.forName("com.mysql.jdbc.Driver");
				
	    			
//	    			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/smartc","s13113241","hs9m322x");
	    			Connection conn = DriverManager.getConnection( "jdbc:mysql://"+"192.168.137.178"+"/"+"smartc"+"?useUnicode=true&characterEncoding=UTF-8","s13113241","hs9m322x");
	    			Statement stmt = conn.createStatement();
	    			String SQL = "INSERT INTO `smartc`.`"
	    					+ "hospital` (`no`, `name`, `addr`, `tel`, `lng`, `lat`,`site`)"
	    						+ " VALUES (NUll, '"+name+"','"+addr+"' , '"+tel+"', '"+lng+"', '"+lat+"', '台南市')";
	    			int result = stmt.executeUpdate(SQL);
				 } catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
				
				// perform program logic here
//				System.out.println(name + ":" + told);
//				System.out.println(productID + ":" + big5str);
			}
	
			products.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
