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

<title>编辑选秀周期</title>

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
 	    url:'<%=request.getContextPath()%>/cycle_saveCycle.action',
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
        <%--	
       	var actionUrl = "<%=request.getContextPath()%>/cycle_selectInit.action?tableName=themes&columns=id,name";  
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
	    	  var theme = $("#theme");
	    	  for(var i=0;i<result.length;i++){
	    		  theme.append('<option value="'+result[i][0]+'">'+result[i][1]+'</option>');
	    	  }

	 		 $("#theme").val('${cycle.themeId }')
	      }
   		});--%>
	 });
	 function checkedForm(){
		if ($.trim($("#cycleNo").val()) == "") {
			alert("主题标题不能为空!");
			return false;
		}
		/**
		if ($.trim($("#starttime").val()) == "") {
			alert("开始时间不能为空!");
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
					<input type="hidden" id="id" name="cycle.id" value="${cycle.id}"
						style="width: 500px" />
					<tr>
						<td align="right">主题标题：</td>
						<td align="left"><input id="cycleNo" name="cycle.themeTitle"
							value="${cycle.themeTitle}" style="width:100px" />
						</td>
						<td align="right">周期状态：</td>
						<td align="left"><select id="status" name="cycle.status"
							style="width:108px;">
								<option value="0"
									<c:if test="${cycle.status==0 }">selected="selected"</c:if>>已结束</option>
								<option value="1"
									<c:if test="${cycle.status==1 }">selected="selected"</c:if>>进行中</option>
								<option value="2"
									<c:if test="${cycle.status==2 }">selected="selected"</c:if>>未开始</option>
						</select></td>
					</tr>
					<tr>
						<td align="right">活动开始时间：</td>
						<td align="left"><input type="text" id="starttime"
							name="cycle.starttime" value="${cycle.starttime }" class="input"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endtime\')}'})"
							readonly="readonly" style="width:100px;" />
						</td>
						<td align="right">活动结束时间：</td>
						<td align="left"><input id="endtime" name="cycle.endtime"
							value="${cycle.endtime}" class="input"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'starttime\')}'})"
							readonly="readonly" style="width:100px;" />
						</td>
					</tr>
					<tr>
						<td align="right">投票开始时间：</td>
						<td align="left"><input type="text" id="voteStartTime"
							name="cycle.voteStartTime" value="${cycle.voteStartTime }" class="input"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'voteEndTime\')}'})"
							readonly="readonly" style="width:100px;" />
						</td>
						<td align="right">投票结束时间：</td>
						<td align="left"><input id="voteEndTime" name="cycle.voteEndTime"
							value="${cycle.voteEndTime}" class="input"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'voteStartTime\')}'})"
							readonly="readonly" style="width:100px;" />
						</td>
					</tr>
					<tr>
						<td align="right">背景图：</td>
						<td align="left"><input type="file" name="bgImage" /></td>
						<td align="right">选秀详情图片：</td>
						<td align="left"><input type="file" name="detailImage" /></td>
					</tr>
					<tr>
						<td align="right">选秀详情内部图片：</td>
						<td align="left"><input type="file" name="insideDetailImage" /></td>
						<td align="right">网页主题详情图片：</td>
						<td align="left"><input type="file" name=webDetailImage /></td>
					</tr>
					<tr>
						<td align="right">描述：</td>
						<td colspan="3"><textarea rows="5" cols="15" id="descs"
								name="cycle.descs">${cycle.descs }</textarea></td>
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
