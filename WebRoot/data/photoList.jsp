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
<title>数据管理</title>
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
			url:'<%=request.getContextPath()%>/photo_queryPhotos.action?edit_date='+dateStr+'&temp='+Math.round(Math.random()*10000),
			datatype: "json",
			//treeGrid: true,
            //treeGridModel: 'adjacency',//treeGrid模式，跟json元数据有关  
            //ExpandColumn : 'type',
			height: 500,
			autoheight: true,
			width: widthScroll/1.5, 
			////p.id,p.uploadDate,p.descs,p.type,p.status,p.praise,p.tread,p.auditingDate,p.userid,p.report,p.view,u.loginname,tc.theme_title 
			colNames:['ID','分类','上传时间','审核时间','审核状态','赞','踩','浏览量','举报','用户id','上传用户','所属主题'],
			colModel:[
					{name:'ID',index:'ID', width:60, key:true, sorttype:"int",hidden:true},								
					{name:'type',index:'type', width:80,align: 'center'}, 
					{name:'uploadDate',index:'uploadDate', width:120,align: 'center',formatter:"date",formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}},
					{name:'auditingDate',index:'auditingDate', width:120,align: 'center',formatter:"date",formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}},
					{name:'status',index:'status', width:80,align: 'center',
						formatter: function(cellvalue, options, rowObject) {
							if(rowObject.status=='审核通过'){
					  			return "<p style=\"color: #FFC125;font-size: 16px;\">审核通过</p>";
							}else if(rowObject.status=='未审核'){
					  			return "<p style=\"color: green;font-size: 16px;\">未审核</p>" ;
							}else if(rowObject.status=='未通过'){
					  			return "<p style=\"color: red;font-size: 16px;\">未通过</p>" ;
							}
		  				}},
				  	{name:'praise',index:'praise', width:50,align: 'center'},
			  		{name:'tread',index:'tread', width:50,align: 'center'},
			  		{name:'view',index:'view', width:50,align: 'center'},
			  		{name:'report',index:'report', width:50,align: 'center'},
	  				{name:'userid',index:'userid', width:150,align: 'center',hidden:true},
	  				{name:'userName',index:'userName', width:80,align: 'center',hidden:false},
	  				{name:'theme_title',index:'theme_title', width:80,align: 'center',hidden:false}
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
				var actionUrl = "<%=request.getContextPath()%>/photo_queryPhotoByPictureSetId.action?pictureSetId="+id;  
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
		
		var actionUrl = "<%=request.getContextPath()%>/cycle_selectInit.action?tableName=theme_cycle&columns=id,theme_title";  
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
		
		$(function(){
            $(window).resize(function(){   
         //$("#gridTable").setGridHeight($(window).height());
        });
       }); 
		//jQuery("#gridTable").hideCol("username").trigger("reloadGrid");
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
		var type = jQuery("#type").val();
		var status = jQuery("#status").val();
		var themeId = jQuery("#themeId").val();
		var themeCycleId = jQuery("#themeCycleId").val();
		var params = {  
            "starttime" : $.trim(starttime),
            "endtime" : $.trim(endtime),
            "type" : encodeURIComponent($.trim(type)),
            "status" : encodeURIComponent($.trim(status)),
            "themeId" : encodeURIComponent($.trim(themeId)),
            "cycleId" : encodeURIComponent($.trim(themeCycleId))
		};							 
		 var postData = $("#gridTable").jqGrid("getGridParam", "postData");
		 $.extend(postData, params);
		jQuery("#gridTable").jqGrid('setGridParam',
		{
			url:'<%=request.getContextPath()%>/photo_queryPhotos.action'
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
		jQuery("#type").val("");
		jQuery("#status").val("");
		jQuery("#themeId").val("");
		jQuery("#themeCycleId").val("");
	}
	
	//审核
	function auditing(){
		var ids = $("#gridTable").jqGrid("getGridParam", "selarrrow") + "";
	    if (!ids) {
	    	alert("请先选择记录!");  
			return false;  
		}
		var width = screen.width/2.5;
		var height = screen.height/2.5;
		var ua = navigator.userAgent.toLowerCase();
        if(ua.match(/chrome\/([\d.]+)/)){
        	//window.open(','newwindow', 'width:'+width+',status:no,height:'+height);
        	window.open ("<%=request.getContextPath()%>/data/auditingPhotos.jsp?type=0&ids="+ids+"&temp="+new Date(), "newwindow", "height="+height+", width="+width+",scrollbars=no");
			//refreshIt();
        } else{
	   		window.showModalDialog('<%=request.getContextPath()%>/data/auditingPhotos.jsp?type=0&ids='+ids+'&temp='+new Date(),'', 'dialogWidth:'+width+';status:no;dialogHeight:'+height+';');
	   		refreshIt();
        }
	}
	
	//查看
	function preview(){
		var ids= $("#gridTable").jqGrid("getGridParam", "selarrrow") + "";
		if (!ids) {
		    alert("请先选择记录!");  
		    return false;  
		}
		if(ids.indexOf(",")!=-1){
			  alert("只能选择一条记录!");  
		        return false; 
		}
		alert("请确保所选数据状态是否一致，避免误操作！");
		var row = jQuery("#gridTable").jqGrid('getRowData',ids);//获取选中行.
		var photoUrl = row.photoUrl;//获取选中行的id属性
		var width = screen.width/2;
		var height = screen.height/2;
		var ua = navigator.userAgent.toLowerCase();
        if(ua.match(/chrome\/([\d.]+)/)){
        	window.open(photoUrl,'', 'dialogWidth:'+width+';status:no;dialogHeight:'+height+';');
			refreshIt();
        } else{
	   		window.showModalDialog(photoUrl,'', 'dialogWidth:'+width+';status:no;dialogHeight:'+height+';');
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
        	window.open("<%=request.getContextPath()%>/data/viewReviewRecords.jsp?type=0&pictureSetId="+ids+"&temp="+new Date(),'', 'dialogWidth:'+width+';status:no;dialogHeight:'+height+';');
			refreshIt();
        } else{
	   		window.showModalDialog("<%=request.getContextPath()%>/data/viewReviewRecords.jsp?type=0&pictureSetId="+ids+"&temp="+new Date(),'', 'dialogWidth:'+width+';status:no;dialogHeight:'+height+';');
	   		refreshIt();
        }
	}
	//加载主题
	<%--
	function initThemes(obj){
		if(obj.value==1){
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
		    	  var theme = $("#themeId");
		    	  for(var i=0;i<result.length;i++){
		    		  theme.append('<option value="'+result[i][0]+'">'+result[i][1]+'</option>');
		    	  }
		      }  
			});
		}else if(obj.value==0){
			jQuery("#themeId").empty();
			jQuery("#themeId").append('<option value="">--请选择--</option>');
			jQuery("#cycleId").empty();
			jQuery("#cycleId").append('<option value="">--请选择--</option>');
		}
	}--%>
	//加载周期
	function initCycles(){
		jQuery("#themeCycleId").empty();
		jQuery("#themeCycleId").append('<option value="">--请选择--</option>');
		var actionUrl = "<%=request.getContextPath()%>/cycle_selectInit.action?tableName=cycle&columns=id,cycle_no&whereCol=theme_id";  
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
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endtime\')}'})"
						readonly="readonly" style="width:120px;" />
				&nbsp;—&nbsp;<input type="text" id="endtime"
						name="endtime" value="" class="input"
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'starttime\')}'})"
						readonly="readonly" style="width:120px;" />
				</td>
				<td>分类：<select id="type" name="type" style="width:80px;" onchange="initThemes(this)">
								<option value="" selected="selected">--请选择--</option>
								<option value="0">个人</option>
								<option value="1">秀场</option>
						</select>
				</td>
				<%--<td>主题：<select id="themeId" name="themeId" style="width:100px;" onchange="initCycles(this)">
							<option value="">--请选择--</option>
						</select>
				</td> --%>
				<td>选秀主题：<select id="themeCycleId" name="themeCycleId" style="width:100px;">
								<option value="">--请选择--</option>
						</select>
				</td>
				<td>状态：<select id="status" name="status" style="width:100px;">
						<option value="">全部</option>
						<option value="0">未审核</option>
						<option value="1">审核通过</option>
						<option value="2">未通过</option>
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
					<%--<input id="add" type='button' value='审批' onclick="auditing();" class='button_b'/>
					<input id="refresh" type='button' value='查 看' onclick='preview()' class='button_b' />--%>
<%--					<input id="delete" type='button' value='删 除' onclick='deleteData();' class='button_b' />
					<input id="refresh" type='button' value='刷 新' onclick='refreshIt()' class='button_b' />
					<input id="add" type='button' value='查看审批记录' onclick="viewReviewRecords();" class='button_b1'/>--%>
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