<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<html>
  <head>
    <title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>

		<script type="text/javascript">
		
	$(function (){
		//***********按钮**************
		$('#submitbtn').linkbutton({   
    		iconCls: 'icon-ok'  
		});  
		$('#closebtn').linkbutton({   
    		iconCls: 'icon-cancel'  
		});
		//**********表单校验*************
		$.formValidator.initConfig({
			formID : "sysusereditform",
			theme : "Default",
			onError : function(msg, obj, errorlist) {
				//alert(msg);
			}
		});
		//用户账号
		$("#sysuser_usercode").formValidator({
			onShow : "",
			onCorrect:"&nbsp;"
		}).inputValidator({
			min : 1,
			max : 20,
			onError : "请输入用户账号(最长10个字符)"
		});
		//用户名称
		$("#sysuser_username").formValidator({
			onShow : "",
			onCorrect:"&nbsp;"
		}).inputValidator({
			min : 1,
			max : 120,
			onError : "请输入用户名称(最长60个字符)"
		});
		//用户类型
		$("#sysuser_groupid").formValidator({
			onShow : "",
			onCorrect:"&nbsp;"
		}).inputValidator({
			min : 1,
			onError : "请选择用户类型"
		});
		//用户密码
		$("#sysuser_password").formValidator({
			onShow : "",
			onCorrect:"&nbsp;"
		}).inputValidator({
			min : 1,
			max : 10,
			onError : "请填写用户密码(最长10个字符)"
		});
		
	});
	function sysusersave(){
		if($.formValidator.pageIsValid()){//如果form表单js校验通过执行提交
			jquerySubByFId('sysusereditform',sysusersave_callback,null,"json");
		}

	}
	function sysusersave_callback(data){
		//输出提交结果信息，解析服务端返回josn数据即将原来map的key解析出来
		//alert(data.type +"   "+data.message);
		//使用统一方法解析服务端响应的结果
		var result = getCallbackData(data);//获取resultInfo（包括type、message）
		var type = result.type;
		_alert(result);
		/* if (TYPE_RESULT_SUCCESS == type) {
			parent.sysuserquery();
			parent.closemodalwindow();
		} 	 */
	}
	
	</script>
 </HEAD>
<BODY>
<form id="sysusereditform" name="sysusereditform" action="${baseurl}/sys/user/insertsubmit.action" method="post">
<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" bgColor=#c4d8ed>
		<TBODY>
			<TR>
				<TD background=images/r_0.gif width="100%">
					<TABLE cellSpacing=0 cellPadding=0 width="100%">
						<TBODY>
							<TR>
								<TD>&nbsp;系统用户信息</TD>
								<TD align=right>&nbsp;</TD>
							</TR>
						</TBODY>
					</TABLE>
				</TD>
			</TR>
			<TR>
				<TD>
					<TABLE class="toptable grid" border=1 cellSpacing=1 cellPadding=4
						align=center>
						<TBODY>
							
							<TR>
								<TD height=30 width="15%" align=right >用户账号：</TD>
								<TD class=category width="35%">
								<div>
								<input type="text" id="sysuser_usercode" name="sysUserCustom.usercode" value="${sysUserCustom.userid}"   />
								</div>
								<!-- 校验出错信息显示区域，id后边tip -->
								<div id="sysuser_usercodeTip"></div>
								</TD>
								<TD height=30 width="15%" align=right >用户名称：</TD>
								<TD class=category width="35%">
								<div>
								<input type="text" id="sysuser_username" name="sysUserCustom.username" value="${sysUserCustom.username}"   />
								</div>
								<div id="sysuser_usernameTip"></div>
								</TD>
							</TR>
							
							
							<TR>
								<TD height=30 width="15%" align=right >用户密码：</TD>
								<TD class=category width="35%">
								<div>
									<input type="password" id="sysuser_password" name="sysUserCustom.pwd" value="${sysUserCustom.pwd}"/>
								</div>
								<div id="sysuser_passwordTip"></div>
								</TD>
								<TD height=30 width="15%" align=right >用户类型：</TD>
								<TD class=category width="35%">
								<div>
								<select name="sysUserCustom.groupid" id="sysuser_groupid">
									<option value="">请选择</option>
									<c:forEach items="${userGroupList}" var="dictinfo">
									   <option value="${dictinfo.id}" <c:if test="${sysUserCustom.groupid== dictinfo.id}">selected</c:if>>${dictinfo.info}</option>
									</c:forEach>
									
								</select>
								</div>
								<div id="sysuser_groupidTip"></div>
								</TD>
								
								
							</TR>
							<TR>
							    <TD height=30 width="15%" align=right >用户单位名称：</TD>
								<TD class=category width="35%">
								<input type="text" name="sysUserCustom.sysmc" value="${sysUserCustom.sysmc}"/>
								</TD>
								<TD height=30 width="15%" align=right>用户状态：</TD>
								<TD class=category width="35%">
								<input type="radio" name="sysUserCustom.userstate" value="s0201" />正常
								<input type="radio" name="sysUserCustom.userstate" value="s0202" />暂停
								<c:forEach items="${userStateList}" var="dictinfo">
								   <input type="radio" name="sysUserCustom.userstate" value="${dictinfo.id}" <c:if test="${sysUserCustom.groupid== dictinfo.id}">checked</c:if>/>${dictinfo.info}
								</c:forEach>
								
								</TD>
								
							</TR>
							
							<tr>
							  <td colspan=4 align=center class=category>
								<a id="submitbtn" href="#" onclick="sysusersave()">提交</a>
								<a id="closebtn" href="#" onclick="parent.closemodalwindow()">关闭</a>
							  </td>
							</tr>
						
						</TBODY>
					</TABLE>
				</TD>
			</TR>
		</TBODY>
	</TABLE>
</form>

</BODY>
</HTML>

