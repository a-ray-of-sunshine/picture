package picture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Connection
{
	private String queryUrl;
	
	public Connection(String queryUrl)
	{
		this.queryUrl = queryUrl;
	}
	
	public String queryResult() throws IOException
	{
		URL url = new URL(queryUrl);
		
		URLConnection connection = url.openConnection();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
		StringBuffer result = new StringBuffer();
		String line = "";
		
		while(null != (line = br.readLine()))
		{
			result.append(line);
		}
		return result.toString();
	}
	
}
