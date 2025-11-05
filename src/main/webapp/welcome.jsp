<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<body>
<h2>✅ Login Successful!</h2>
<p>Welcome, <%= request.getAttribute("uname") %>!</p>
<a href="login.html">Logout</a>
</body>
</html>
