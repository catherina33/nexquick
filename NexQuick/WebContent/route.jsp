<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<script type="text/javascript" src = "./js/jquery-3.3.1.min.js"></script>	
<script>		


var headers = {}; 
headers["appKey"]="5096fcfd-6200-419f-b367-a37263afc3cc";
$.ajax({
	
	method:"POST",
	headers : {"appKey":"5096fcfd-6200-419f-b367-a37263afc3cc"},
	url:"https://api2.sktelecom.com/tmap/routes?version=1&format=xml",//�ڵ��� ��ξȳ� api ��û url�Դϴ�.
	async:false,
	data:{
		//����� ���浵 ��ǥ�Դϴ�.
		//37.501384, 127.039696
		startX : "127.039696",
		startY : "37.501384",
		//������ ���浵 ��ǥ�Դϴ�.
		//37.502143, 127.026356
		endX : "127.026356",
		endY : "37.502143",
		//�����, ������, ������ ��ǥ�� ������ �����մϴ�.
		reqCoordType : "WGS84GEO",
		resCoordType : "EPSG3857",
/* 		//�����Դϴ�.
		angle : "172", */
		//��� Ž�� �ɼ� �Դϴ�.
		searchOption : 0
	},
	//������ �ε尡 ���������� �Ϸ�Ǿ��� �� �߻��ϴ� �Լ��Դϴ�.
	success:function(response){
		prtcl = response;
	
		console.log("����!")
		// ��� ���
		var innerHtml ="";
		var prtclString = new XMLSerializer().serializeToString(prtcl);//xml to String	
	    xmlDoc = $.parseXML( prtclString ),
	    $xml = $( xmlDoc ),
    	$intRate = $xml.find("Document");
    	
    	var tDistance = "�� �Ÿ� : "+($intRate[0].getElementsByTagName("tmap:totalDistance")[0].childNodes[0].nodeValue/1000).toFixed(1)+"km,";
    	var tTime = " �� �ð� : "+($intRate[0].getElementsByTagName("tmap:totalTime")[0].childNodes[0].nodeValue/60).toFixed(0)+"��,";	
    	
		
    	console.log(tDistance+"..�Ÿ� �� ���� ");
    	console.log(tTime+"..�ð� �� ����");
    	
    	$("#result").text(tDistance+tTime);
		

	},
	//��û ���н� �ܼ�â���� ���� ������ Ȯ���� �� �ֽ��ϴ�.
	error:function(request,status,error){
		console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	}
});



</script>
</head>
<body>

</body>

</html>