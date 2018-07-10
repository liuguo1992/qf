<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
 
<html>
<head>
	<title>创建申请</title>
	<meta name="decorator" content="default"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
<script src="../../../static/js/dateDiff.js"></script> 

	
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/apply/leave/list">我的申请</a></li>
		<li class="active"><a href="${ctx}/apply/leave/_list">我的审批</a></li>
		<shiro:hasPermission name="apply:budget:edit"><li><a href="${ctx}/apply/leave/_add">创建申请</a></li></shiro:hasPermission>
	</ul><br/>
	<form:form id="inputForm"  action="${ctx}/apply/leave/save" method="post" class="form-horizontal">
	<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">申请类型:</label>
			<div class="controls">
				请假
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">请假类型:</label>
			<div class="controls">
				 <select style="width:120px;" id="type" class="select" name="type">
			        <option value="">请选择</option>     
			        <option value="1">事假</option>     
			        <option value="2">病假</option>
			        <option value="3">产假</option>
			        <option value="4">婚假</option>
			        <option value="5">调休假</option>
			        <option value="6">丧假</option>
			        <option value="7">年假</option>
			        <option value="8">其他</option>
			    </select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">请假事由:</label>
			<div class="controls">
				<input type="text" name="reason" value="" />
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		 <div class="control-group">
			 <label class="control-label">请假时间:</label>
			 <div class="controls">
				<input id="startDate"  name="startDate"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:163px;"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
					　--　
				
				<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:163px;"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
			</div> 
		</div>
		<div class="control-group" >
			<label class="control-label">天数:</label>
			<div class="controls"> 
				<input type="text"  id="days" name="days"  value =""  />
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
			 //验证请假类型
			var options=$("#type option:selected");
			if(options.val() == ""){
				alert("请选择请假类型！");
				return false;
			};
			validatorDate();
			/*  var result = validatorDate();
			//alert()
			if(!result){
				return false;
			} */
		
		});
		//计算天数s
		$("#endDate").blur(function(){
			validatorDate();
		})
		
		function validatorDate(){
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			if(startDate && endDate){
				var days = dateDiff(startDate,endDate);
				if(days < 0 ){
					alert("起始时间不能大于结束时间！");
					return false;
				}
				$("#days").val(days.toFixed(1));
			}
		} 
	</script>
</body>

</html>