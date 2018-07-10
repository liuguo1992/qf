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
	
	/* function printTable(){
		var head="<html><head><title></title></head><body>";
		var foot="</body></html>";
		var newstr=document.all.item("printDiv").innerHTML;
		var oldstr=document.body.innerHTML;
		document.body.innerHTML=head+newstr+foot;
		pagesetup_null();
		window.print();
		document.body.innerHTML = oldstr;
	    return false;
	} */
	
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
	 <h3 style="text-align:center;">费用报销单</h3> 
	<table style="width: 100%;margin:0 auto;border-width:1px thin;" border="1" cellpadding="0" cellspacing="0">	
		<thead>
			<!--  <tr >
				<th colspan="5" ><h3>费用报销单</h3></th>
			</tr>  -->
			<tr>
			    <th style="font-weight: 300;">申请日期</th>
			    <td style="text-align: left;
			padding-left: 20px; font-weight: 300;"><fmt:formatDate value="${budget.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
			padding-left: 20px;">${budget.user.name}</td>
			    <th style="font-weight: 300;">申请部门</th>
			    <td style="text-align: left;font-weight: 300;
			padding-left: 20px;">${budget.office.name}</td>
			    <td style="text-align: left;font-weight: 300;
			padding-left: 20px;">费用所属:</td> 
			</tr>
			<tr>
			    <th style="font-weight: 300;">费用类型</th>
			    <td style="text-align: left;font-weight: 300;
			padding-left: 20px;" id="type"><c:choose >
					    <c:when test="${budget.type eq 1}">通讯费</c:when>
						<c:when test="${budget.type eq 2}">游戏分成</c:when>
						<c:when test="${budget.type eq 3}">手续费</c:when>
						<c:when test="${budget.type eq 4}">职工薪酬</c:when>
						<c:when test="${budget.type eq 5}">住房公积金</c:when>
						<c:when test="${budget.type eq 6}">保险费</c:when>
						<c:when test="${budget.type eq 7}">福利费</c:when>
						<c:when test="${budget.type eq 8}">交通费</c:when>
						<c:when test="${budget.type eq 9}">招待费</c:when>
						<c:when test="${budget.type eq 10}">办公费</c:when>
						<c:when test="${budget.type eq 11}">招待费</c:when>
						<c:when test="${budget.type eq 12}">技术服务费</c:when>
						<c:when test="${budget.type eq 13}">市场推广费</c:when>
						<c:when test="${budget.type eq 14}">租赁费</c:when>
						<c:when test="${budget.type eq 15}">车辆费</c:when>
						<c:when test="${budget.type eq 16}">差旅费</c:when>
						<c:when test="${budget.type eq 17}">低值易耗品</c:when>
						<c:when test="${budget.type eq 18}">补偿金</c:when>
						<c:when test="${budget.type eq 19}">研发费</c:when>
						<c:when test="${budget.type eq 20}">咨询费</c:when>
						<c:when test="${budget.type eq 21}">审计验资费</c:when>
						<c:when test="${budget.type eq 22}">会务费</c:when>
						<c:when test="${budget.type eq 23}">网络服务费</c:when>
						<c:when test="${budget.type eq 24}">车船税</c:when>
						<c:when test="${budget.type eq 25}">罚款支出</c:when>
						<c:when test="${budget.type eq 26}">其它</c:when>
					</c:choose></td>
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
			    <th colspan="3" style="font-weight: 300;">报销费用明细</th>
			</tr>
		</thead>
		<tbody>
			<tr>
			    <th style="font-weight: 300;">费用类别</th>
			    <th style="font-weight: 300;">事由摘要</th>
			    <th style="font-weight: 300;">金额</th>
			</tr>
			<tr>
			    <th style="font-weight: 300;"><c:choose>
						<c:when test="${budget.type eq 1}">通讯费</c:when>
						<c:when test="${budget.type eq 2}">游戏分成</c:when>
						<c:when test="${budget.type eq 3}">手续费</c:when>
						<c:when test="${budget.type eq 4}">职工薪酬</c:when>
						<c:when test="${budget.type eq 5}">住房公积金</c:when>
						<c:when test="${budget.type eq 6}">保险费</c:when>
						<c:when test="${budget.type eq 7}">福利费</c:when>
						<c:when test="${budget.type eq 8}">交通费</c:when>
						<c:when test="${budget.type eq 9}">招待费</c:when>
						<c:when test="${budget.type eq 10}">办公费</c:when>
						<c:when test="${budget.type eq 11}">招待费</c:when>
						<c:when test="${budget.type eq 12}">技术服务费</c:when>
						<c:when test="${budget.type eq 13}">市场推广费</c:when>
						<c:when test="${budget.type eq 14}">租赁费</c:when>
						<c:when test="${budget.type eq 15}">车辆费</c:when>
						<c:when test="${budget.type eq 16}">差旅费</c:when>
						<c:when test="${budget.type eq 17}">低值易耗品</c:when>
						<c:when test="${budget.type eq 18}">补偿金</c:when>
						<c:when test="${budget.type eq 19}">研发费</c:when>
						<c:when test="${budget.type eq 20}">咨询费</c:when>
						<c:when test="${budget.type eq 21}">审计验资费</c:when>
						<c:when test="${budget.type eq 22}">会务费</c:when>
						<c:when test="${budget.type eq 23}">网络服务费</c:when>
						<c:when test="${budget.type eq 24}">车船税</c:when>
						<c:when test="${budget.type eq 25}">罚款支出</c:when>
						<c:when test="${budget.type eq 26}">其它</c:when>
					</c:choose></th>
			    <td style="text-align: left;font-weight: 300;
			padding-left: 20px;">${chargeDetail.summary}</td>
			    <td style="text-align: left;font-weight: 300;
			padding-left: 20px;">${chargeDetail.money}</td> 
			</tr>
			<tr>
			    <th style="font-weight: 300;">合计金额</th>
			    <td colspan="2"  style="text-align: left;font-weight: 300;
			padding-left: 20px;">${chargeDetail.money}</td>
			</tr>
			<tr>
			    <th style="font-weight: 300;">金额大写</th>
			    <td colspan="2"  style="text-align: left;font-weight: 300;
			padding-left: 20px;">${chargeDetail.cAmout}</td>
			</tr>
			<tr>
			    <th style="font-weight: 300;">原借款金额</th>
			    <td colspan="2"  style="text-align: left;font-weight: 300;
			padding-left: 20px;"><c:if test="${chargeDetail.lAmout == null}">0.00</c:if></td>
			</tr>
			<tr>
			    <th style="font-weight: 300;">应退余额</th>
			    <td colspan="2"  style="text-align: left;font-weight: 300;
			padding-left: 20px;"><c:if test="${chargeDetail.rAmout == null}">0.00</c:if> </td>
			</tr>
			<tr>
			    <th style="font-weight: 300;">收款单位</th>
			    <td colspan="2"  style="text-align: left;font-weight: 300;
			padding-left: 20px;">${chargeDetail.rUnit}</td>
			</tr>
			<tr>
			    <th style="font-weight: 300;">开户银行（支行）</th>
			    <td colspan="2"  style="text-align: left;font-weight: 300;
			padding-left: 20px;"> ${chargeDetail.aBank}</td>
			</tr>
			<tr>
			    <th style="font-weight: 300;">银行账号</th>
			    <td colspan="2"  style="text-align: left;font-weight: 300;
			padding-left: 20px;"> ${chargeDetail.bAccout}</td>
			</tr>
			<tr>
			    <th style="font-weight: 300;">费用类型</th>
			    <td colspan="2"  style="text-align: left;font-weight: 300;
			padding-left: 20px;">
			    	<%-- <input type="radio" name="" value="${chargeDetail.pMethod}">支票
			    	<input type="radio" name="" value="${chargeDetail.pMethod}">转账
			    	<input type="radio" name="" value="${chargeDetail.pMethod}">现金 --%>
			    	<c:choose>
						<c:when test="${chargeDetail.pMethod eq 0}">支票</c:when>
						<c:when test="${chargeDetail.pMethod eq 1}">转账</c:when>
						<c:when test="${chargeDetail.pMethod eq 2}">现金</c:when>
					</c:choose>
			    </td>
			</tr>
			<tr>
			    <th style="font-weight: 300;">附注</th>
			    <td colspan="2"  style="text-align: left;font-weight: 300;
			padding-left: 20px;">
			    	<%-- <input type="radio" name="" value="${chargeDetail.notes}">
			    	<input type="radio" name="" value="${chargeDetail.notes}"> --%>
			    	<c:choose>
						<c:when test="${chargeDetail.notes eq 0}">预算内支出</c:when>
						<c:when test="${chargeDetail.notes eq 1}">预算外支出</c:when>
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
			<c:forEach items="${budgetRecord}" var="budgetRecord" >
			<tr>
			    <th style="font-weight: 300;">${budgetRecord.role.name}</th>
			    <th style="font-weight: 300;">${budgetRecord.user.name}</th>
			    <th style="font-weight: 300;">
			    	<c:choose>
						<c:when test="${budgetRecord.result eq 0}"></c:when>
						<c:when test="${budgetRecord.result eq 1}">通过</c:when>
						<c:when test="${budgetRecord.result eq 2}">驳回</c:when>
					</c:choose>
			   </th>
			    <th style="font-weight: 300;"><fmt:formatDate value="${budgetRecord.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></th>
			    <td style="font-weight: 300;">${budgetRecord.note}</td> 
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<p></p>
	<!--endprint-->
	<div class="formBar" style="text-align: center;">
				
					<div class="buttonActive">
							<!-- <div class="buttonContent">
								<button id="btnSubmit" type="submit">保存</button>
							</div> -->
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