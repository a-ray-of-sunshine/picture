package picture;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import util.Constant;

public class GetPictureUrls extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		String searchKey = req.getParameter("searchKey");
		String source = req.getParameter("source");

		// 存储从不同的源中获取的URL
		List<String> urls = new ArrayList<String>();

		if ("google".equals(source))
		{
			// 图片源为google
			for (int i = 0; i < 16; i++)
			{
				int start = i * 4;
				StringBuffer buffer = new StringBuffer(Constant.GOOGLE_BASEURL);

				buffer.append("start=").append(start).append("&q=")
						.append(searchKey);

				String result = new Connection(buffer.toString()).queryResult();

				try
				{
					JSONObject json = new JSONObject(result);
					JSONObject responseData = json
							.getJSONObject("responseData");
					JSONArray results = responseData.getJSONArray("results");

					for (int j = 0; j < results.length(); j++)
					{
						JSONObject data = results.getJSONObject(j);

						String url = data.getString("url");
						urls.add(url);
					}
				}
				catch (JSONException e)
				{
					e.printStackTrace();
				}

			}
		}
		else
		{
			// 图片源为flickr
			StringBuffer buffer = new StringBuffer(Constant.FLICKR_BASEURL);

			buffer.append("tags=").append(searchKey);

			String result = new Connection(buffer.toString()).queryResult();

			result = result.substring(14, result.length() - 1);

			try
			{
				JSONObject json = new JSONObject(result);
				JSONObject photos = json.getJSONObject("photos");
				JSONArray photo = photos.getJSONArray("photo");

				for (int i = 0; i < photo.length(); i++)
				{
					JSONObject data = photo.getJSONObject(i);

					String id = data.getString("id");
					String secret = data.getString("secret");
					String server = data.getString("server");
					String farm = data.getString("farm");

					// http://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg
					StringBuffer url = new StringBuffer("http://farm")
							.append(farm).append(".staticflickr.com/")
							.append(server).append("/").append(id).append("_")
							.append(secret).append(".jpg");
					
					urls.add(url.toString());

				}

			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
		}

		req.getSession().setAttribute("urls", urls);
		req.getSession().setAttribute("searchKey", searchKey);
		req.getSession().setAttribute("source", source);
		
		String result = new Gson().toJson(urls);

		resp.setContentType("json");
		resp.setHeader("pragma", "no-cache");
		resp.setHeader("cache-control", "no-cache");

		PrintWriter pw = resp.getWriter();
		pw.write(result);
		pw.flush();
		pw.close();
	}
}
