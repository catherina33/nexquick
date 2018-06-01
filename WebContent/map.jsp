<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<title>Daum ���� �����ϱ�</title>
</head>
<body>
	<div id="map" style="width:500px;height:500px;"></div>

	<script type="text/javascript" src = "./js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7039bd6474ba59d8e1698a4fe4b35471"></script>
	<script>
		var container = document.getElementById('map');
		var options = {
			center: new daum.maps.LatLng(37.501410, 127.039621), //������ �߽��� �� ��ġ (������ �ڿ��� ��輳���ؼ� ���ǹ������� ����� �۵��ȵ�)
			level: 5 //1 : ���� �ڼ��ϰ� -> 5: �а� ������.
		};

		var map = new daum.maps.Map(container, options);	////������ �����ȴ�.
		var bounds = new daum.maps.LatLngBounds();   
		
		var imageSrc0 = 'images/scooter.png', // ��Ŀ�̹����� �ּ��Դϴ�    
  		 	 imageSize0 = new daum.maps.Size(40, 40), // ��Ŀ�̹����� ũ���Դϴ�
   			 imageOption0 = {offset: new daum.maps.Point(27, 69)}; 

		
		var markerImage0 = new daum.maps.MarkerImage(imageSrc0, imageSize0, imageOption0),
	    markerPosition0 = new daum.maps.LatLng(33.452278, 126.567803); // ��Ŀ�� ǥ�õ� ���� ��ġ�Դϴ�
		
	    var marker0 = new daum.maps.Marker({
	    	  position: markerPosition0,
	    	  image: markerImage0 // ��Ŀ�̹��� ���� 
	    	});
		
	    marker0.setMap(map);
	    bounds.extend(markerPosition0);
		
	    
		var imageSrc1 = 'images/location.png', // ��Ŀ�̹����� �ּ��Դϴ�    
		 	 imageSize1 = new daum.maps.Size(40, 40), // ��Ŀ�̹����� ũ���Դϴ�
			 imageOption1 = {offset: new daum.maps.Point(27, 69)}; 

	
		var markerImage1 = new daum.maps.MarkerImage(imageSrc1, imageSize1, imageOption1),
  			 markerPosition1 = new daum.maps.LatLng(33.452671, 126.574792); // ��Ŀ�� ǥ�õ� �ֹ��� ��ġ�Դϴ�
	
  		 var marker1 = new daum.maps.Marker({
   	  position: markerPosition1,
   	  image: markerImage1 // ��Ŀ�̹��� ���� 
   	});
	
   marker1.setMap(map);
   bounds.extend(markerPosition1);
	    
   
/*    
   var content = '<div class="customoverlay">' +
   '    <span class="title">���� ��ġ</span>' +
   '</div>';

//Ŀ���� �������̰� ǥ�õ� ��ġ�Դϴ� 
var position = new daum.maps.LatLng(33.452278, 126.567803);  

//Ŀ���� �������̸� �����մϴ�
var customOverlay = new daum.maps.CustomOverlay({
   map: map,
   position: position,
   content: content,
   yAnchor: 1 
}); */
   
	   

		$(function(){
			map.setBounds(bounds);
		});
		

		
	</script>
	

</body>
</html>