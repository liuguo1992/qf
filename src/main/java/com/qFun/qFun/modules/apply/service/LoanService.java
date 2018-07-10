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

import com.qFun.qFun.common.persistence.Page;
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
public class LoanService extends CrudService<LoanDao, Loan>{
	
	
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
	public Page<Loan> getPage(Page<Loan> page, Loan loan) {
		loan.setPage(page);
		page.setList(loanDao.getPage(loan));
		return page;
	}
	
	
	@Transactional(readOnly = false)
	public void saveLoan(Loan loan) throws Exception{
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
		approval.setType(UserUtils.APPROVAL_TYPE_JK);//借款
		
		//添加一条审批记录	
		BudgetRecord record = new BudgetRecord();
		record.setId(IdGen.uuid());
		
		//设置下一级审批人
		//获取当前用户所在部门
		Office office = user.getOffice();
		if(office.getPrimaryPerson().getId().equals(user.getId()) && office.getType().equals("2")) {
			//当前用户为部门总监 找会计确认是否有存款
			//User _user = UserUtils.getByName(UserUtils.USER_ROLE_SHKJ);//审核会计
			List<String> result = roleDao.getUserByRoleName(UserUtils.USER_ROLE_SHKJ);
			if(result.size() <= 0 ){
				throw new RuntimeException("审核会计不存在，请核对人员信息！");
			}
			approval.setpId(result.get(0));
			approval.setStep(5);
			record.setuId(result.get(0));
		}else{
			//普通用户 找到对应的组长审核
			approval.setpId(office.getPrimaryPerson().getId());
			approval.setStep(3);
			record.setuId(approval.getpId());
		}
		approvalDao.insert(approval);
		
		record.settId(approval.getId());
		budgetRecordDao.insert(record);
	}
	
	
	
	
	/**
	 * 获取借款相关信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getLoanInfo(String id) throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		BudgetRecord budgetRecord = budgetRecordDao.getById(id);
		if(budgetRecord == null){
			throw new RuntimeException("该审批记录不存在！");
		}
		map.put("budgetRecord", budgetRecord);
		Approval approval = approvalDao.get(budgetRecord.gettId());
		if(approval == null){
			throw new RuntimeException("该申请记录不存在！");
		}
		map.put("loan", loanDao.getLoanAndUser(approval.gettId()));
		return map;
	}
	
	
	
	@Transactional(readOnly = false)
	public void updateLoanRecord(BudgetRecord budgetRecord) throws Exception{
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
		
		//流程步骤
		switch (approval.getStep()) {    
		case 1:
			break;
		case 2:
			break;
		case 3:
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case3(approval,budgetRecord);
			break;
		case 4:
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case4(approval,budgetRecord);
		break;
		case 5:
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case5(approval,budgetRecord);
			
		break;
		case 6:
		break;
		case 7:
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case7(approval,budgetRecord);
		break;
		case 8:
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case8(approval,budgetRecord);
		break;
		case 9:
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case9(approval,budgetRecord);
		break;
		case 10:
		break;
		case 11:
		break;
		case 12:
		break;
		case 13:
		break;
		}
	}
	
	
	
	private void case3(Approval approval,BudgetRecord budgetRecord){
		//分管总监审批
		User user = UserUtils.getUser();
		if(user == null ){
			throw new RuntimeException("当前用户不存在或登录超时！");
		}
		//获取当前部门
		Office office = user.getOffice();
		if(office == null ){
			throw new RuntimeException("当前部门为空！");
		}
		User _user = officeDao.get(office.getParentId()).getPrimaryPerson();//获取用户对应的分销总监
		if(_user == null){
			throw new RuntimeException("该部门总监不存在，请设置！");   
		}
		approval.setpId(_user.getId());//设置下一级审批人
		approval.setStep(4);//设置流程步骤
		approvalDao.update(approval);
		//新增一条审批记录
		budgetRecordService.insertBudgetRecord(budgetRecord, _user.getId());
	}
	
	private void case4(Approval approval,BudgetRecord budgetRecord){
		//会计审批
		//User user = UserUtils.getByName(UserUtils.USER_ROLE_SHKJ);
		List<String> result = roleDao.getUserByRoleName(UserUtils.USER_ROLE_SHKJ);
		if(result.size() <= 0 ){
			throw new RuntimeException("角色【审核会计】不存在，请核对人员信息！");
		}
		
		approval.setpId(result.get(0));//设置下一级审批人
		approval.setStep(5);//设置流程步骤
		approvalDao.update(approval);
		//新增一条审批记录
		budgetRecordService.insertBudgetRecord(budgetRecord, result.get(0));
	}
	
	private void case5(Approval approval,BudgetRecord budgetRecord){
		//判断借款金额是否大于6万
		Loan loan = loanDao.get(approval.gettId());
		if(loan == null ){
			throw new RuntimeException("该借款不存在，请核实！");
		}
		if(Double.valueOf(loan.getMoney()) < 60000){
			//找财务经理审批
			case8(approval,budgetRecord);
			return;
		}else{
			//判断张佳慧之前是否审批该申请
			String userId = budgetRecordService.isApproval(budgetRecord, UserUtils.USER_ZJH);
			if(StringUtils.isNotEmpty(userId)){
				//曹宇审批
				case7(approval,budgetRecord);
				return;
			}else{
				approval.setpId(userId);
				approval.setStep(7);
				approvalDao.update(approval);
			}
		}
		//新增一条审批记录
		budgetRecordService.insertBudgetRecord(budgetRecord, UserUtils.getByName(UserUtils.USER_ZJH).getId());
	}
	
	private void case7(Approval approval,BudgetRecord budgetRecord){
		String userId = UserUtils.getByName(UserUtils.USER_CY).getId();
		//曹宇审批
		approval.setpId(userId);//设置下一级审批人
		approval.setStep(8);//设置流程步骤
		approvalDao.update(approval);
		//新增一条审批记录
		budgetRecordService.insertBudgetRecord(budgetRecord, userId);
	}
	
	private void case8(Approval approval,BudgetRecord budgetRecord){
		//String userId = UserUtils.getByName(UserUtils.USER_ROLE_CWZJ).getId();
		List<String> result = roleDao.getUserByRoleName(UserUtils.USER_ROLE_CWZJ);
		if(result.size() <= 0 ){
			throw new RuntimeException("角色【审核会计】不存在，请核对人员信息！");
		}
		//财务总监审批
		approval.setpId(result.get(0));//设置下一级审批人
		approval.setStep(9);//设置流程步骤
		approvalDao.update(approval);
		//新增一条审批记录
		budgetRecordService.insertBudgetRecord(budgetRecord, result.get(0));
	}
	
	private void case9(Approval approval,BudgetRecord budgetRecord){
		//财务总监审批
		approval.setbStatus(1);//流程结束
		approval.setResult(budgetRecord.getResult());
		approval.setpDate(new Date());
		approval.setpStatus(1);
		approval.setStep(10);//设置流程步骤
		approvalDao.update(approval);
	}
	
	
	
	
	public Map<String,Object> getLoanFormInfo(Loan loan)throws Exception{
		Map<String, Object> map =new HashMap<String, Object>();
		//获取申请人相关信息和借款明细
		map.put("loan", loanDao.getLoanAndUser(loan.getId()));
		//审批人列表
		map.put("loanBudgetRecord", loanDao.getBudgetRecord(loan));
		return map;
	}
	
	
	
	
	
	
}
