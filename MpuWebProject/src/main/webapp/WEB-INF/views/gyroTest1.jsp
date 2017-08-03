<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<title>JSP Page</title>
		<link href="<%= application.getContextPath() %>/resources/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
		<script src="<%= application.getContextPath() %>/resources/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
		<script src="<%= application.getContextPath() %>/resources/bootstrap-3.3.7/js/bootstrap.min.js" type="text/javascript"></script>
		<meta name="viewport" content="width=device-width, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
		<style>
			body {
				font-family: Monospace;
				background-color: #000;
				margin: 0px;
				overflow: hidden;
			}
			#info {
				position: absolute;
				color: #fff;
				top: 0px;
				width: 100%;
				padding: 5px;
				text-align:center;
			}
			a {
				color: #fff;
			}
		</style>
	</head>
<body>
<div id="info"><a href="http://threejs.org" target="_blank" rel="noopener">three.js</a> - ConvexGeometry</div>

		<script src="<%= application.getContextPath() %>/resources/js/threejs/three.js"></script>
		<script src="<%= application.getContextPath() %>/resources/js/threejs/controls/OrbitControls.js"></script>
		<script src="<%= application.getContextPath() %>/resources/js/threejs/QuickHull.js"></script>
		<script src="<%= application.getContextPath() %>/resources/js/threejs/geometries/ConvexGeometry.js"></script>
		<script src="<%= application.getContextPath() %>/resources/js/threejs/Detector.js"></script>
		<script src="<%= application.getContextPath() %>/resources/js/threejs/libs/stats.min.js"></script>

		<script>
			if ( ! Detector.webgl ) Detector.addGetWebGLMessage();
			var group, camera, scene, renderer, yawAngle=0, pitchAngle=0, rollAngle=0, preyawAngle=0, prepitchAngle=0, prerollAngle=0;
			init();
			requestGyroSensorData();
			animate();
			function init() {
				scene = new THREE.Scene();
				renderer = new THREE.WebGLRenderer( { antialias: true } );
				renderer.setPixelRatio( window.devicePixelRatio );
				renderer.setSize( window.innerWidth, window.innerHeight );
				document.body.appendChild( renderer.domElement );
				// camera
				camera = new THREE.PerspectiveCamera( 70, window.innerWidth / window.innerHeight, 1, 1000 );
				camera.position.set( 15, 20, 0 );
				scene.add( camera );
				// controls
				var controls = new THREE.OrbitControls( camera, renderer.domElement );
				controls.minDistance = 20;
				controls.maxDistance = 80;
				controls.maxPolarAngle = Math.PI;
				scene.add( new THREE.AmbientLight( 0x222222 ) );
				// light
				var light = new THREE.PointLight( 0xffffff, 1 );
				camera.add( light );
				// helper
				scene.add( new THREE.AxisHelper( 20 ) );
				scene.add( new THREE.GridHelper( 50, 10 ) );
				
				// convex hull
				var meshMaterial = new THREE.MeshLambertMaterial( {
					color: 0xffffff,
					opacity: 0.7,
					transparent: true
				} );
				var meshGeometry = new THREE.ConvexBufferGeometry( pointsGeometry.vertices );
				var mesh = new THREE.Mesh( meshGeometry, meshMaterial );
				mesh.material.side = THREE.BackSide; // back faces
				mesh.renderOrder = 0;
				group.add( mesh );
				var mesh = new THREE.Mesh( meshGeometry, meshMaterial.clone() );
				mesh.material.side = THREE.FrontSide; // front faces
				mesh.renderOrder = 1;
				group.add( mesh );
				//
				window.addEventListener( 'resize', onWindowResize, false );
			}
			function randomPoint() {
				return new THREE.Vector3( THREE.Math.randFloat( - 1, 1 ), THREE.Math.randFloat( - 1, 1 ), THREE.Math.randFloat( - 1, 1 ) );
			}
			function onWindowResize() {
				camera.aspect = window.innerWidth / window.innerHeight;
				camera.updateProjectionMatrix();
				renderer.setSize( window.innerWidth, window.innerHeight );
			}
			function animate() {
				requestAnimationFrame( animate );
				group.rotation.x = prepitchAngle/200; //빨강 y값
				group.rotation.y = preyawAngle/200; //초록 z값
				group.rotation.z = prerollAngle/200; //파랑 x값
				render();
			}
			function render() {
				renderer.render( scene, camera );
			}
			function requestGyroSensorData(){
				var ws = new WebSocket("ws://"+location.host+"/MpuWebProject/websocket/GyroSensor");
				ws.onmessage = function(event){
					var data = JSON.parse(event.data);
					preyawAngle = data.yawAngle-180;
					prepitchAngle = data.pitchAngle-180;
					prerollAngle = data.rollAngle;
				};
				yawAngle = preyawAngle;
				pitchAngle = prepitchAngle;
				rollAngle = prerollAngle;
				
			}
		</script>

</body>
</html>