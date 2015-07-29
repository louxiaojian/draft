<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="org.apache.commons.lang.time.DateFormatUtils"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<base target="_self" />

<title>审核图片</title>
<%@include file="/include/jquerylib.jsp"%>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/demo1.css" />
<link rel="stylesheet" type="text/css"
	href="<%=path%>/js/jqueryui/themes/default/easyui.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jqueryui/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/web/style/layout2.css" />
<style type="text/css">
body {
	background: #ffffff;
	background-image: url(images/);
}
</style>
<script type="text/javascript">
	var opener=window.opener;
	if(opener!=null){
		
	}
  	$(document).ready(function(){
  		var optionsSubmit = {
 	    url:'<%=request.getContextPath()%>/photo_auditing.action',
 	    dataType:'json',
 	    success: function(data) {
   	 		if(data.result=='success'){
				alert("保存成功");
				window.close();
	  	    }else{
	  	    	alert("保存失败");
	  	    }
   		}};
       	$('#submitBtn').click(function(){
		    $('#pageFrom').ajaxSubmit(optionsSubmit);
	        return false;
	    });
	 });
    function closeMeAndReloadParent()
    {
	    alert(1)
    }
</script>

<style type="text/css">
html {
	overflow-x: hidden;
	overflow-y: auto;
}

.infoTableSpace {
	font-size: 12px;
}

.infoTableSpace textarea {
	border: 1px solid #999;
	padding: 5px;
	width: 590px;
	-moz-border-radius: 3px;
	border-radius: 3px;
	margin-bottom: 5px;
	color: #666;
	background: url(../images/input_bg.gif) repeat-x top;
}
.button_b{height: 18px;width: 58.5px;background-image: url(<%=request.getContextPath()%>/images/inputBg3.png) ;background-size:cover;background-color: transparent;border: none ;}
</style>
</head>
<body>
	<form action="" id="pageFrom" name="" method="post">
		<br />
		<fieldset class="fieldsetStyle">
			<legend>
				<font size="3">审核信息</font>
			</legend>
			<div class="fieldsetContent">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="infoTableSpace">
					<input type="hidden" id="ids" name="ids" value="<%=request.getParameter("ids") %>" style="width: 500px" />
					<tr>
						<td align="right">审核：</td>
						<td align="left">&nbsp;&nbsp;&nbsp;&nbsp;通过<input type="radio" name="status" value="1" checked="checked"/>&nbsp;&nbsp;&nbsp;&nbsp;不通过<input type="radio" name="status" value="2"/></td>
					</tr>
					<tr>
						<td align="right">描述：</td>
						<td align="left">
							<textarea rows="6" cols="30" id="descs" name="descs"></textarea>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" id="submitBtn" value="提交" class="button_b" />
							<input type="button" value="取 消" class="button_b" onclick="window.close();" />
						</td>
					</tr>
				</table>
			</div>
		</fieldset>
	</form>
</body>
</html>
