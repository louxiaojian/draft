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

<title>编辑数据</title>
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
          	$(document).ready(function(){
          		var optionsSubmit = {
    	    	    url:'<%=request.getContextPath()%>/locker_saveDataImg.action',
    	    	    dataType:'json',
    	    	    success: function(data) {
    	    	    	 if(data.result=='success'){
    							alert("保存成功");
    							window.returnValue = data;
    							window.close();
    		    	      }else{
    		    	      	window.returnValue = "error";
    		    	    	  alert("保存失败");
    		    	      }
    	    	}};
	          	$('#submitBtn').click(function(){
			    	if(checkedForm()){
					    $('#pageFrom').ajaxSubmit(optionsSubmit);
				        return false;
			        }
			     });
			 });
			 
			 function checkedForm(){
				 if ($.trim($("#title").val()) == "") {
						alert("标题不能为空!");
						return false;
					}
					if ($.trim($("#collect_time").val()) == "") {
						alert("时间不能为空!");
						return false;
					}
					if ($.trim($("#collect_website").val()) == "") {
						alert("来源网站不能为空!");
						return false;
					}
					var d=/\.[^\.]+$/.exec($("#url").val())+'';
					if (d.toLowerCase() == ".gif") {
						var select = document.getElementById("data_type");  
						for(var i=0; i<select.options.length; i++){  
						    if(select.options[i].innerHTML == '动态图'){  
						        select.options[i].selected = true;  
						        break;  
						    }  
						}
						return true;
					}
					if ($.trim($("#data_type").val()) == "gif") {
						if(d.toLowerCase()!='.gif'){
							alert("类型与url不匹配!");
							return false;
						}
					}
					if (d.toLowerCase() != ".jpg"&&d.toLowerCase() != ".png") {
						if($("#data_type").val()=='joke'||$("#data_type").val()=='news'){
							alert("类型与url不匹配!");
							return false;
						}
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

.infoTableSpace input {
	border: 1px solid #999;
	padding: 5px;
	width: 180px;
	-moz-border-radius: 3px;
	border-radius: 3px;
	margin-bottom: 5px;
	color: #666;
	background: url(../images/input_bg.gif) repeat-x top;
}

.infoTableSpace select {
	border: 1px solid #999;
	padding: 4px;
	width: 190px;
	-moz-border-radius: 3px;
	border-radius: 3px;
	margin-bottom: 3px;
	color: #666;
	background: url(../images/input_bg.gif) repeat-x top;
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

.infoTableSpace .form_bt_grey {
	width: 80px;
	border: 1px solid #aaaaaa;
	background-image: url(../images/bt_bg2.gif);
	height: 27px;
	background-repeat: repeat-x;
	background-color: #e9e9e9;
	color: #333333;
	padding: 0 15px 0 15px;
}

.infoTableSpace .form_bt_orange {
	width: 80px;
	border: 1px solid #aaaaaa;
	background-image: url(../web/images/bt_bg1.gif);
	height: 27px;
	background-repeat: repeat-x;
	background-color: #ffa00a;
	color: #FFFFFF;
	padding: 0 15px 0 15px;
	font-weight: bold;
}

.infoTableSpace .form_bt_blue {
	width: 80px;
	border: 1px solid #6081c3;
	background-image: url(../images/bt_bg1.gif);
	height: 27px;
	background-repeat: repeat-x;
	background-color: #ffa00a;
	color: #FFFFFF;
	padding: 0 15px 0 15px;
	font-weight: bold;
}

#gridTable input {
	border: 1px solid #fff;
	padding: 0px;
	width: 20px;
	-moz-border-radius: 0px;
	border-radius: 0px;
	margin-bottom: 0px;
	color: #666;
	background: url() repeat-x top;
}

#cb_gridTable {
	border: 1px solid #e6e6e6;
	padding: 0px;
	width: 20px;
	-moz-border-radius: 0px;
	border-radius: 0px;
	margin-bottom: 0px;
	color: #666;
	background: url(images/ui-bg_glass_75_e6e6e6_1x400.png) #e6e6e6 repeat-x
		50% 50%;
}

#gridPager input {
	border: 1px solid #ccc;
	padding: 1px;
	padding-top: 2px;
	width: 25px;
	height: 14px;
	-moz-border-radius: 0px;
	border-radius: 0px;
	margin-bottom: 0px;
	color: #666;
	background: url() repeat-x top;
	background-color: #fff;
}

#gridPager select {
	border: 1px solid #ccc;
	padding: 0px;
	width: 50px;
	-moz-border-radius: 0px;
	border-radius: 0px;
	margin-bottom: 0px;
	color: #666;
	background: url() repeat-x top;
	background-color: #fff;
}
</style>
</head>
<body>
	<form action="" id="pageFrom" name="" method="post">
		<br />
		<fieldset class="fieldsetStyle">
			<legend>
				<font size="3">基本信息</font>
			</legend>
			<div class="fieldsetContent">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="infoTableSpace">
					<input type="hidden" id="id" name="dataImgTable.id" value="${dataImgTable.id}" style="width: 500px" />
					<tr>
						<td align="right">标题：</td>
						<td align="left"><input id="title" name="dataImgTable.title"
							value="${dataImgTable.title}" style="width: 500px" /></td>
					</tr>
					<tr>
						<td align="right">url：</td>
						<td align="left"><input type="text" id="url"
							name="dataImgTable.url" value="${dataImgTable.url}" style="width: 500px" /></td>
					</tr>
					<tr>
						<td align="right">内容：</td>
						<td align="left"><input type="text" id="imgUrl"
							name="dataImgTable.imgUrl" value="${dataImgTable.imgUrl}" style="width: 500px" /></td>
					</tr>
					<tr>
						<td align="right">来源网站：</td>
						<td align="left"><input id="collect_website" name="dataImgTable.collect_website"
							value="${dataImgTable.collect_website}" style="width: 200px" /></td>
					</tr>
					<tr>
						<td align="right">数据类型：</td>
						<td align="left">
						<select id="data_type" name="dataImgTable.data_type" style="width:200px;">
								<option value="0">--请选择--</option>
								<option value="joke" <c:if test="${dataImgTable.data_type=='joke'}">selected="selected"</c:if>>搞笑</option>
								<option value="news" <c:if test="${dataImgTable.data_type=='news'}">selected="selected"</c:if>>新闻</option>
								<option value="film" <c:if test="${dataImgTable.data_type=='film'}">selected="selected"</c:if>>电影</option>
								<option value="game" <c:if test="${dataImgTable.data_type=='game'}">selected="selected"</c:if>>游戏</option>
								<option value="gif" <c:if test="${dataImgTable.data_type=='gif'}">selected="selected"</c:if>>动态图</option>
								<option value="html" <c:if test="${dataImgTable.data_type=='html'}">selected="selected"</c:if>>HTML</option>
						</select>
					</tr>
					<tr>
						<td align="right">采集时间：</td>
						<td align="left"><input id="collect_time"
							name="dataImgTable.collect_time"  readonly="readonly" value="${dataImgTable.collect_time}"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 200px"  /></td>
					</tr>
					<tr>
						<td colspan="4" align="center">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="4" align="center">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="4" align="center">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="4" align="center"><input type="button"
							id="submitBtn" value="保 存" class="form_bt_orange" /> <input
							type="button" value="取 消" class="form_bt_orange"
							onclick="window.close();" /></td>
					</tr>
				</table>
			</div>
		</fieldset>
	</form>
</body>
</html>
