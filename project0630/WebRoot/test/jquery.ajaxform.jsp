<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>jQuery ajaxForm</title>
<LINK rel="stylesheet" type="text/css" href="${baseurl}/js/easyui/styles/default.css">
<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>
	<script type="text/javascript">
	 	function myFormSubmit_l(){
	 		// 准备好Options对象  
	 		var options = {  
	 		    //target:     '#divToUpdate',  
	 		    url:        '${baseurl }/sys/user/insertsubmit.action',  //提交的地址，一般不用在这定义，一般使用form定义的action
	 		    datatype:'json',  //预期服务端响应的结果
	 		    success: function(result) {  //回调，执行完成要调用方法
	 		    	 //解析服务端响应的json结果
	 		      alert(result.username);  
	 		    } };  
	 		  
	 		   // 将options传给ajaxForm  
	 		$('#myForm').ajaxSubmit(options);  

	 	}
	 	
	 	//使用封装js
	 	
		function myFormSubmit() {
			jquerySubByFId('myForm', myFormSubmit_callback, null, "json");
		}
		function myFormSubmit_callback(data) {
			alert(data.username);
		}
	</script>
</head>
<body>
	<form id="myForm" action="${baseurl }/sys/user/insertsubmit.action" method="post">
		用户账号：<input type="text" name="sysUserCustom.usercode"><br/>
		用户名称：<input type="text" name="sysUserCustom.username"/><br/>
		<input type="button" onclick="myFormSubmit()" value="ajaxForm提交"/>
	</form>

</body>
</html>