<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>借款审批</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/apply/loan/_list");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/apply/loan/list?type=3">我的申请</a></li>
		<li class="active"><a href="${ctx}/apply/loan/_list?type=3">我的审批</a></li>
		<shiro:hasPermission name="apply:budget:edit"><li><a href="${ctx}/apply/loan/_add?type=3">创建申请</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="budgetRecord" action="${ctx}/apply/loan/_list?type=3" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>登录名：</label>
				<input type="text" name="loginName" value=""/>
			</li>
			<li><label>姓&nbsp;&nbsp;&nbsp;名：</label>
				<input type="text" name="name" value=""/>
			</li>
			<li><label>时&nbsp;&nbsp;&nbsp;间：</label>
				<input id="startDate"  name="startDate"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:163px;"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
					　--　
				<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:163px;"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
			</li>
			<li><label>审批状态：</label>
				<select style="width:120px;" id="type" name="status">
			        <option value="">请选择</option>     
			        <option value="0">未审批</option>     
			        <option value="1">已审批</option>
			    </select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<sys:message content="${message}"/>
			<tr>
				<th style="text-align: center;" width="200px">申请编号</th>
				<th style="text-align: center;">申请人</th>
				<th style="text-align: center;">借款事由</th>
				<th style="text-align: center;">借款金额</th>
				<th style="text-align: center;">借款类型</th>
				<th style="text-align: center;">借款日期</th>
				<th style="text-align: center;">还款日期</th>
				<th style="text-align: center;">申请日期</th>
			 	<th style="text-align: center;">流程状态</th> 
				<shiro:hasPermission name="apply:budget:edit"><th style="text-align: center;">操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="loan" varStatus="index">
			<tr>
				<td style="text-align: center;">${(page.pageNo - 1) * page.pageSize + index.count}</td>
				<td style="text-align: center;">${loan.user.name}</td>
				<td style="text-align: center;">${loan.reason}</td>
				<%-- <td style="text-align: center;">
					<fmt:formatDate value="${budgetRecord.loanApproval.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>-
					<fmt:formatDate value="${budgetRecord.loanApproval.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td style="text-align: center;">${budgetRecord.loanApproval.days}</td> --%>
				<td style="text-align: center;">
					${loan.money}
				</td>
				<td style="text-align: center;">
					${loan.type}
				</td>
				<td style="text-align: center;">
					<fmt:formatDate value="${loan.loanDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td style="text-align: center;">
					<fmt:formatDate value="${loan.repayDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td style="text-align: center;">
					<fmt:formatDate value="${loan.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td style="text-align: center;">
					<c:choose>
						<c:when test="${loan.approval.bStatus  eq 0}" >未完成</font></c:when>
						<c:when test="${loan.approval.bStatus  eq 1}">已完成</c:when>
					</c:choose>
				</td>
				<shiro:hasPermission name="apply:budget:edit"><td style="text-align: center;">
					<c:if test="${loan.budgetRecord.status == 0}"><a href="${ctx}/apply/loan/handle?id=${loan.budgetRecord.id}">办理</a></c:if>
					<c:if test="${loan.budgetRecord.status == 1}">
						<a href="${ctx}/apply/loan/delete?id=${loan.budgetRecord.id}" onclick="return confirmx('确认要删除该条记录吗？', this.href)">删除</a>
					</c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>