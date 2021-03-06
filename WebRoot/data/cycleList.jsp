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
			url:'<%=request.getContextPath()%>/cycle_queryCycles.action?edit_date='+dateStr+'&temp='+Math.round(Math.random()*10000),
			datatype: "json",
			height: 500,
			autoheight: true,
			width: widthScroll/1.5, //"ID", "cycle_no", "starttime", "signup_endtime", "endtime", "status", "theme_id"
			colNames:['ID','周期号','开始时间','报名结束时间','周期结束时间','状态','所属主题id','所属主题'],
			colModel:[
					{name:'ID',index:'ID', width:60, key:true, sorttype:"int",hidden:true},								
					{name:'cycle_no',index:'cycle_no', width:80,align: 'center'}, 
					{name:'starttime',index:'starttime', width:120,align: 'center',formatter:"date",formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}}, 
					{name:'signup_endtime',index:'signup_endtime', width:120,align: 'center',formatter:"date",formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}}, 
					{name:'endtime',index:'endtime', width:120,align: 'center',formatter:"date",formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}},
					{name:'status',index:'status', width:80,align: 'center'},
					{name:'theme_id',index:'theme_id', width:150,align: 'center',hidden:true},
					{name:'theme_name',index:'theme_name', width:150,align: 'center'},
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
		$(function(){
            $(window).resize(function(){   
         		//$("#gridTable").setGridHeight($(window).height());
        	});
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
		      }  
		});
		
	}); 
	
	//添加
	function addData(){
		var width = screen.width/2;
		var height = screen.height/2;
		var ua = navigator.userAgent.toLowerCase();
        if(ua.match(/chrome\/([\d.]+)/)){
        	window.open('<%=request.getContextPath()%>/data/editCycle.jsp?temp='+new Date(),'', 'dialogWidth:'+width+';status:no;dialogHeight:'+height+';');
			refreshIt();
        } else{
	   		window.showModalDialog('<%=request.getContextPath()%>/data/editCycle.jsp?temp='+new Date(),'', 'dialogWidth:'+width+';status:no;dialogHeight:'+height+';');
	   		refreshIt();
        }
	}
	
	//修改
	function updateData(flag){
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
		var id =row.ID;
		var width = screen.width/2;
		var height = screen.height/2;
		var ua = navigator.userAgent.toLowerCase();
        if(ua.match(/chrome\/([\d.]+)/)){
        	window.open('<%=request.getContextPath()%>/cycle_getCycleById.action?id='+id+'&flag='+flag+'&temp='+new Date(),'', 'dialogWidth:'+width+';status:no;dialogHeight:'+height+';');
    		refreshIt();
        } else{
	   		window.showModalDialog('<%=request.getContextPath()%>/cycle_getCycleById.action?id='+id+'&flag='+flag+'&temp='+new Date(),'', 'dialogWidth:'+width+';status:no;dialogHeight:'+height+';');
	   		refreshIt();
        }
	}
	
	//delete
	function deleteData(){
		var ids = $("#gridTable").jqGrid("getGridParam", "selarrrow") + "";
	    if (!ids) {
	    	alert("请先选择记录!");  
			return false;  
		} 
       
		if(!confirm("是否确认删除 ？")){
			return false;
		}
		var params = {"ids": ids};  
		var actionUrl = "<%=request.getContextPath()%>/cycle_deleteTheme.action";  
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
		var cycle_no = jQuery("#cycle_no").val();
		var starttime = jQuery("#starttime").val();
		var endtime = jQuery("#endtime").val();
		var theme_id = jQuery("#theme").val();
		var status = jQuery("#status").val();
		var params = {  
            "cycleNo" : encodeURIComponent($.trim(cycle_no)),
            "starttime" : encodeURIComponent($.trim(starttime)),
            "endtime" : encodeURIComponent($.trim(endtime)),
            "themeId" : encodeURIComponent($.trim(theme_id)),
            "status" : encodeURIComponent($.trim(status))
		};							 
		 var postData = $("#gridTable").jqGrid("getGridParam", "postData");
		 $.extend(postData, params);
		jQuery("#gridTable").jqGrid('setGridParam',
		{
			url:'<%=request.getContextPath()%>/cycle_queryCycles.action'
		}).trigger("reloadGrid", [{page:1}]); 
    } 
	//刷新
	function refreshIt(){
		gridSearch();
	}
	//清空
	function reset() {
		jQuery("#cycle_no").val("");
		jQuery("#starttime").val("");
		jQuery("#endtime").val("");
		jQuery("#theme").val("");
		jQuery("#status").val("");
	}
</script>
</head>
<body>
	<form action="" method="post"">
		<table width="100%" border="0" cellpadding="6" cellspacing="0"
			class="tabman" style="width:100%;margin-bottom:0px">
			<tr>
				<td>&nbsp;&nbsp;周期号：<input type="text" id="cycle_no"
					name="cycle_no" value="" class="input" style="width:120px;" />&nbsp;&nbsp;
				</td>
				<td>&nbsp;&nbsp;时间：<input type="text" id="starttime"
						name="starttime" value="" class="input"
						onClick="WdatePicker()"
						readonly="readonly" style="width:100px;" />
				—<input type="text" id="endtime"
						name="endtime" value="" class="input"
						onClick="WdatePicker()"
						readonly="readonly" style="width:100px;" />
				</td>
				<td>&nbsp;&nbsp;主题：
					<select id="theme" name="theme" style="width:80px;">
						<option value="" selected="selected">--请选择--</option>
					</select>
				</td>
				<td>&nbsp;&nbsp;状态：
					<select id="status" name="status" style="width:80px;">
						<option value="">全部</option>
						<option value="0">未开始</option>
						<option value="1">进行中</option>
						<option value="2">已结束</option>
					</select>
				</td>
				<td><input type="button"
							class="button_b" value="查询" onclick="gridSearch()" />
							&nbsp;&nbsp;<input type="button"
							class="button_b" value="清空" onclick="reset()" />
				</td>
			</tr>
		</table>
		<table style="width: 100%;" class="tableCont">
			<tr>
				<td>
					<input id="add" type='button' value='添 加' onclick="addData();" class='button_b' />
					<input id="update" type='button' value='修 改' onclick='updateData(0)' class='button_b' />
					<input id="refresh" type='button' value='查 看' onclick='updateData(1)' class='button_b' />
					<input id="delete" type='button' value='删 除' onclick='deleteData();' class='button_b' />
					<input id="delete" type='button' value='批量修改时间' onclick='updateTime();' class='button_b1'"/>
					<input id="delete" type='button' value='置顶' onclick='sticks();' class='button_b'"/>
					<input id="delete" type='button' value='取消置顶' onclick='cancelStick();' class='button_b1'/>
					<input id="refresh" type='button' value='刷 新' onclick='refreshIt()' class='button_b' />
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