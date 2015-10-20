<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<link href="../css/css.css" rel="stylesheet" type="text/css" />
<title>用户管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/jqGrid/themes/cupertino/jquery-ui-1.7.2.custom.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/jqGrid/css/jqgrid.css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/jqGrid/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/jqGrid/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/jqGrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/jqGrid/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/jqGrid/js/grid.locale-cn.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/DatePicker/WdatePicker.js"></script>
<c:if test="${requestScope.message!=null}">
	<script type="text/javascript">
	  	 		     alert('<c:out value="${requestScope.message}"></c:out>');
	  	 		     window.close();
	  			</script>
</c:if>
<style>
.ui-jqgrid tr.jqgrow td {
text-overflow : ellipsis;
}
.button_b{height: 18px;width: 36px;background-image: url(<%=request.getContextPath()%>/images/inputBg.png) ;background-size:cover;background-color: transparent;border: none ;}
.button_b1{height: 18px;width: 89px;background-image: url(<%=request.getContextPath()%>/images/inputBg2.png) ;background-size:cover;background-color: transparent;border: none ;}
.altclass{
	background: #f9fdfc;
}
</style>
<script type="text/javascript">
	var widthScroll=window.screen.width;
	var heightScroll=window.screen.height;
	var now = new Date();
	var dateStr = now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate()
	$(document).ready(function(){
		$("#gridTable").jqGrid({					
			url:'<%=request.getContextPath()%>/user_queryUsers.action?edit_date='+dateStr+'&temp='+Math.round(Math.random()*10000),
			datatype: "json",
			height: window.screen.availHeight/2.2,
			//autoheight: true,
			width: widthScroll/1.5, 
			//id,username,loginname,password,address,telephone,name,flag,isvalidate,headPortrait,age,introduction,registration_date,org_id，gender
			colNames:['ID','昵称','登录名','真实姓名','密码','性别','年龄','地址','联系电话','用户状态','真人验证状态','真人验证状态','真人验证照片','头像','个人介绍','注册时间','用户类型','真人验证时间'],
			colModel:[
					{name:'ID',index:'ID', width:60, key:true, sorttype:"int",hidden:true},								
					{name:'username',index:'username', width:80,align: 'center'}, 
					{name:'loginname',index:'loginname', width:80,align: 'center'}, 
					{name:'name',index:'name', width:80,align: 'center'}, 
					{name:'password',index:'password', width:80,align: 'center',hidden:true}, 
					{name:'gender',index:'gender', width:80,align: 'center'}, 
					{name:'age',index:'age', width:80,align: 'center'}, 
					{name:'address',index:'address', width:80,align: 'center'}, 
					{name:'telephone',index:'telephone', width:80,align: 'center'}, 
					{name:'flag',index:'flag', width:80,align: 'center',
						formatter: function(cellvalue, options, rowObject) {
							if(rowObject.flag=='未激活'){
					  			return "<p style=\"color: #FFC125;font-size: 16px;\">未激活</p>";
							}else if(rowObject.flag=='正常'){
					  			return "<p style=\"color: green;font-size: 16px;\">正常</p>" ;
							}else if(rowObject.flag=='冻结'){
					  			return "<p style=\"color: red;font-size: 16px;\">冻结</p>" ;
							}
		  				}}, 
	  				{name:'isvalidate',index:'isvalidate', width:80,align: 'center',hidden:true}, 
		  			{name:'formatterisvalidate',index:'formatterisvalidate', width:80,align: 'center',
						formatter: function(cellvalue, options, rowObject) {
							if(rowObject.isvalidate=='未验证'){
					  			return "<p style=\"color: #FFC125;font-size: 16px;\">未验证</p>";
							}else if(rowObject.isvalidate=='验证成功'){
					  			return "<p style=\"color: green;font-size: 16px;\">验证成功</p>" ;
							}else if(rowObject.isvalidate=='验证失败'){
					  			return "<p style=\"color: red;font-size: 16px;\">验证失败</p>" ;
							}else if(rowObject.isvalidate=='审核中'){
					  			return "<p style=\"color: pink;font-size: 16px;\">审核中</p>" ;
							}
		  				}},
					{name:'validateUrl',index:'validateUrl', width:80,align: 'center',
							formatter: function(cellvalue, options, rowObject) {
								if(rowObject.validateUrl!=null&&""!=rowObject.validateUrl){
						  			return "<img src='"+rowObject.validateUrl+"' height='32px' width='32px'>" ;
								}else{
									return "";
								}
			  				}}, 
					{name:'headPortrait',index:'headPortrait', width:80,align: 'center',
							formatter: function(cellvalue, options, rowObject) {
					  			return "<img src='"+rowObject.headPortrait+"' height='32px' width='32px'>" ;
			  				}}, 
					{name:'introduction',index:'introduction', width:80,align: 'center'}, 
					{name:'registration_date',index:'registration_date', width:120,align: 'center',formatter:"date",formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}},
				  	{name:'org_id',index:'org_id', width:50,align: 'center'}, 
					{name:'validateDate',index:'validateDate', width:120,align: 'center',formatter:"date",formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}}
			],
			shrinkToFit:false,
			sortname:'id',
			sortorder:'desc',
			viewrecords:true,
			multiselect: true, // 是否显示复选框
			multiboxonly : true, 
			//gridview: true,  //提升速度
			rownumbers: false,//显示行号
			rownumWidth: 30, //行号的宽度
			rowNum:10,
			rowList:[10,20,50,100,200,500],
			toolbar: [false,"top"],
			altRows:true,//隔行变色
			altclass:'altclass',//隔行变色样式
			ondblClickRow:function(id){
				var actionUrl = "<%=request.getContextPath()%>/photo_queryVerificationPhotoByUserId.action?userId="+id;  
				$.ajax({  
					  url : actionUrl,  
				      type : "post", 
				      dataType : "json",  
				      cache : false,  
				      error : function(textStatus, errorThrown) {  
				          alert("系统ajax交互错误: " + textStatus.value);  
				      },  
				      success : function(data, textStatus) {
				    	 var result =data.list;
			    		 var div1=document.getElementById("div1");
				    	 document.getElementById("div1").innerHTML="";
				    	 for(var i=0;i<result.length;i++){
				    		 var img=document.createElement("img");
				    		 img.src=result[i];
				    		 img.style.width='400px';
				    		 div1.appendChild(img);
					    	 div1.style.display="block";
				    	 }
				      }  
				});
			},
			jsonReader: {
				root:"rows",		// 数据行（默认为：rows）
				page: "page",  	// 当前页
				total: "total",  // 总页数
				records: "records",  // 总记录数
				repeatitems : false		// 设置成false，在后台设置值的时候，可以乱序。且并非每个值都得设
			},
			prmNames:{rows:"rows",page:"page",sort:"sidx",order:"sord",search:"search"},
			pager:"#gridPager",
			caption: "数据列表"
	});
	 jQuery("#gridTable").jqGrid('navGrid','#gridPager',{add:false,edit:false,del:false,search:false,refresh:false});
		jQuery("#gridTable").jqGrid('navButtonAdd','#gridPager',
					{ 	
					caption: "列状态",                          
					title: "Reorder Columns",                           
					onClickButton : function (){                               
					jQuery("#gridTable").jqGrid('columnChooser');                           
					}
		}); 
	}); 
	
	//delete
	function deleteData(){
		alert("暂不支持！");
		return false;
		var ids = $("#gridTable").jqGrid("getGridParam", "selarrrow") + "";
	    if (!ids) {
	    	alert("请先选择记录!");  
			return false;  
		} 
		if(!confirm("是否确认删除 ？")){
			return false;
		}
		var params = {"ids": ids};  
		var actionUrl = "<%=request.getContextPath()%>/photo_deletePhotoByIds.action";  
		$.ajax({  
			  url : actionUrl,  
		      type : "post", 
		      data : params,  
		      dataType : "json",  
		      cache : false,  
		      error : function(textStatus, errorThrown) {  
		          alert("系统ajax交互错误: " + textStatus.value);  
		      },  
		      success : function(data, textStatus) {
		      	if(data.result=='success'){
		      		alert("删除成功！");       
		      		refreshIt();    
		      	}else{
		      		alert("删除失败！");
		      	}
		      }
		});
	}
    //查询
	function gridSearch(){
		var starttime = jQuery("#starttime").val();
		var endtime = jQuery("#endtime").val();
		var loginname = jQuery("#loginname").val();
		var flag = jQuery("#flag").val();
		var orgId = jQuery("#orgId").val();
		var isvalidate = jQuery("#isvalidate").val();
		var params = {  
            "starttime" : $.trim(starttime),
            "endtime" : $.trim(endtime),
            "loginname" : encodeURIComponent($.trim(loginname)),
            "orgId" : encodeURIComponent($.trim(orgId)),
            "isvalidate" : encodeURIComponent($.trim(isvalidate)),
            "flag" : encodeURIComponent($.trim(flag))
		};							 
		 var postData = $("#gridTable").jqGrid("getGridParam", "postData");
		 $.extend(postData, params);
		jQuery("#gridTable").jqGrid('setGridParam',
		{
			url:'<%=request.getContextPath()%>/user_queryUsers.action'
		}).trigger("reloadGrid", [{page:1}]); 
    } 
	//刷新
	function refreshIt(){
		gridSearch();
	}
	//清空
	function reset() {
		jQuery("#starttime").val("");
		jQuery("#endtime").val("");
		jQuery("#loginname").val("");
		jQuery("#flag").val("");
		jQuery("#orgId").val("");
		jQuery("#isvalidate").val("");
	}
	
	//审核用户真实性
	function auditing(){
		var ids = $("#gridTable").jqGrid("getGridParam", "selarrrow");
	    if (!ids) {
	    	alert("请先选择记录!");  
			return false;  
		}
	    var row = jQuery("#gridTable").jqGrid('getRowData',ids[0]);//获取选中行.
	    var isvalidate=row.isvalidate;
	    for(var i=1;i<ids.length;i++){
	    	var row1 = jQuery("#gridTable").jqGrid('getRowData',ids[i]);//获取选中行.
	    	if(isvalidate!=row1.isvalidate){
	    		alert("只能处理待审核状态用户");
	    		return false;
	    	}
	    }
		var width = screen.width/2.5;
		var height = screen.height/2.5;
		var ua = navigator.userAgent.toLowerCase();
        if(ua.match(/chrome\/([\d.]+)/)){
        	//window.open(','newwindow', 'width:'+width+',status:no,height:'+height);
        	window.open ("<%=request.getContextPath()%>/data/auditingPhotos.jsp?type=1&ids="+ids+"&temp="+new Date(), "newwindow", "height="+height+", width="+width+",scrollbars=no");
			//refreshIt();
        } else{
	   		window.showModalDialog('<%=request.getContextPath()%>/data/auditingPhotos.jsp?type=1&ids='+ids+'&temp='+new Date(),'', 'dialogWidth:'+width+';status:no;dialogHeight:'+height+';');
	   		refreshIt();
        }
	}
	function hiddenDiv(){
		var div1=document.getElementById("div1");
		div1.style.display="none";
	}
	//查看审批记录
	function viewReviewRecords(){
		var ids= $("#gridTable").jqGrid("getGridParam", "selarrrow") + "";
		if (!ids) {
		    alert("请先选择记录!");  
		    return false;  
		}
		if(ids.indexOf(",")!=-1){
			  alert("只能选择一条记录!");  
		        return false; 
		}
		var row = jQuery("#gridTable").jqGrid('getRowData',ids);//获取选中行.
		var width = screen.width/2.9;
		var height = screen.height/2.2;
		var ua = navigator.userAgent.toLowerCase();
        if(ua.match(/chrome\/([\d.]+)/)){
        	window.open("<%=request.getContextPath()%>/data/viewReviewRecords.jsp?type=1&userId="+ids+"&temp="+new Date(),'', 'dialogWidth:'+width+';status:no;dialogHeight:'+height+';');
			refreshIt();
        } else{
	   		window.showModalDialog("<%=request.getContextPath()%>/data/viewReviewRecords.jsp?type=1&userId="+ids+"&temp="+new Date(),'', 'dialogWidth:'+width+';status:no;dialogHeight:'+height+';');
	   		refreshIt();
        }
	}
	
	function editNotifition(){
		var width = screen.width/2.9;
		var height = screen.height/2.2;
		var ua = navigator.userAgent.toLowerCase();
        if(ua.match(/chrome\/([\d.]+)/)){
        	window.open("<%=request.getContextPath()%>/data/editNotifition.jsp?temp="+new Date(),'', 'dialogWidth:'+width+';status:no;dialogHeight:'+height+';');
			refreshIt();
        } else{
	   		window.showModalDialog("<%=request.getContextPath()%>/data/editNotifition.jsp?temp="+new Date(),'', 'dialogWidth:'+width+';status:no;dialogHeight:'+height+';');
	   		refreshIt();
        }
	}
