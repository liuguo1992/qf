<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>借款审批</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/apply/loan/list?type=3">我的申请</a></li>
		<li class="active"><a href="${ctx}/apply/loan/_list?type=3">我的审批</a></li>
		<shiro:hasPermission name="apply:budget:edit"><li><a href="${ctx}/apply/loan/_add?type=3">创建申请</a></li></shiro:hasPermission>
	</ul>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<sys:message content="${message}"/>
			<tr>
				<!-- <th style="text-align: center;" width="200px">申请编号</th> -->
				<th style="text-align: center;">申请时间</th>
				<th style="text-align: center;">借款事由</th>
				<th style="text-align: center;">借款金额</th>
				<th style="text-align: center;">借款日期</th>
				<th style="text-align: center;">还款日期</th>
				<th style="text-align: center;">是否还款</th>
				<th style="text-align: center;">流程状态</th>
			 	<th style="text-align: center;">下一级审批人</th> 
				<th style="text-align: center;">审批状态</th>
				<th style="text-align: center;">审批结果</th>
				<shiro:hasPermission name="apply:budget:edit"><th style="text-align: center;">操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="loan" varStatus="index">
			<tr>
				<td style="text-align: center;"><fmt:formatDate value="${loan.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td style="text-align: center;">${loan.reason}</td>
				<td style="text-align: center;">${loan.money}</td>
				<td style="text-align: center;"><fmt:formatDate value="${loan.loanDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td style="text-align: center;"><fmt:formatDate value="${loan.repayDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td style="text-align: center;">
					<c:choose>
						<c:when test="${loan.isRepay  eq 0}" >否</font></c:when>
						<c:when test="${loan.isRepay  eq 1}">是</c:when>
					</c:choose>
				</td>
				<td style="text-align: center;">
					<c:choose>
						<c:when test="${loan.approval.bStatus  eq 0}" >未完成</font></c:when>
						<c:when test="${loan.approval.bStatus  eq 1}">已完成</c:when>
					</c:choose>
				</td>
				<td style="text-align: center;">${loan.user.name}</td>
				<td style="text-align: center;">
					<c:choose>
						<c:when test="${loan.approval.pStatus  eq 0}" >未审批</font></c:when>
						<c:when test="${loan.approval.pStatus eq 1}">已审批</c:when>
					</c:choose>
				</td>
					<td style="text-align: center;">
					<c:choose>
						<c:when test="${loan.approval.result  eq 0}" ></font></c:when>
						<c:when test="${loan.approval.result eq 1}" >通过</font></c:when>
						<c:when test="${loan.approval.result eq 2}">驳回</c:when>
					</c:choose>
				</td>
				 <%-- <td style="text-align: center;">${budget.budgetRecord.note}</td> --%>
				<shiro:hasPermission name="apply:budget:edit"><td style="text-align: center;">
					<c:if test="${loan.approval.bStatus == 1}"><a href="${ctx}/apply/loan/printForm?id=${loan.id}">打印</a></c:if>
					<%-- <c:if test="${approval.isDel == 0}"><a href="${ctx}/apply/budget/delete?bId=${budget.bId}" onclick="return confirmx('确认要删除该條記錄吗？', this.href)">删除</a></c:if> --%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>