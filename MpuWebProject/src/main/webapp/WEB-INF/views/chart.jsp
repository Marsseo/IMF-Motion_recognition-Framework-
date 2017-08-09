<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<title>JSP Page</title>
		<!-- start: CSS -->
			<link id="bootstrap-style" href="<%=application.getContextPath()%>/resources/css/bootstrap.min.css" rel="stylesheet">
			<link href="<%=application.getContextPath()%>/resources/css/bootstrap-responsive.min.css" rel="stylesheet">
			<link id="base-style" href="<%=application.getContextPath()%>/resources/css/style.css" rel="stylesheet">
			<link id="base-style-responsive" href="<%=application.getContextPath()%>/resources/css/style-responsive.css" rel="stylesheet">
			<link
				href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800&subset=latin,cyrillic-ext,latin-ext'
				rel='stylesheet' type='text/css'
			>
		<!-- end: CSS -->
		
		<link href="<%= application.getContextPath() %>/resources/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
		<script src="<%= application.getContextPath() %>/resources/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
		<script src="<%= application.getContextPath() %>/resources/bootstrap-3.3.7/js/bootstrap.min.js" type="text/javascript"></script>
		<meta name="viewport" content="width=device-width, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
		<!--  하이차트 start -->
		<script	src="<%=application.getContextPath()%>/resources/highcharts/code/highcharts.js"></script>
		<script	src="<%=application.getContextPath()%>/resources/highcharts/code/themes/gray.js"></script>
		<script	src="<%=application.getContextPath()%>/resources/highcharts/code/highcharts-more.js"></script>
		<script src="<%=application.getContextPath()%>/resources/js/ultrasonicChart.js"></script>
		<script src="<%=application.getContextPath()%>/resources/js/ifraredrayChart.js"></script>
		<script src="<%=application.getContextPath()%>/resources/js/gyroChart.js"></script>
		<!-- 하이차트 end -->
	</head>
<body>
	<div id="gyroChartContainer"></div>
	<div id="ultrasonicChartContainer"></div>
	<div id="ifraredrayChartContainer"></div>
</body>
</html>