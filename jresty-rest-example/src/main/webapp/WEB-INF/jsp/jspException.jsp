
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>jspException</title>
</head>

<body>
	
	<%
	int x = 4;
	int y = 5;
	int z = 7;
	if (x + y > z) {
		throw new Exception("Exception in JSP");
	}
	%>

</body>
</html>
