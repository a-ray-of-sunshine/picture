package util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class SaveImageThread extends Thread
{
	private String url;
	private File photo;
	
	public SaveImageThread(String url, File photo)
	{
		this.url = url;
		this.photo = photo;
	}
	
	@Override
	public void run()
	{
		try
		{
			NetWorkUtil.saveImage2Local(url, photo);
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
