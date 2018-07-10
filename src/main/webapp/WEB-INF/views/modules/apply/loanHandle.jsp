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
	<form:form id="inputForm"  action="${ctx}/apply/loan/doHandle" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${budgetRecord.id}"/>
		<input type="hidden" name="tId" value="${budgetRecord.tId}"/>
		<div class="control-group">
			<label class="control-label">申请人:</label>
			<div class="controls">
				${loan.user.name}
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">请假类型:</label>
			<div class="controls">
					<c:choose>
						<c:when test="${budgetRecord.leaveApproval.type eq 1}">事假</c:when>
						<c:when test="${budgetRecord.leaveApproval.type eq 2}">病假</c:when>
						<c:when test="${budgetRecord.leaveApproval.type eq 3}">产假</c:when>
						<c:when test="${budgetRecord.leaveApproval.type eq 4}">婚假</c:when>
						<c:when test="${budgetRecord.leaveApproval.type eq 5}">调休假</c:when>
						<c:when test="${budgetRecord.leaveApproval.type eq 6}">丧假</c:when>
						<c:when test="${budgetRecord.leaveApproval.type eq 7}">年假</c:when>
						<c:when test="${budgetRecord.leaveApproval.type eq 8}">其他</c:when>
					</c:choose>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">借款事由:</label>
			<div class="controls">
				${loan.reason}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借款时间:</label>
			<div class="controls">
				<fmt:formatDate value="${loan.loanDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款时间:</label>
			<div class="controls">
				<fmt:formatDate value="${loan.repayDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
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