<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
 
<html>
<head>
	<title>创建申请</title>
	<meta name="decorator" content="default"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
<script src="../../../static/js/bankCardNoCheck.js"></script> 
<script src="../../../static/json/type.json"></script>
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/apply/budget/list">我的申请</a></li>
		<li class="active"><a href="${ctx}/apply/budget/_list">我的审批</a></li>
		<shiro:hasPermission name="apply:budget:edit"><li><a href="${ctx}/apply/budget/_add">创建申请</a></li></shiro:hasPermission>
	</ul><br/>
	<form:form id="inputForm"  action="${ctx}/apply/budget/save" method="post" class="form-horizontal">
	<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">申请类型:</label>
			<div class="controls">
				费用预算申请
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">头像:</label>
			<div class="controls">
				<%-- <form:hidden id="nameImage" path="photo" htmlEscape="false" maxlength="255" class="input-xlarge"/> --%>
				<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">费用类型:</label>
			<div class="controls">
				 <select style="width:120px;" id="type" class="select" name="type" ">
			         <option value="" >请选择</option>
			    </select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">金额:</label>
			<div class="controls">
				<input type="text" id="money" name="money"  value ="" class="required" />
				<span class="help-inline"><font color="red">(单位：元，若有小数，请保留两位！)&nbsp;*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收款人/单位:</label>
			<div class="controls">
				<input type="text" id="rUnit" name="rUnit"  value ="" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">付款方式:</label>
			<div class="controls" id="radio">
			     <input type="radio" name="pMethod" value="0"/>支票
				<input type="radio" name="pMethod" value="1" checked="checked"/>转账
				<input type="radio" name="pMethod" value="2"/>现金
			</div>
		</div>
		<div class="control-group" id="bank">
			<label class="control-label">开户银行（支行）:</label>
			<div class="controls">
				<input type="text" id="aBank" name="aBank"  value =""  />
			</div>
		</div>
		<div class="control-group" id="accout">
			<label class="control-label">银行账号:</label>
			<div class="controls">
				<input type="text" id="bAccout" name="bAccout"  value ="" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附注:</label>
			<div class="controls">
				<!--  <select style="width:120px;" id="notes" class="select" name="notes" required="true">
			        <option value="">请选择</option>     
			        <option value="0">预算内支出</option>     
			        <option value="1">预算外支出</option>
			    </select> -->
			    <input type="radio" name="notes" value="0" checked="checked"/>预算内支出
				<input type="radio" name="notes" value="1"/>预算外支出
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">事由摘要:</label>
			<div class="controls">
				<textarea id="bNote" name="summary" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge" required="required"></textarea>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<textarea id="bNote" name="bNote" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"></textarea>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="apply:budget:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="提交" />&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>

	<script type="text/javascript">
		$(document).on('click','#btnSubmit',function(){
			//验证费用类型
			var options=$("#type option:selected");
			if(options.val() == ""){
				alert("请选择费用类型！");
				return false;
			};
	
			//验证金额
          	var money = document.getElementById("money").value;
			var parnt = /(^([1-9][0-9]*)|^0)(($)|(.[0-9]{1,2})$)/;
			if(!parnt.test(money)){
				alert("金额请输入整数或者保留两位小数！");
				return false;
			};
	      
	        //验证银行卡号
	          var bAccout = $("#bAccout").val()
 			  var result = luhmCheck(bAccout);
 			  if(!result){
 			  return false;
 			  }
		});
		
		
		$(function(){
			$("input[name='pMethod']").click(function(){
				var pMethod = $(this).val();
				if(pMethod == 2){
					$("#bank").attr("style","display:none;");
					$("#accout").attr("style","display:none;");
				}else{
					$("#bank").attr("style","display::block;");
					$("#accout").attr("style","display::block;");
				}
			})
		
		})
		
		 $(document).ready(function(){  
        // 初始化内容
        $.getJSON("../../../static/json/type.json","",function(data){
        		var type = "";
			  $.each(data, function(i, item) {
			  	type = "<option value=" + i + "> "+ item + "</option>";
            	$("#type").append(type);
      		 }) 
			});
    	}); 
	</script>
</body>

</html>