</script>
</head>
<body>
	<form action="" method="post"">
		<table width="100%" border="0" cellpadding="6" cellspacing="0"
			class="tabman" style="width:100%;margin-bottom:0px">
			<tr>
				<td>&nbsp;&nbsp;时间：<input type="text" id="starttime"
						name="starttime" value="" class="input"
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endtime\')}'})"
						readonly="readonly" style="width:120px;" />
				&nbsp;—&nbsp;<input type="text" id="endtime"
						name="endtime" value="" class="input"
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'starttime\')}'})"
						readonly="readonly" style="width:120px;" />
				</td>
				<td>&nbsp;&nbsp;用户名：<input type="text" id="loginname"
						name="loginname" value="" class="input" style="width:120px;" />
				</td>
				<td>分类：<select id="flag" name="flag" style="width:150px;">
								<option value="" selected="selected">--请选择--</option>
								<option value="0">未激活</option>
								<option value="1">正常</option>
								<option value="2">冻结</option>
						</select>
				</td>
				<td>状态：<select id="orgId" name="orgId" style="width:150px;">
						<option value="">全部</option>
						<option value="0">普通用户</option>
						<option value="1">管理员</option>
					</select>
				</td>
				<td>真人验证：<select id="isvalidate" name="isvalidate" style="width:150px;">
						<option value="">全部</option>
						<option value="0">未验证</option>
						<option value="3">审核中</option>
						<option value="1">验证成功</option>
						<option value="2">验证失败</option>
					</select>
				</td>
				<td><input type="button"
							class="button_b" value="查询" onclick="gridSearch()" />
							&nbsp;&nbsp;<input type="button"
							class="button_b" value="清空" onclick="reset()" />
				</td>
			</tr>
		</table>
		<div id="div1" style="display: none;width: 90%;height: 600px;position:absolute ;z-index: 9999;overflow: auto;border: 1px solid #000;" onclick="hiddenDiv()">
		</div>
		<table style="width: 100%;" class="tableCont">
			<tr>
				<td>
					<input id="add" type='button' value='审批真人验证' onclick="auditing();" class='button_b1'/>
<%--					<input id="refresh" type='button' value='查 看' onclick='preview()' class='button_b' />
					<input id="delete" type='button' value='删 除' onclick='deleteData();' class='button_b' />--%>
					<input id="refresh" type='button' value='刷 新' onclick='refreshIt()' class='button_b' />
					<input id="add" type='button' value='查看审批记录' onclick="viewReviewRecords();" class='button_b1'/>
					<input id="pushNotification" type='button' value='发送通知' onclick="editNotifition()" class='button_b1'/>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<table id="gridTable"></table>
					<div id="gridPager"></div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>