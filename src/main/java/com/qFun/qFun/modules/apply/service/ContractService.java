package com.qFun.qFun.modules.apply.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qFun.qFun.common.service.CrudService;
import com.qFun.qFun.common.utils.IdGen;
import com.qFun.qFun.common.utils.StringUtils;
import com.qFun.qFun.modules.apply.dao.ApprovalDao;
import com.qFun.qFun.modules.apply.dao.BudgetRecordDao;
import com.qFun.qFun.modules.apply.dao.LoanDao;
import com.qFun.qFun.modules.apply.entity.Approval;
import com.qFun.qFun.modules.apply.entity.BudgetRecord;
import com.qFun.qFun.modules.apply.entity.Loan;
import com.qFun.qFun.modules.apply.utils.NumberToCN;
import com.qFun.qFun.modules.sys.dao.OfficeDao;
import com.qFun.qFun.modules.sys.dao.RoleDao;
import com.qFun.qFun.modules.sys.entity.Office;
import com.qFun.qFun.modules.sys.entity.User;
import com.qFun.qFun.modules.sys.utils.UserUtils;


@Service
@Transactional(readOnly = true)
public class ContractService extends CrudService<LoanDao, Loan>{
	

	@Autowired
	private LoanDao loanDao;
	
	@Autowired
	private ApprovalDao approvalDao;
	
	@Autowired
	private BudgetRecordDao budgetRecordDao; 
	
	@Autowired
	private BudgetRecordService budgetRecordService;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private OfficeDao officeDao;
	
	

