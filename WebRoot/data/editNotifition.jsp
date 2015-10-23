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

<title>编辑通知</title>
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
 	    url:'<%=request.getContextPath()%>/user_pushNotification.action',
 	    dataType:'json',
 	    success: function(data) {
   	 		if(data.result=='success'){
   	 		alert("通知发送成功！");
				window.close();
	  	    }else{
	  	    	alert("通知发送失败！");
	  	    }
   		}};
       	$('#submitBtn').click(function(){
	    	if(checkedForm()){
	       		if(!confirm("是否确认推送通知到全部设备 ？")){
	    			return false;
	    		}
			    $('#pageFrom').ajaxSubmit(optionsSubmit);
		        return false;
	        }
	    });
       	
       	var actionUrl = "<%=request.getContextPath()%>/cycle_selectInit.action?tableName=theme_cycle&columns=id,theme_title&whereCol=status&whereVal=1";  
		$.ajax({  
		  url : actionUrl,  
	      type : "post", 
	      dataType : "json",  
	      cache : false,  
	      error : function(textStatus, errorThrown) {  
	          alert("系统ajax交互错误: " + textStatus.value);  
	      },  
	      success : function(data, textStatus) {
	    	  var result =data.data;
	    	  var cycle = $("#themeCycleId");
	    	  for(var i=0;i<result.length;i++){
	    		  cycle.append('<option value="'+result[i][0]+'">'+result[i][1]+'</option>');
	    	  }
	      }  
		});
	 });

	 function checkedForm(){
		 if ($.trim($("#type").val()) == "") {
			alert("先选择通知类别!");
			return false;
		}
		 if ($.trim($("#content").val()) == "") {
				alert("请填写通知内容!");
				return false;
			}
		return true;
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
					<tr>
						<td align="right"  style="width: 80px;">通知类别：</td>
						<td align="left" style="width: 250px;">
							<select id="type" name="type" style="width:150px;">
								<option value="" selected="selected">--请选择--</option>
								<option value="0">版本更新</option>
								<option value="1">活动新主题</option>
						</select>
						</td>
						<td>选秀主题：<select id="themeCycleId" name="themeCycleId" style="width:100px;">
										<option value="">--请选择--</option>
								</select>
						</td>
					</tr>
					<tr>
						<td align="right" style="width: 100px;">通知内容：</td>
						<td align="left" style="width: 200px;" colspan="3">
							<textarea rows="6" cols="20" style="width:400px;" id="content" name="content"></textarea>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
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
