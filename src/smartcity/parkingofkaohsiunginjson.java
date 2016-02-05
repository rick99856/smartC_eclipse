package smartcity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/*
 * json in url 
 * this is for kaohsiung for parking
 * 
 * 
 * */
public class parkingofkaohsiunginjson {
	public static void main(String argv[])throws Exception{
		URL url = new URL("http://data.kaohsiung.gov.tw/Opendata/DownLoad.aspx?Type=2&CaseNo1=AP&CaseNo2=13&FileType=2&Lang=C&FolderType=O");
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

                
                String areano = c.getString("郵遞區號");
                String area = c.getString("行政區");
                String type = c.getString("型式");
                String no = c.getString("編號");
                String name = c.getString("停車場名稱");
                String addr0 = c.getString("停車場位置");
                String payinfo = c.getString("收費標準(收費時間)");
                String worktime = c.getString("營業時間");
//                String ps = c.getString("備 註");
                Double Lat,Lng;
                String addr;
                try {
                    String sKeyWord = "台北市101";
                    URL gg  = new URL(String.format("http://maps.googleapis.com/maps/api/geocode/json?address=%s&sensor=false&language=zh-TW", 
                    URLEncoder.encode("高雄市"+addr0, "UTF-8")));//p=%s is KeyWord in
                     URLConnection connection = gg.openConnection();
                    String line;
                    StringBuilder builder = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
                    while ((line = reader.readLine()) != null) {builder.append(line);}
                    JSONObject json = new JSONObject(builder.toString()); //轉換json格式
                    JSONArray ja = json.getJSONArray("results");//取得json的Array物件
                    for (int i1 = 0; i1 < ja.length(); i1++) {
                          System.out.print("地址：" + ja.getJSONObject(i1).getString("formatted_address") + " ");
                          System.out.print("緯度：" + ja.getJSONObject(i1).getJSONObject("geometry").getJSONObject("location").getDouble("lat") + " ");
                          System.out.print("經度：" + ja.getJSONObject(i1).getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
                          System.out.println("");
                    }
                    Lat = ja.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                    Lng = ja.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                    
                    
                    addr = ja.getJSONObject(0).getString("formatted_address");
                } catch (Exception e){
                	Lat = 0.0;
                	Lng = 0.0;
                	addr = addr0;
                }

                Class.forName("com.mysql.jdbc.Driver");
    			
//    			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/smartc","s13113241","hs9m322x");
    			Connection conn = DriverManager.getConnection( "jdbc:mysql://"+"192.168.137.178"+"/"+"smartc"+"?useUnicode=true&characterEncoding=UTF-8","s13113241","hs9m322x");
    			Statement stmt = conn.createStatement();
    			String SQL = "INSERT INTO `smartc`.`"
    					+ "parking` (`no`, `area`, `type`, `name`, `addr`, `payinfo`, `worktime`, `ps`, `px`, `py`)"
    						+ " VALUES (NULL, '"+area+"','"+type+"' , '"+name+"', '"+addr+"', '"+payinfo+"', '"+worktime+"',  '' , '"+Lat+"' ,'"+Lng+"')";
    			
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