	@Transactional(readOnly = false)
	public void saveContract(Loan loan) throws Exception{
		//获取当前用户
		User user = UserUtils.getUser();
		if(user == null){
			throw new RuntimeException("用户不存在！");
		}
		//创建一条借款详情
		loan.setId(IdGen.uuid());
		loan.setuId(user.getId());
		
		//金额换成中文汉字
		double money = Double.parseDouble(loan.getMoney());
		String cAmout = NumberToCN.number2CNMontrayUnit(new BigDecimal(money));
		loan.setcAmout(cAmout);
		
		//将金额保留两位小数保存
		DecimalFormat df = new DecimalFormat("0.00");//格式化
		String _money = df.format(money);
		loan.setMoney(_money);
		loanDao.insert(loan);
		
		//创建一条借款申请
		Approval approval = new Approval();
		approval.setId(IdGen.uuid());
		approval.setuId(user.getId());
		approval.settId(loan.getId());
		approval.setType(UserUtils.APPROVAL_TYPE_FYYS);//借款
		
		//添加一条审批记录	
		BudgetRecord record = new BudgetRecord();
		record.setId(IdGen.uuid());
		record.settId(approval.getId());
				
		Office office = user.getOffice();
		if(office == null ){
			throw new RuntimeException("部门不存在！");
		}
		//获取负责人
		User _user = office.getPrimaryPerson(); 
		if(_user == null ){
			throw new RuntimeException("上级用户不存在！");
		}
		//判断是否是当前用户
		if(_user.getId().equals(user.getId())){
			//判断该费用是否为市场部监控费用
			if(loan.getType() == 4){
				List<String> userIdList = roleDao.getUserByRoleName(UserUtils.USER_ROLE_SCJK);  //对应数据库的enname
				approval.setpId(userIdList.get(0));
				approval.setStep(4);
				record.setuId(approval.getpId());
			}else{
				//预算专管员审核 
				List<String> userIdList = roleDao.getUserByRoleName(UserUtils.USER_ROLE_SCJK);
				approval.setpId(userIdList.get(0));
				approval.setStep(5);
				record.setuId(approval.getpId());
			}
		}else{
			//负责人
			approval.setpId(_user.getId());
			approval.setStep(2);
			record.setuId(approval.getpId());
		}
		approvalDao.insert(approval);
		budgetRecordDao.insert(record);
		//钉钉推送。
	}
	
	
	/**
	 * 预算审批
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public void updateContractRecord(BudgetRecord budgetRecord) throws Exception{
		System.out.println("------------进入service");
		//本条借款申请设置成已审批状态
		budgetRecord.setStatus(1);
		
		Approval approval = approvalDao.get(budgetRecord.gettId());
		if(approval == null ){
			throw new RuntimeException("该申请不存在，请重新申请！");
		}
		
		//驳回
		if(budgetRecord.getResult() == 2){
			approval.setbStatus(1); //流程已完成
			approval.setpDate(new Date());
			approval.setpStatus(1);//已审批
			approval.setResult(budgetRecord.getResult());
			approvalDao.update(approval);
			//更新记录
				budgetRecordDao.updateBudgetRecord(budgetRecord);
				return;
			}
		
		//流程
		switch (approval.getStep()) {
		case 1:
			break;
		case 2:   //部门经理审批
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case2(approval,budgetRecord);
			break;
		case 3:
			break;
		case 4:  //市场监控专员
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case4(approval,budgetRecord);
			break;
		case 5:  //预算专管
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case5(approval,budgetRecord);
			break;
		case 6:
			break;
		case 7:  //付可祥审批
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case7(approval,budgetRecord);
			break;
		case 8:  //人事总监
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case8(approval,budgetRecord);
			break;
		case 9:	 //法务审核
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case9(approval, budgetRecord);
			break;
		case 10: //会计审核
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case10(approval, budgetRecord);
			break;
		case 11: //财务经理
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case11(approval, budgetRecord);
			break;
		case 12: // 分管总监
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case12(approval, budgetRecord);
			break;
		case 13: //周天石
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case13(approval, budgetRecord);
			break;
		case 14: //张家慧
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case14(approval, budgetRecord);
			break;
		case 15:  //总经理
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case15(approval, budgetRecord);
			break;
		default:
			break;
		}
		
	}

	private void case2(Approval approval,BudgetRecord budgetRecord) throws Exception{
		Loan loan = loanDao.get(approval.gettId());
		if(loan == null ){
			throw new RuntimeException("该预算审批不存在！");
		}
		if(loan.getType() == 1 ){  //市场推广费用
			//查询市场部监控专员（会计部门陈英姿）
			List<String> userIdList = roleDao.getUserByRoleName(UserUtils.USER_ROLE_SCJK);  //对应数据库的enname
			approval.setpId(userIdList.get(0));
			approval.setStep(4);
			approvalDao.update(approval);
			
			//添加一条审批记录
			budgetRecordService.insertBudgetRecord(budgetRecord, approval.getpId());
			
		}else{
			//预算专员
			case4(approval, budgetRecord);
			return; 
			/*List<S  tring> userIdList = roleDao.getUserByRoleName(UserUtils.USER_ROLE_YSZG);  //对应数据库的enname
			approval.setpId(userIdList.get(0));
			approval.setStep(5);
			approvalDao.update(approval);
			//添加一条审批记录
			budgetRecordService.insertBudgetRecord(budgetRecord, approval.getpId());*/
			}
		
	}
	
	private void case4(Approval approval,BudgetRecord budgetRecord){
		//预算专管
		List<String> result = roleDao.getUserByRoleName(UserUtils.USER_ROLE_YSZG);
		if(result.size() <= 0 ){
			throw new RuntimeException("角色【预算专管】不存在，请核对角色信息！");
		}
		
		approval.setpId(result.get(0));//设置下一级审批人
		approval.setStep(5);//设置流程步骤
		approvalDao.update(approval);
		//新增一条审批记录
		budgetRecordService.insertBudgetRecord(budgetRecord, approval.getpId());
	}
	
	private void case5(Approval approval,BudgetRecord budgetRecord){
		Loan loan = loanDao.get(approval.gettId());
		if(loan == null ){
			throw new RuntimeException("该预算审批不存在！");
		}
		List<String> result = null;
		if(loan.getType() == 2){  //工资成本
			result = roleDao.getUserByRoleName(UserUtils.USER_ROLE_CWZJ);
			if(result.size() <= 0 ){
				throw new RuntimeException("角色【财务总监】不存在，请核对角色信息！");
			}
			approval.setStep(7);//设置流程步骤
		}
		if(loan.getType() == 3){//人力资源费用
			result = roleDao.getUserByRoleName(UserUtils.USER_ROLE_RSZJ);
			if(result.size() <= 0 ){
				throw new RuntimeException("角色【人事总监】不存在，请核对角色信息！");
			}
			approval.setStep(8);//设置流程步骤
		}
		if(loan.getType() == 4){  //法务
			result = roleDao.getUserByRoleName(UserUtils.USER_ROLE_FW);
			if(result.size() <= 0 ){
				throw new RuntimeException("角色【法务】不存在，请核对角色信息！");
			}
			approval.setStep(9);//设置流程步骤
		}
		approval.setpId(result.get(0));//设置下一级审批人
		approvalDao.update(approval);
		//新增一条审批记录
		budgetRecordService.insertBudgetRecord(budgetRecord, approval.getpId());
	}
	
	
	private void case7(Approval approval,BudgetRecord budgetRecord){
		//人事总监
		List<String> result = roleDao.getUserByRoleName(UserUtils.USER_ROLE_RSZJ);
		if(result.size() <= 0 ){
			throw new RuntimeException("角色【人事总监】不存在，请核对角色信息！");
		}
		approval.setStep(8);//设置流程步骤
		approval.setpId(result.get(0));//设置下一级审批人
		approvalDao.update(approval);
		//新增一条审批记录
		budgetRecordService.insertBudgetRecord(budgetRecord, approval.getpId());
	}
	
	private void case8(Approval approval,BudgetRecord budgetRecord){
		//法务
		List<String> result = roleDao.getUserByRoleName(UserUtils.USER_ROLE_FW);
		if(result.size() <= 0 ){
			throw new RuntimeException("角色【法务】不存在，请核对角色信息！");
		}
		approval.setStep(9);//设置流程步骤
		approval.setpId(result.get(0));//设置下一级审批人
		approvalDao.update(approval);
		//新增一条审批记录
		budgetRecordService.insertBudgetRecord(budgetRecord, approval.getpId());
	}
	
	private void case9(Approval approval,BudgetRecord budgetRecord){
		//会计
		List<String> result = roleDao.getUserByRoleName(UserUtils.USER_ROLE_KJ);
		if(result.size() <= 0 ){
			throw new RuntimeException("角色【会计】不存在，请核对角色信息！");
		}
		approval.setStep(10);//设置流程步骤
		approval.setpId(result.get(0));//设置下一级审批人
		approvalDao.update(approval);
		//新增一条审批记录
		budgetRecordService.insertBudgetRecord(budgetRecord, approval.getpId());
	}
	
	
	private void case10(Approval approval,BudgetRecord budgetRecord){
		List<String> result = roleDao.getUserByRoleName(UserUtils.USER_ROLE_CWZJ);
		if(result.size() <= 0 ){
			throw new RuntimeException("角色【财务总监】不存在，请核对角色信息！");
		}
		//查询财务经理之前是否审批过该申请
		if(!isApproval(budgetRecord.gettId(), result.get(0))){
			approval.setStep(11);//设置流程步骤财务经理审核
			approval.setpId(result.get(0));//设置为下一级审批人
		}else{
			//找到当前用户所在组或者部门的负责人
			case11(approval, budgetRecord);
			return;
		/*	approval.setpId(getPrimaryPerson().getId());
			approval.setStep(12);//设置流程步骤为分管总监审核
*/		} 
		approvalDao.update(approval);
		//新增一条审批记录
		budgetRecordService.insertBudgetRecord(budgetRecord, approval.getpId());
	}
	
	private void case11(Approval approval,BudgetRecord budgetRecord){
		//查询分管总监之前是否审批过该申请
		String userId = budgetRecordService.isApproval(budgetRecord, getPrimaryPerson().getName());
		if(!StringUtils.isNotEmpty(userId)){
			approval.setStep(12);//设置流程步骤
			approval.setpId(userId);//设置为下一级审批人
		}else{
			//周天石审批
			case12(approval, budgetRecord);
			return;
			/*User user = UserUtils.getByName(UserUtils.USER_ZTS);
			if(user == null){
				throw new RuntimeException("用户【"+UserUtils.USER_ZTS+"】不存在,请核实人员信息！");
			}
			approval.setpId(user.getId());
			approval.setStep(13);//设置流程步骤为分管总监审核
*/		} 
		approvalDao.update(approval);
		//新增一条审批记录
		budgetRecordService.insertBudgetRecord(budgetRecord, approval.getpId());
	}
	
	
	private void case12(Approval approval,BudgetRecord budgetRecord){
		//判断周天石是否审批过
		String userId = budgetRecordService.isApproval(budgetRecord, UserUtils.USER_ZTS);
		if(!StringUtils.isNotEmpty(userId)){
			approval.setStep(13);//设置流程步骤周天石
			approval.setpId(userId);
		}else{
			//张佳慧
			case13(approval, budgetRecord);
			return;
			/*User user = UserUtils.getByName(UserUtils.USER_ZJH);
			if(user == null){
				throw new RuntimeException("用户【"+UserUtils.USER_ZJH+"】不存在,请核实人员信息！");
			}
			approval.setpId(user.getId());
			approval.setStep(14);//设置流程步骤为分管总监审核*/
		} 
		approvalDao.update(approval);
		//新增一条审批记录
		budgetRecordService.insertBudgetRecord(budgetRecord, approval.getpId());
	}
	
	private void case13(Approval approval,BudgetRecord budgetRecord){
		//判断张佳慧是否审批过
		String userId = budgetRecordService.isApproval(budgetRecord, UserUtils.USER_ZJH);
		if(!StringUtils.isNotEmpty(userId)){
			approval.setStep(14);//设置流程步骤张佳慧
			approval.setpId(userId);
		}else{
			//曹宇
			case14(approval, budgetRecord);
			return;
			/*User user = UserUtils.getByName(UserUtils.USER_CY);
			if(user == null){
				throw new RuntimeException("用户【"+UserUtils.USER_CY+"】不存在,请核实人员信息！");
			}
			approval.setpId(user.getId());
			approval.setStep(15);//设置流程步骤为分管总监审核*/

	} 
		approvalDao.update(approval);
		//新增一条审批记录
		budgetRecordService.insertBudgetRecord(budgetRecord, approval.getpId());
	}
	
	private void case14(Approval approval,BudgetRecord budgetRecord){
		//判断caoyu是否审批过
		String userId = budgetRecordService.isApproval(budgetRecord, UserUtils.USER_CY);
		if(!StringUtils.isNotEmpty(userId)){
			approval.setStep(15);//设置流程步骤曹宇
			approval.setpId(userId);
		}else{
			case15(approval, budgetRecord);
			return;
			/*
			approval.setbStatus(1);
			approval.setpStatus(1);
			approval.setResult(budgetRecord.getResult());
			approval.setpDate(new Date());
*/			
		} 
		/*approvalDao.update(approval);
		//新增一条审批记录
		budgetRecordService.insertBudgetRecord(budgetRecord, approval.getpId());*/
	}
	
	

	private void case15(Approval approval,BudgetRecord budgetRecord){
		//审批完成
		approval.setbStatus(1);
		approval.setpStatus(1);
		approval.setResult(budgetRecord.getResult());
		approval.setpDate(new Date());
		approvalDao.update(approval);
	}
	
	
	private boolean isApproval(String tId,String uId){
		Map<String, Object> map = new HashMap<>();
		map.put("tId", tId);
		map.put("uId", uId);
		BudgetRecord budgetRecord = budgetRecordDao.getParams(map);
		if(budgetRecord != null){
			return true;
		}
		return false;
	}
	
	
	private User getPrimaryPerson(){
		//找到当前用户所在组或者部门的负责人
		User user = UserUtils.getUser();
		Office office = user.getOffice();
		if(office == null){
			throw new RuntimeException("当前部门不存在！");
		}
		if(office.getGrade().equals("2")){ //部门
			return user;
		}
		if(office.getGrade().equals("3")){ //组
			User primaryPerson = officeDao.get(office.getParentId()).getPrimaryPerson();
			if(primaryPerson == null ){
				throw new RuntimeException("当前部门没有负责人，请分配负责人！");
			}
			return primaryPerson;
		}
		return null;
	}
	
}
