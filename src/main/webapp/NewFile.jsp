<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>Yo!</h1>
</body>
</html>
<% String id = request.getParameter("registered_number");
	String name = request.getParameter("name");
	try{
		Connection con = GetConnection.getConnection();
		String sql = "select * from students";
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
	}catch(Exception e){
		
	}
	
	
	
	%>