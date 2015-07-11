package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class NetWorkUtil
{
	public static void saveImage2Local(String url, File photo) throws MalformedURLException, IOException
	{
		InputStream is = new URL(url).openStream();
		
		OutputStream os = new FileOutputStream(photo);
		
		byte[] b = new byte[1024];
		int length = 0;
		
		while(-1 != (length = is.read(b, 0, 1024)))
		{
			os.write(b, 0, length);
		}
	}
}
