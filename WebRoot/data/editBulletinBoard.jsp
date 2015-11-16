<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<title>编辑公告</title>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/jqGrid/themes/cupertino/jquery-ui-1.7.2.custom.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/jqGrid/css/jqgrid.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jqGrid/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jqGrid/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jqGrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jqGrid/js/jquery.form.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jqGrid/js/grid.locale-cn.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/DatePicker/WdatePicker.js"></script>
<style type="text/css">
body {
	background: #ffffff;
	background-image: url(images/);
}
</style>
<script type="text/javascript">
  	$(document).ready(function(){
  		var optionsSubmit = {
 	    url:'<%=request.getContextPath()%>/cycle_saveBulletinBoard.action',
 	    dataType:'json',
 	    success: function(data) {
   	 		if(data.result=='success'){
				alert("保存成功");
				window.close();
	  	    }else{
	  	    	alert("保存失败");
	  	    }
   			}
 	    };
      	$('#submitBtn').click(function(){
    	if(checkedForm()){
		    $('#pageFrom').ajaxSubmit(optionsSubmit);
	        return false;
        }
    	});
	 });
	 function checkedForm(){
		if ($.trim($("#url").val()) == "") {
			alert("跳转url不能为空!");
			return false;
		}
		
		/**if ($.trim($("#bgImage").val()) == "") {
			alert("图片url不能为空!");
			return false;
		}
		if ($.trim($("#signupEndtime").val()) == "") {
			alert("报名结束时间不能为空!");
			return false;
		}
		if ($.trim($("#endtime").val()) == "") {
			alert("周期结束时间不能为空!");
			return false;
		}*/
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

.button_b {
	height: 18px;
	width: 58.5px;
	background-image: url(<%=request.getContextPath()%>/images/inputBg3.png
		);
	background-size: cover;
	background-color: transparent;
	border: none;
}
</style>
</head>
<body>
	<form action="" id="pageFrom" name="" method="post">
		<fieldset class="fieldsetStyle">
			<legend>
				<font size="3">基本信息</font>
			</legend>
			<div class="fieldsetContent">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="infoTableSpace">
					<input type="hidden" id="id" name="bulletinBoard.id" value="${bulletinBoard.id}"
						style="width: 500px" />
					<tr>
						<td align="right">跳转类型：</td>
						<td align="left"><select id="type" name="type"
							style="width:108px;">
								<option <c:if test="${type=='webview' }">selected="selected"</c:if> value="webview">网页</option>
								<option <c:if test="${type=='theme' }">selected="selected"</c:if> value="theme">主题</option>
						</select></td>
						<td align="right">主键：</td>
						<td align="left"><input id="url" name="bulletinBoard.url"
							value="${bulletinBoard.url}" style="width:100px" />
						</td>
					</tr>
					<tr>
						<td align="right">是否显示：</td>
						<td align="left"><select id="display" name="bulletinBoard.display"
							style="width:108px;">
								<option value="0"
									<c:if test="${bulletinBoard.display==0 }">selected="selected"</c:if>>显示</option>
								<option value="1"
									<c:if test="${bulletinBoard.display==1 }">selected="selected"</c:if>>不显示</option>
						</select></td>
						<td align="right">图片url：</td>
						<td align="left"><input type="file" name="bgImage" /></td>
					</tr>
					<tr>
						<td colspan="4" align="center"><c:if test="${flag!=1 }">
								<input type="button" id="submitBtn" value="保 存" class="button_b" />
							</c:if> <input type="button" value="取 消" class="button_b"
							onclick="window.close();" /></td>
					</tr>
				</table>
			</div>
		</fieldset>
	</form>
</body>
</html>
