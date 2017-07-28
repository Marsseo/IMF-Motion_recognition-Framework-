
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP Page</title>


<!-- start: Mobile Specific -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- end: Mobile Specific -->

<!-- start: CSS -->
<link id="bootstrap-style"
	href="<%=application.getContextPath()%>/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="<%=application.getContextPath()%>/resources/css/bootstrap-responsive.min.css"
	rel="stylesheet">
<link id="base-style"
	href="<%=application.getContextPath()%>/resources/css/style.css"
	rel="stylesheet">
<link id="base-style-responsive"
	href="<%=application.getContextPath()%>/resources/css/style-responsive.css"
	rel="stylesheet">
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800&subset=latin,cyrillic-ext,latin-ext'
	rel='stylesheet' type='text/css'>
<!-- end: CSS -->

<!-- start: Favicon -->
<link rel="shortcut icon"
	href="<%=application.getContextPath()%>/resources/img/favicon.ico">
<!-- end: Favicon -->

<style type="text/css">
body {
	background: url(resources/img/bg-login.jpg) !important;
}
</style>
</head>
<body>
	<script>
		//This is called with the results from from FB.getLoginStatus().
		function statusChangeCallback(response) {
			console.log('statusChangeCallback');
			console.log(response);
			// The response object is returned with a status field that lets the
			// app know the current login status of the person.
			// Full docs on the response object can be found in the documentation
			// for FB.getLoginStatus().
			if (response.status === 'connected') {
				// Logged into your app and Facebook.
				testAPI();
			} else if (response.status === 'not_authorized') {
				// The person is logged into Facebook, but not your app.
				document.getElementById('status').innerHTML = 'Please log '
						+ 'into this app.';
			} else {
				// The person is not logged into Facebook, so we're not sure if
				// they are logged into this app or not.
				document.getElementById('status').innerHTML = 'Please log '
						+ 'into Facebook.';
			}
		}

		// This function is called when someone finishes with the Login
		// Button. See the onlogin handler attached to it in the sample
		// code below.
		function checkLoginState() {
			FB.getLoginStatus(function(response) {
				statusChangeCallback(response);
			});
		}
		window.fbAsyncInit = function() {
			FB.init({
				appId : '1541339359250690',
				cookie : true,
				xfbml : true,
				version : 'v2.8'
			});
			FB.AppEvents.logPageView();
			FB.getLoginStatus(function(response) {
				statusChangeCallback(response);
			});
		};

		(function(d, s, id) {
			var js, fjs = d.getElementsByTagName(s)[0];
			if (d.getElementById(id)) {
				return;
			}
			js = d.createElement(s);
			js.id = id;
			js.src = "//connect.facebook.net/en_US/sdk.js";
			fjs.parentNode.insertBefore(js, fjs);
		}(document, 'script', 'facebook-jssdk'));

		function testAPI() {
			console.log('Welcome! Fetching your information.... ');
			FB
					.api(
							'/me',
							{
								fields : 'email, name'
							},
							function(response) {
								console.log('Successful login for: '
										+ response.name);
								console.log('Successful login for: '
										+ response.email);
								document.getElementById('status').innerHTML = 'Thanks for logging in, '
										+ response.name + '!';
							});
		}
	</script>



	<fb:login-button scope="public_profile,email"
		onlogin="checkLoginState();">
	</fb:login-button>

	<div id="status"></div>
	<div class="container-fluid-full">
		<div class="row-fluid">

			<div class="row-fluid">
				<div class="login-box">
					<div class="icons">
						<a href="home"><i class="halflings-icon home"></i></a> <a href="#"><i
							class="halflings-icon cog"></i></a>

					</div>
					<h2>Login to your facebook account</h2>
					<form class="form-horizontal" action="home" method="post">
						<fieldset>

							<div class="input-prepend" title="Username">
								<span class="add-on"><i class="halflings-icon user"></i></span>
								<input class="input-large span10" name="username" id="username"
									type="text" placeholder="type username" />
							</div>
							<div class="clearfix"></div>

							<div class="input-prepend" title="Password">
								<span class="add-on"><i class="halflings-icon lock"></i></span>
								<input class="input-large span10" name="password" id="password"
									type="password" placeholder="type password" />
							</div>
							<div class="clearfix"></div>

							<label class="remember" for="remember"><input
								type="checkbox" id="remember" />Remember me</label>

							<div class="button-login">
								<button type="submit" class="btn btn-primary">Login</button>
							</div>
							<div class="clearfix"></div>
					</form>
					<hr>
					
				</div>
				<!-- /span -->
			</div>
			<!-- /row -->


		</div>
		<!-- /.fluid-container -->

	</div>
	<!-- /fluid-row -->

	<!-- start: JavaScript -->

	<script src="resources/js/jquery-1.9.1.min.js"></script>
	<script src="resources/js/jquery-migrate-1.0.0.min.js"></script>

	<script src="resources/js/jquery-ui-1.10.0.custom.min.js"></script>

	<script src="resources/js/jquery.ui.touch-punch.js"></script>

	<script src="resources/js/modernizr.js"></script>

	<script src="resources/js/bootstrap.min.js"></script>

	<script src="resources/js/jquery.cookie.js"></script>

	<script src='resources/js/fullcalendar.min.js'></script>

	<script src='resources/js/jquery.dataTables.min.js'></script>

	<script src="resources/js/excanvas.js"></script>
	<script src="resources/js/jquery.flot.js"></script>
	<script src="resources/js/jquery.flot.pie.js"></script>
	<script src="resources/js/jquery.flot.stack.js"></script>
	<script src="resources/js/jquery.flot.resize.min.js"></script>

	<script src="resources/js/jquery.chosen.min.js"></script>

	<script src="resources/js/jquery.uniform.min.js"></script>

	<script src="resources/js/jquery.cleditor.min.js"></script>

	<script src="resources/js/jquery.noty.js"></script>

	<script src="resources/js/jquery.elfinder.min.js"></script>

	<script src="resources/js/jquery.raty.min.js"></script>

	<script src="resources/js/jquery.iphone.toggle.js"></script>

	<script src="resources/js/jquery.uploadify-3.1.min.js"></script>

	<script src="resources/js/jquery.gritter.min.js"></script>

	<script src="resources/js/jquery.imagesloaded.js"></script>

	<script src="resources/js/jquery.masonry.min.js"></script>

	<script src="resources/js/jquery.knob.modified.js"></script>

	<script src="resources/js/jquery.sparkline.min.js"></script>

	<script src="resources/js/counter.js"></script>

	<script src="resources/js/retina.js"></script>

	<script src="resources/js/custom.js"></script>
	<!-- 	end: JavaScript -->

</body>
</html>
