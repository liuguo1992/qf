<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script src="/static/jquery/jquery-1.8.3.min.js"></script> 
<script src="/static/jquery-validation/1.11.1/jquery.validate.js"></script> 
<script src="/static/jquery-validation/1.11.1/jquery.validate.method.js"></script> 
<html>
<head>
	<title>审批办理</title>
	<meta name="decorator" content="default"/>
	
</head>
<body>
	<form:form id="inputForm"  action="${ctx}/apply/loan/doContractHandle" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${budgetRecord.id}"/>
		<input type="hidden" name="tId" value="${budgetRecord.tId}"/>
		<div class="control-group">
			<label class="control-label">申请人:</label>
			<div class="controls">
				${loan.user.name}
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label">预算类型:</label>
			<div class="controls">
					<c:choose>
						<c:when test="${loan.type eq 1}">市场部费用</c:when>
						<c:when test="${loan.type eq 2}">工资成本</c:when>  
						<c:when test="${loan.type eq 3}">人力资源部费用 </c:when>
						<c:when test="${loan.type eq 4}">其他费用</c:when>
					</c:choose>
			</div>
		</div> 
		<div class="control-group">
			<label class="control-label">预算摘要:</label>
			<div class="controls">
				${loan.reason}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预算金额:</label>
			<div class="controls">
				${loan.money}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				${loan.note}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审批:</label>
			<div class="controls">
				<input type="radio" name="result" value="2"/>驳回
				<input type="radio" name="result" value="1"/>同意
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审批备注:</label>
			<div class="controls">
				<textarea id="note" name="note" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"></textarea>
				<span class="help-inline"><font color="red">如没有，可不填。</font> </span>
			</div>
		</div>
		 <div class="form-actions">
			<shiro:hasPermission name="apply:budget:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="提交"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div> 
		</form:form>
</body>
</html>