<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script type="text/javascript" src="Script/jquery-1.4.4.js"></script>
	
	<script type="text/javascript">
	
		$(function()
		{
			$("#search").click(function()
			{
				$.get(
					"GetPictureUrls",
					{searchKey:encodeURI($("#searchKey").val()), source:$("input[name='source']:checked").val()},
					function(data)
					{
						$("#result").html("");
						
						var html = "";
						for(var i = 0; i < data.length; i++)
						{
							html += "<img width='320px' src=" + data[i] + ">";
						}

						$("#result").append(html);
					},
					"json"
				);
			});
			
			$("#save").click(function()
			{
				$.get(
					"SavePicture",
					{},
					function()
					{
						alert("those picture are all saved!");
					}
				);
			});

		});

	</script>
  </head>
  
  <body>
    
    <div style="border:1px solid red; background-color:pink; padding-left:50px; padding-bottom:10px">
    
    	<h3>PictureTools</h3>
    
    	please input search key: &nbsp;&nbsp;<input type="text" id="searchKey"> &nbsp;&nbsp;&nbsp;&nbsp;
    	
    	<input type="radio" value="google" name="source" checked="checked">Google &nbsp;&nbsp;<input type="radio" value="flickr" name="source">Flickr &nbsp;&nbsp;
    	
    	<input type="button" id="search" value="Search"> &nbsp;&nbsp;<!--  <input type="button" id="save" value="Save"> -->
    
    </div>
    
    <div id="result"></div>

  </body>
</html>
