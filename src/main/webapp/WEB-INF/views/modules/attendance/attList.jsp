<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>我的考勤</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户考勤数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/attendance/attLog/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/attendance/attLog/list");
			$("#searchForm").submit();
	    	return false;
	    	 
	    	
	    }
	    
	    onload = function ()
    {
         var year=new Date().getFullYear(); //获取当前年份
         
       var sel = document.getElementById ('sel');//获取select下拉列表
       for ( var i = 2006; i < year+3; i++)//循环添加2006到当前年份加3年的每个年份依次添加到下拉列表
       {
           var option = document.createElement ('option');
           option.value = i;
           var txt = document.createTextNode (i);
           option.appendChild (txt);
           sel.appendChild (option);
       }
    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/attendance/attLog/list">我的考勤</a></li>
		<%-- <shiro:hasPermission name="attendance:attLog:view"><li><a href="${ctx}/attendance/attLog/_list">用户考勤</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="budgetRecord" action="${ctx}/attendance/attLog/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>年份：</label>
				<select id="sel" style="width:120px;" name="year">
					<option value="">请选择</option>  	
				</select>
			</li>
			<li><label>月份：</label>
				<select style="width:120px;" id="type" name="month">
			        <option value="">请选择</option>     
			        <option value="1">一月</option>     
			        <option value="2">二月</option>
			        <option value="3">三月</option>
			        <option value="4">四月</option>
			        <option value="5">五月</option>
			        <option value="6">六月</option>
			        <option value="7">七月</option>
			        <option value="8">八月</option>
			        <option value="9">九月</option>
			        <option value="10">十月</option>
			        <option value="11">十一月</option>
			        <option value="12">十二月</option>
			    </select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
				<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<sys:message content="${message}"/>
			<tr>
				<th style="text-align: center;">编号</th>
				<th style="text-align: center;">姓名</th>
				<th style="text-align: center;">日期</th>
				<th style="text-align: center;">上班时间</th>
			 	<th style="text-align: center;">下班时间</th> 
			 	<th style="text-align: center;">签到</th> 
			 	<th style="text-align: center;">签退</th> 
				<th style="text-align: center;">是否迟到</th>
				<th style="text-align: center;">是否早退</th>
				<th style="text-align: center;">是否旷工</th>
				<th style="text-align: center;">是否加班</th>
				<shiro:hasPermission name="attendance:attLog:edit"><th style="text-align: center;">操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dayAtt" varStatus="index">
			<tr>
				<%-- <td style="text-align: center;">${(page.pageNo - 1) * page.pageSize + index.count}</td> --%>
				<td style="text-align: center;">${dayAtt.user.enrollNumber}</td>
				<td style="text-align: center;">${dayAtt.user.name}</td>
				<td style="text-align: center;">${dayAtt.workTime}</td>
				<td style="text-align: center;">09:30</td>	
				<td style="text-align: center;">18:30</td>
				<td style="text-align: center;"><fmt:formatDate value="${dayAtt.onWorkTime}" pattern="HH:mm:ss"/></td>
				<td style="text-align: center;"><fmt:formatDate value="${dayAtt.offWorkTime}" pattern="HH:mm:ss"/></td>
				<td style="text-align: center;">
					<c:choose>
						<c:when test="${dayAtt.isLate  eq 0}" >否</font></c:when>
						<c:when test="${dayAtt.isLate eq 1}">是</c:when>
					</c:choose>
				</td>
				<td style="text-align: center;">
					<c:choose>
						<c:when test="${dayAtt.leaveEarly  eq 0}" >否</font></c:when>
						<c:when test="${dayAtt.leaveEarly eq 1}" >是</font></c:when>
					</c:choose>
				</td>
				<td style="text-align: center;">
					<c:choose>
						<c:when test="${dayAtt.isCompletion  eq 0}" >否</font></c:when>
						<c:when test="${dayAtt.isCompletion eq 1}" >是</font></c:when>
					</c:choose>
				</td>
				<td style="text-align: center;">
					<c:choose>
						<c:when test="${dayAtt.isOverTime  eq 0}" >否</font></c:when>
						<c:when test="${dayAtt.isOverTime eq 1}" >是</font></c:when>
					</c:choose>
				</td>
				<%-- <td style="text-align: center;">${budget.budgetRecord.note}</td>  --%>
				<shiro:hasPermission name="attendance:attLog:edit"><td style="text-align: center;">
					<c:if test="${budget.bStatus == 1}"><a href="${ctx}/apply/budget/printForm?bId=${budget.bId}">打印</a></c:if>
					<c:if test="${budget.isDel == 0}"><a href="${ctx}/apply/budget/delete?bId=${budget.bId}" onclick="return confirmx('确认要删除该條記錄吗？', this.href)">导出</a></c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>