<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<head>

<style type="text/css" >
		*{margin: 1;padding: 0;}
		td,th{padding: 5px;
			font-weight: 300;
		}
		h3,td{text-align: center;}
		h3{padding:20px 0px;}
		.left{
			text-align: left;
			padding-left: 20px;
		}
		.close{
			width:100px;
    		height:50px;
		}
</style>
<script type="text/javascript">
	$("#btnSubmit").click(function() {
		if ($('#billMerchantId').val() != '') {
			$('#merchantId').val($('#billMerchantId').val());
		}
		;
	});
	
	//设置网页打印的页眉页脚为空 
	function pagesetup_null(){ 
		var hkey_root, hkey_path, hkey_key;
			hkey_root = "HKEY_CURRENT_USER"
			hkey_path = "\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
			try {
				var RegWsh = new ActiveXObject("WScript.Shell");
				hkey_key = "header";
				RegWsh.RegWrite(hkey_root + hkey_path + hkey_key, "");
				hkey_key = "footer";
				RegWsh.RegWrite(hkey_root + hkey_path + hkey_key, "");
			} catch (e) {
		}
	} 
	
	function printPage() {
        //获取当前页的html代码  
        var bodyhtml = window.document.body.innerHTML;  
        //设置打印开始区域、结束区域  
        var startFlag = "<!--startprint-->";  
        var endFlag = "<!--endprint-->";  
        // 要打印的部分  
        var printhtml = bodyhtml.substring(bodyhtml.indexOf(startFlag),   
                bodyhtml.indexOf(endFlag));  
        // 生成并打印ifrme  
        var f = document.getElementById('printf'); 
        pagesetup_null();
        f.contentDocument.write(printhtml);
        f.contentDocument.close();
        f.contentWindow.print();
    }  
