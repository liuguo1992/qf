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
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/apply/loan/list?type=3">我的申请</a></li>
		<li class="active"><a href="${ctx}/apply/loan/_list?type=3">我的审批</a></li>
		<shiro:hasPermission name="apply:budget:edit"><li><a href="${ctx}/apply/loan/_add?type=3">创建申请</a></li></shiro:hasPermission>
	</ul><br/>
	<form:form id="inputForm"  action="${ctx}/apply/loan/save" method="post" class="form-horizontal">
	<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">申请类型:</label>
			<div class="controls">
				借款申请
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
			<label class="control-label">借款事由:</label>
			<div class="controls">
				<textarea id="reason" name="reason" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge" required="required"></textarea>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		 <div class="control-group">
			 <label class="control-label">借款日期:</label>
			 	<div class="controls">
					<input id="startDate"  name="loanDate"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:163px;"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
				</div>
			</div> 
			 <div class="control-group">
			 <label class="control-label">还款日期:</label>
			 	<div class="controls">
					<input id="startDate"  name="repayDate"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:163px;"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
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
			<label class="control-label">备注:</label>
			<div class="controls">
				<textarea id="note" name="note" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"></textarea>
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
				alert("请选择借款类型！");
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