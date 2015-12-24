<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html>
<head>
<title>系统用户信息查询</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>

<script type="text/javascript">

//用户添加方法
function sysuseradd(){
	//打开一个模态窗口（当前只有模态窗口可以操作）
	var sendUrl = "${baseurl}/sys/user/insert.action";
	//调用createmodalwindow封装js方法，createmodalwindow底层构建一个iframe（指定一个url，相当于浏览器）
	createmodalwindow("添加医院信息", 800, 250, sendUrl);
}
//删除用户
function sysuserdel(id){
	_confirm('您确定要执行删除操作吗?', null, function() {
		//将id赋值给sysuserdelForm表单中sysuserdelid
		$("#sysuserdelid").val(id);
		//执行ajax的form提交
		//在sysuserdelForm中将删除用户的id传到action
		jquerySubByFId('sysuserdelForm',sysuserdel_callback,null,"json");
	});
	
}
//删除用户回调方法
function sysuserdel_callback(data){
	
	var result = getCallbackData(data);//获取resultInfo（包括type、message）
	var type = result.type;
	_alert(result);
	//如果type为成功要刷新 父窗口
	if(type==TYPE_RESULT_SUCCESS){
		//调用父窗口的datagrid查询
		sysuserquery();
	}
}
//工具栏
var toolbar = [ {
	id : 'sysuseradd',
	text : '添加',
	iconCls : 'icon-add',
	handler : sysuseradd
} ];

//datagrid列
var columns = [ [ /* {
		field : 'id',
		title : '',
		checkbox : true
	}, */ {
		field : 'usercode',
		title : '账号',
		width : 180
	}, {
		field : 'username',
		title : '名称',
		width : 130
	}, {
		field : 'groupname',
		title : '用户类型',
		width : 100,
		//使用formatter方法对象单元格数据进行重定义，如果不使用formatter，根据field定义的列名从json数据中找key
		//value是单元格的值，row是行的值是一个json数据
		formatter:function(value,row,index){
			//row就是行一行记录，dictinfoByGroupid是一个关联对象，info是关联对象中的属性
			return row.dictinfoByGroupid.info;
		}
	}, {
		field : 'sysmc',
		title : '所属单位',
		width : 200,
		formatter:function(value,row,index){
			if(row.useryy){
				return row.useryy.mc;
			}else if(row.userjd){
				return row.userjd.mc;
			}else if(row.usergys){
				return row.usergys.mc;
			}
		}
	}, {
		field : 'userstate',
		title : '状态',
		width : 100,
		formatter : function(value, row, index) {
			return row.dictinfoByUserstate.info;
		}
	}, {
		field : 'opt1',
		title : '修改',
		width : 60,
		formatter : function(value, row, index) {
			<shiro:hasPermission name="user:update">
			return '<a href=javascript:sysuseredit(\'' + row.id + '\')>修改</a>';
			</shiro:hasPermission>

		}
	}, {
		field : 'opt2',
		title : '删除',
		width : 60,
		formatter : function(value, row, index) {
			return '<a href=javascript:sysuserdel(\'' + row.id + '\')>删除</a>';
		}
	} ] ];

//调用datagrid的加载方法加载datagird
function initGrid() {
		$('#sysuserlist').datagrid({
			title : '系统用户列表',
			//nowrap : false,
			striped : true,
			//collapsible : true,
			url : '${baseurl}/sys/user/sysuserlist_result.action',
			//sortName : 'code',
			//sortOrder : 'desc',
			//remoteSort : false,
			idField : 'id',
			//frozenColumns : frozenColumns,
			columns : columns,
			pagination : true,
			rownumbers : true,
			toolbar : toolbar,
			loadMsg : "",
			pageList : [ 15, 30, 50, 100 ],
			onClickRow : function(index, field, value) {
				$('#sysuserlist').datagrid('unselectRow', index);
			}
		});

	}
	
	//预加载，进入页面就执行
	$(function() {
		initGrid();
		
	});

	//输入查询条件查询列表
	function sysuserquery() {
		//将form中的input格式为json
		var formdata = $("#sysuserqueryForm").serializeJson();
		//alert(formdata);
		$('#sysuserlist').datagrid('unselectAll');
		//load方法加载datagrid，formdata是查询条件，它是json格式，请求到action
		$('#sysuserlist').datagrid('load', formdata);
	}

</script>

</HEAD>
<!-- body的onload方法页面的dom元素全部加载完成执行onload方法  -->
<BODY >
	<div id="sysuserquery_div">
		<!-- 查询条件区域 -->
		<form id="sysuserqueryForm" name="sysuserqueryForm" method="post" action="${baseurl}/sys/user/list_result.action">
			<TABLE class="table_search">
				<TBODY>
				
					<TR>
						<TD class="left">用户账号：${sysUserCustom.usercode }</td>
						<td><INPUT type="text" name="sysUserCustom.usercode" /></TD>
						<TD class="left">用户名称：</TD>
						<td><INPUT type="text" name="sysUserCustom.username" /></TD>

						<TD class="left">用户类型：</TD>
						<td>
							<select name="sysUserCustom.groupid">
								<option value="">请选择</option>
								<c:forEach items="${userGroupList}" var="dictinfo">
								  <option value="${dictinfo.id}">${dictinfo.info}</option>
								</c:forEach>
								
							</select>
						</TD>
						<td >
						<shiro:hasPermission name="user:list">
						<a id="btn" href="#" onclick="sysuserquery()"
							class="easyui-linkbutton" iconCls='icon-search'>查询</a>
						</shiro:hasPermission>
							</td>
					</TR>


				</TBODY>
			</TABLE>
		</form>
		<!-- 查询条件区域 -->
		<!-- datagrid区域 -->
		<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
			<TBODY>
				<TR>
					<TD>
						<table id="sysuserlist"></table>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<!-- datagrid区域 -->
	</div>

	<div id="sysuseredit_div"></div>
	<form id="sysuserdelForm" name="sysuserdelForm"
		action="${baseurl}/sys/user/delete.action" method="post">
		
		<input type="hidden" id="sysuserdelid" name="sysUserCustom.id" />
	</form>

</BODY>
</HTML>