</script>
</head>
<body>
<!-- <OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height="0" id="WebBrowser3" width="0" VIEWASTEXT>
</OBJECT> -->
<div class="pageContent">
		<!--startprint-->
		<div style="width: 80%;margin:20px auto">
	<!-- 表格1 -->
	 <h3 style="text-align:center;">合同和预算审批单</h3> 
	<table style="width: 100%;margin:0 auto;border-width:1px thin;" border="1" cellpadding="0" cellspacing="0">	
		<thead>
			<tr>
			    <th style="font-weight: 300;">申请日期</th>
			    <td style="text-align: left;
			padding-left: 20px; font-weight: 300;"><fmt:formatDate value="${loan.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			    <th style="font-weight: 300;">流程所属</th>
			    <td style="text-align: left;
			padding-left: 20px; font-weight: 300;">财务部</td>
			    <td style="text-align: left;font-weight: 300;
			padding-left: 20px; font-weight: 300;">
			    	编号:AJPSOIDFJAPSODIASDPO;<br />
			    	单位:元
			    </td> 
			</tr>
		</thead>
		<tbody>
			<tr>
			    <th style="font-weight: 300;">申请人</th>
			    <td style="text-align: left;font-weight: 300;
			padding-left: 20px;">${loan.user.name}</td>
			    <th style="font-weight: 300;">申请部门</th>
			    <td style="text-align: left;font-weight: 300;
			padding-left: 20px;">${loan.office.name}</td>
			    <td style="text-align: left;font-weight: 300;
			padding-left: 20px;">费用所属:</td> 
			</tr>
			<tr>
			    <th style="font-weight: 300;">费用类型</th>
			    <td style="text-align: left;font-weight: 300;
			padding-left: 20px;" id="type"></td>
			    <th style="font-weight: 300;"></th>
			    <td></td>
			    <td></td> 
			</tr>
		</tbody>
	</table>
	<!-- 表格2 -->
	<table style="width: 100%;margin:50px auto;" border="1" cellpadding="0" cellspacing="0">	
		<thead>
			<tr>
			    <th colspan="3" style="font-weight: 300;">借款明细</th>
			</tr>
		</thead>
		<tbody>
			<tr>
			    <th style="font-weight: 300;">费用类别</th>
			    <th style="font-weight: 300;">事由摘要</th>
			    <th style="font-weight: 300;">金额</th>
			</tr>
			<tr>
			    <th style="font-weight: 300;"></th>
			    <td style="text-align: left;font-weight: 300;
			padding-left: 20px;">${loan.reason}</td>
			    <td style="text-align: left;font-weight: 300;
			padding-left: 20px;">${loan.money}</td> 
			</tr>
			<tr>
			    <th style="font-weight: 300;">合计金额</th>
			    <td colspan="2"  style="text-align: left;font-weight: 300;
			padding-left: 20px;">${loan.money}</td>
			</tr>
			<tr>
			    <th style="font-weight: 300;">金额大写</th>
			    <td colspan="2"  style="text-align: left;font-weight: 300;
			padding-left: 20px;">${loan.cAmout}</td>
			</tr>
			<tr>
			    <th style="font-weight: 300;">原借款金额</th>
			    <td colspan="2"  style="text-align: left;font-weight: 300;
			padding-left: 20px;"><c:if test="${loan.lAmout == null}">0.00</c:if></td>
			</tr>
			<tr>
			    <th style="font-weight: 300;">应退余额</th>
			    <td colspan="2"  style="text-align: left;font-weight: 300;
			padding-left: 20px;"><c:if test="${loan.rAmout == null}">0.00</c:if> </td>
			</tr>
			<tr>
			    <th style="font-weight: 300;">收款单位</th>
			    <td colspan="2"  style="text-align: left;font-weight: 300;
			padding-left: 20px;">${loan.rUnit}</td>
			</tr>
			<tr>
			    <th style="font-weight: 300;">开户银行（支行）</th>
			    <td colspan="2"  style="text-align: left;font-weight: 300;
			padding-left: 20px;"> ${loan.aBank}</td>
			</tr>
			<tr>
			    <th style="font-weight: 300;">银行账号</th>
			    <td colspan="2"  style="text-align: left;font-weight: 300;
			padding-left: 20px;"> ${loan.bAccout}</td>
			</tr>
			<tr>
			    <th style="font-weight: 300;">费用类型</th>
			    <td colspan="2"  style="text-align: left;font-weight: 300;
			padding-left: 20px;">
			    	<%-- <input type="radio" name="" value="${chargeDetail.pMethod}">支票
			    	<input type="radio" name="" value="${chargeDetail.pMethod}">转账
			    	<input type="radio" name="" value="${chargeDetail.pMethod}">现金 --%>
			    	<c:choose>
						<c:when test="${loan.pMethod eq 0}">支票</c:when>
						<c:when test="${loan.pMethod eq 1}">转账</c:when>
						<c:when test="${loan.pMethod eq 2}">现金</c:when>
					</c:choose>
			    </td>
			</tr>
		</tbody>
	</table>
	<!-- 表格3 -->
	<table style="width: 100%;margin:50px auto;" border="1" cellpadding="0" cellspacing="0">	
		<thead>
			<tr>
			    <th style="font-weight: 300;">审批级别</th>
			    <th style="font-weight: 300;">审批人</th>
			    <th style="font-weight: 300;">状态</th>
			    <th style="font-weight: 300;">时间</th>
			    <th style="font-weight: 300;">审批备注</th> 
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${loanBudgetRecord}" var="loanBudgetRecord" >
			<tr>
			    <th style="font-weight: 300;">${loanBudgetRecord.role.name}</th>
			    <th style="font-weight: 300;">${loanBudgetRecord.user.name}</th>
			    <th style="font-weight: 300;">
			    	<c:choose>
						<c:when test="${loanBudgetRecord.budgetRecord.result eq 0}"></c:when>
						<c:when test="${loanBudgetRecord.budgetRecord.result eq 1}">通过</c:when>
						<c:when test="${loanBudgetRecord.budgetRecord.result eq 2}">驳回</c:when>
					</c:choose>
			   </th>
			    <th style="font-weight: 300;"><fmt:formatDate value="${loanBudgetRecord.budgetRecord.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></th>
			    <td style="font-weight: 300;">${loanBudgetRecord.budgetRecord.note}</td> 
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<p></p>
	<!--endprint-->
	<div class="formBar" style="text-align: center;">
				
					<div class="buttonActive">
							<div class="buttonContent">
								<button id="print2" type="button" onclick="printPage();" class="close">打印</button>&nbsp;
								<button id="btnCancel" type="button" onclick="javascript:history.back(-1);" class="close">取消</button>
							</div> 
						</div
				<div class="button">
						<div class="buttonContent">
							
						</div>
					</div>
		</div>

	<iframe id="printf" src="" width="0" height="0" frameborder="0"></iframe>
</div>
</div>

</body>