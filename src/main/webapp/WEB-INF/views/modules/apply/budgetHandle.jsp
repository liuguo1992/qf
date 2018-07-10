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
	<form:form id="inputForm"  action="${ctx}/apply/budget/doHandle" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${budgetRecord.id}"/>
		<input type="hidden" name="tId" value="${budgetRecord.tId}"/>
		<div class="control-group">
			<label class="control-label">申请人:</label>
			<div class="controls">
				${budgetRecord.user.name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">金额:</label>
			<div class="controls">
				${budgetRecord.budget.money}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">费用类型:</label>
			<div class="controls">
					<c:choose>
						<c:when test="${budgetRecord.budget.type eq 1}">通讯费</c:when>
						<c:when test="${budgetRecord.budget.type eq 2}">游戏分成</c:when>
						<c:when test="${budgetRecord.budget.type eq 3}">手续费</c:when>
						<c:when test="${budgetRecord.budget.type eq 4}">职工薪酬</c:when>
						<c:when test="${budgetRecord.budget.type eq 5}">住房公积金</c:when>
						<c:when test="${budgetRecord.budget.type eq 6}">保险费</c:when>
						<c:when test="${budgetRecord.budget.type eq 7}">福利费</c:when>
						<c:when test="${budgetRecord.budget.type eq 8}">交通费</c:when>
						<c:when test="${budgetRecord.budget.type eq 9}">招待费</c:when>
						<c:when test="${budgetRecord.budget.type eq 10}">办公费</c:when>
						<c:when test="${budgetRecord.budget.type eq 11}">招待费</c:when>
						<c:when test="${budgetRecord.budget.type eq 12}">技术服务费</c:when>
						<c:when test="${budgetRecord.budget.type eq 13}">市场推广费</c:when>
						<c:when test="${budgetRecord.budget.type eq 14}">租赁费</c:when>
						<c:when test="${budgetRecord.budget.type eq 15}">车辆费</c:when>
						<c:when test="${budgetRecord.budget.type eq 16}">差旅费</c:when>
						<c:when test="${budgetRecord.budget.type eq 17}">低值易耗品</c:when>
						<c:when test="${budgetRecord.budget.type eq 18}">补偿金</c:when>
						<c:when test="${budgetRecord.budget.type eq 19}">研发费</c:when>
						<c:when test="${budgetRecord.budget.type eq 20}">咨询费</c:when>
						<c:when test="${budgetRecord.budget.type eq 21}">审计验资费</c:when>
						<c:when test="${budgetRecord.budget.type eq 22}">会务费</c:when>
						<c:when test="${budgetRecord.budget.type eq 23}">网络服务费</c:when>
						<c:when test="${budgetRecord.budget.type eq 24}">车船税</c:when>
						<c:when test="${budgetRecord.budget.type eq 25}">罚款支出</c:when>
						<c:when test="${budgetRecord.budget.type eq 26}">其它</c:when>
					</c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">事由摘要:</label>
			<div class="controls">
				${chargeDetail.summary}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				${budgetRecord.budget.bNote}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申请时间:</label>
			<div class="controls">
				<fmt:formatDate value="${budgetRecord.budget.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申请结果:</label>
			<div class="controls">
				<input type="radio" name="result" value="2"/>驳回
				<input type="radio" name="result" value="1"/>同意
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审批备注:</label>
			<div class="controls">
				<textarea id="bNote" name="note" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"></textarea>
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