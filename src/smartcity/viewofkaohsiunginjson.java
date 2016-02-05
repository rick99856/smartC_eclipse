package smartcity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;
/*
 * json in url 
 * this is for kaohsiung for view
 * 
 * 
 * */
public class viewofkaohsiunginjson {
	public static void main(String argv[])throws Exception{
		URL url = new URL("http://data.kaohsiung.gov.tw/Opendata/DownLoad.aspx?Type=2&CaseNo1=AV&CaseNo2=1&FileType=1&Lang=C&FolderType=");
		HttpURLConnection huc = (HttpURLConnection)url.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream(), "UTF-8"));
		
		String str = ""; 
		StringBuffer sb = new StringBuffer();
		
		while(null != ((str=br.readLine()))) {
			sb.append(str);
		}
		
		br.close();
		
		String jjson = sb.toString(); 
		try {
			JSONArray jsonOba = new JSONArray(jjson);
			
			for(int i =0;i<jsonOba.length();i++){
                JSONObject c = jsonOba.getJSONObject(i);

                
                String Name = c.getString("Name");
                String Toldescribe = c.getString("Toldescribe");
                String Tel = c.getString("Tel");
                String Addr = c.getString("Add");
                String Py = c.getString("Px");
                String Px = c.getString("Py");
                String Opentime = c.getString("Opentime");
                String Ticketinfo = c.getString("Ticketinfo");
//                System.out.print(Name+"\t"+Toldescribe);
                
                Class.forName("com.mysql.jdbc.Driver");
    			
//    			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/smartc","s13113241","hs9m322x");
    			Connection conn = DriverManager.getConnection( "jdbc:mysql://"+"192.168.137.178"+"/"+"smartc"+"?useUnicode=true&characterEncoding=UTF-8","s13113241","hs9m322x");
    			Statement stmt = conn.createStatement();
    			String SQL = "INSERT INTO `smartc`.`view` (`ID`, `Name`, `Told`, `Tel`, `Addr`, `Open`, `Px`, `Py`, `Ticket`, `PS`, `site`)"
    						+ " VALUES (NULL, '"+Name+"','"+Toldescribe+"' , '"+Tel+"', '"+Addr+"', '"+Opentime+"', '"+Px+"', '"+Py+"', '"+Ticketinfo+"', "+"''"+", '高雄市')";
    			
    			int result = stmt.executeUpdate(SQL);
//    			用在查詢
//    			ResultSet rs = stmt.executeQuery(SQL);
//    			System.out.print(rs);
//    			while (rs.next()) {
//    				System.out.println(rs.getString("disName")+"\t"+rs.getString("info"));	
//    			}
//    			stmt.close();
//    			if (!conn.isClosed()){
//    				conn.close();
//    			}

            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		//	System.out.print(xmlResponse);
		br.close();
		huc.disconnect();
	}
}


