package picture;


import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.NetWorkUtil;
import util.SaveImageThread;

public class SavePicture extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		@SuppressWarnings("unchecked")
		List<String> urls = (List<String>)req.getSession().getAttribute("urls");
		String searchKey = (String)req.getSession().getAttribute("searchKey");
		String source = (String)req.getSession().getAttribute("source");
		
		String path = req.getSession().getServletContext().getRealPath("/image");
		
		File directory = new File(path, source + "/" + searchKey);
		
		directory.mkdirs();
		
		for(int i = 0; i < urls.size(); i++)
		{
			String url = urls.get(i);
			
			int position = url.lastIndexOf("/");
			
			String imageName = url.substring(position + 1);
			
			File photo = new File(directory, imageName);
			
//			NetWorkUtil.saveImage2Local(url, photo);
			
			new SaveImageThread(url, photo).start();
		}

		System.out.println(urls.size());
	}
}
