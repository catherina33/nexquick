<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<script type="text/javascript" src = "./js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="https://service.iamport.kr/js/iamport.payment-1.1.5.js"></script>
<script type="text/javascript">

$(function() {
	IMP.init('imp94690506');
	
	IMP.request_pay({
	    pg : 'inicis',
	    pay_method : 'card',
	    merchant_uid : 'merchant_' + new Date().getTime(),
	    name : '�ֹ���:�׽�Ʈ��',
	    amount : 100,
	    buyer_email : 'kimmingyu65@gmail.com',
	    buyer_name : '��α�',
	    buyer_tel : '010-4940-8292',
	    buyer_addr : '����Ư���� ������ �Ｚ��',
	    buyer_postcode : '123-456'
	}, function(rsp) {
	    if ( rsp.success ) {
	    	//[1] �����ܿ��� �������� ��ȸ�� ���� jQuery ajax�� imp_uid �����ϱ�
	    	jQuery.ajax({
	    		url: "/payments/complete", //cross-domain error�� �߻����� �ʵ��� ������ ���������� ����
	    		type: 'POST',
	    		dataType: 'json',
	    		data: {
		    		imp_uid : rsp.imp_uid
		    		//��Ÿ �ʿ��� �����Ͱ� ������ �߰� ����
	    		}
	    	}).done(function(data) {
	    		//[2] �������� REST API�� ��������Ȯ�� �� ���񽺷�ƾ�� �������� ���
	    		if ( everythings_fine ) {
	    			var msg = '������ �Ϸ�Ǿ����ϴ�.';
	    			msg += '\n����ID : ' + rsp.imp_uid;
	    			msg += '\n���� �ŷ�ID : ' + rsp.merchant_uid;
	    			msg += '\���� �ݾ� : ' + rsp.paid_amount;
	    			msg += 'ī�� ���ι�ȣ : ' + rsp.apply_num;
	
	    			alert(msg);
	    		} else {
	    			//[3] ���� ����� ������ ���� �ʾҽ��ϴ�.
	    			//[4] ������ �ݾ��� ��û�� �ݾװ� �޶� ������ �ڵ����ó���Ͽ����ϴ�.
	    		}
	    	});
	    } else {
	        var msg = '������ �����Ͽ����ϴ�.';
	        msg += '�������� : ' + rsp.error_msg;
	
	        alert(msg);
	    }
	});
	
});
</script>
</head>
<body>


</body>
</html>