package smartcity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.CDL;
import org.json.JSONArray;
/*
 * get the data in csv from url
 * 
 * 
 * 
 * 
 * */
public class getdatainurlforcsv{
	public static void main(String argv[])throws Exception {
		URL url = new URL("http://data.tainan.gov.tw/dataset/e70e2580-b45b-457f-8715-d1d0846434ac/resource/4a9a150a-e79a-4ae0-bcec-abd3e619f6a3/download/landmark2.csv");
//		URL url = new URL("http://www.2384.com.tw/qrcode/vstop?code=9211&rnd=0.14039480773190383");
		HttpURLConnection huc = (HttpURLConnection)url.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(huc.getInputStream(), "UTF-8"));


		String str = ""; 
		StringBuffer sb = new StringBuffer();
		
		while(null != ((str=br.readLine()))) {
			sb.append(str);
		}
		
		br.close();
		String xmlResponse = sb.toString(); 
//		JSONArray arr = CDL.toJSONArray(xmlResponse);
		
			System.out.print(xmlResponse);
			br.close();
			huc.disconnect();

		decode(xmlResponse);
	}
	public static void decode(String st){
		// System.out.print("123456");
	}
}