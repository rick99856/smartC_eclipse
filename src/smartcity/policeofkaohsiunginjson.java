package smartcity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;
/*
 * json in url 
 * this is for kaohsiung for parking
 * 
 * 
 * */
public class policeofkaohsiunginjson {
	public static void main(String argv[])throws Exception{
		URL url = new URL("http://data.kaohsiung.gov.tw/Opendata/DownLoad.aspx?Type=2&CaseNo1=AP&CaseNo2=13&FileType=2&Lang=C&FolderType=");
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

                String area = c.getString("��F��");
                String type = c.getString("����");
                String name = c.getString("�������W��");
                String addr = c.getString("��������m");
                String payinfo = c.getString("���O�з�(���O�ɶ�)");
                String worktime = c.getString("��~�ɶ�");
//                String ps = c.getString("�� ��");
//                String px = c.get("�n��Lat").toString();
//                String py = c.get("�g��Lng").toString();
                
                Double lat=0.0,lng =0.0; 
                URL map  = new URL(String.format("http://maps.googleapis.com/maps/api/geocode/json?address=%s&sensor=false&language=zh-TW", 
                URLEncoder.encode("������"+name, "UTF-8")));//p=%s is KeyWord in
                 URLConnection connection = map.openConnection();
                String line;
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
                while ((line = reader.readLine()) != null) {builder.append(line);}
                JSONObject json = new JSONObject(builder.toString()); //�ഫjson�榡
                JSONArray ja = json.getJSONArray("results");//���ojson��Array����
                try{
                      System.out.print("�a�}�G" + ja.getJSONObject(0).getString("formatted_address") + " ");
                      System.out.print("�n�סG" + ja.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat") + " ");
                      System.out.print("�g�סG" + ja.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
                      System.out.println("");
                      lat = ja.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                      lng = ja.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                }catch(Exception e){
                	lat =0.0;
                	lng =0.0;
                }
                


                Class.forName("com.mysql.jdbc.Driver");
    			
//    			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/smartc","s13113241","hs9m322x");
    			Connection conn = DriverManager.getConnection( "jdbc:mysql://"+"192.168.137.178"+"/"+"smartc"+"?useUnicode=true&characterEncoding=UTF-8","s13113241","hs9m322x");
    			Statement stmt = conn.createStatement();
    			String SQL = "INSERT INTO `smartc`.`"
    					+ "parking` (`no`,`area`, `type`, `name`, `addr`, `payinfo`, `worktime`, `ps`,`px`,`py`)"
    						+ " VALUES (NULL,'"+area+"', '"+type+"','"+name+"' , '"+addr+"', '"+ payinfo+"', '"+worktime+"', '' , '"+lat+"','"+lng+"')";
    			
    			int result = stmt.executeUpdate(SQL);
//    			�Φb�d��
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
		
//		try{
//			URL map  = new URL(String.format("http://maps.googleapis.com/maps/api/geocode/json?address=%s&sensor=false&language=zh-TW", 
//	                URLEncoder.encode(	
//	                		"�򤺩]����", "UTF-8")));//p=%s is KeyWord in
//	                URLConnection connection = map.openConnection();
//	                String line;
//	                StringBuilder builder = new StringBuilder();
//	                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
//	                while ((line = reader.readLine()) != null) {builder.append(line);}
//	                JSONObject json = new JSONObject(builder.toString()); //�ഫjson�榡
//	                JSONArray ja = json.getJSONArray("results");//���ojson��Array����
//	                for (int i1 = 0; i1 < ja.length(); i1++) {
//	                      System.out.print("�a�}�G" + ja.getJSONObject(i1).getString("formatted_address") + " ");
//	                      System.out.print("�n�סG" + ja.getJSONObject(i1).getJSONObject("geometry").getJSONObject("location").getDouble("lat") + " ");
//	                      System.out.print("�g�סG" + ja.getJSONObject(i1).getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
//	                      System.out.println("");
//	                }
//		}catch(Exception e){
//			
//		}
		
	}
}


