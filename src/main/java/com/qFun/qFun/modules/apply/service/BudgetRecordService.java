package com.qFun.qFun.modules.apply.service;


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
import com.qFun.qFun.modules.apply.dao.BudgetDao;
import com.qFun.qFun.modules.apply.dao.BudgetRecordDao;
import com.qFun.qFun.modules.apply.dao.ChargeDetailDao;
import com.qFun.qFun.modules.apply.entity.Approval;
import com.qFun.qFun.modules.apply.entity.Budget;
import com.qFun.qFun.modules.apply.entity.BudgetRecord;
import com.qFun.qFun.modules.sys.dao.OfficeDao;
import com.qFun.qFun.modules.sys.dao.RoleDao;
import com.qFun.qFun.modules.sys.dao.UserDao;
import com.qFun.qFun.modules.sys.entity.Office;
import com.qFun.qFun.modules.sys.entity.User;
import com.qFun.qFun.modules.sys.utils.UserUtils;

@Service
@Transactional(readOnly = true)
public class BudgetRecordService extends CrudService<BudgetRecordDao,BudgetRecord>{
	
	@Autowired
	private BudgetRecordDao budgetRecordDao;
	
	
	@Autowired
	private BudgetDao budgetDao;
	
	@Autowired
	private ChargeDetailDao chargeDetailDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao  roleDao;
	
	@Autowired
	private OfficeDao officeDao;

	@Autowired
	private ApprovalDao approvalDao;
	
	
	/**
	 * 分页查询审批列表
	 */
	@Transactional(readOnly = false)
	public Page<BudgetRecord> findPage(Page<BudgetRecord> page, BudgetRecord budgetRecord) {
		return super.findPage(page, budgetRecord);
	}
	
	@Transactional(readOnly = false)
	public Page<BudgetRecord> getPage(Page<BudgetRecord> page, BudgetRecord budgetRecord) {
		budgetRecord.setPage(page);
		page.setList(budgetRecordDao.getPage(budgetRecord));
		return page;
	}

	
	/**
	 * 获取申请审批的所有信息
	 * @param id  审批记录表id
	 * @return
	 */
	@Transactional(readOnly = false)
	public Map<String,Object> getBudgetInfo(String id){
		Map<String,Object> map = new HashMap<>();
		Map<String,Object> param = new HashMap<>();
		param.put("id", id);
		BudgetRecord record = budgetRecordDao.getParam(param);
		map.put("budgetRecord", record);
		map.put("chargeDetail", chargeDetailDao.getByBudgetId(record.gettId()));
		return map;
	}
	
	
	
	/**
	 * 修改审批记录
	 * @param budgetRecord
	 * @throws Exception 
	 */
	@Transactional(readOnly = false)
	public void updateBudgetRecord(BudgetRecord budgetRecord) throws Exception{
		//获取流程申请信息
		Budget budget = budgetDao.get(budgetRecord.gettId());
		
		//驳回
		if(budgetRecord.getResult() == 2){
			budget.setbStatus(1); //流程已完成
			budget.setpDate(new Date());
			budget.setpStatus(1);//已审批
			budget.setpNote(budgetRecord.getNote());
			budget.setResult(budgetRecord.getResult());
			budgetDao.update(budget);
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			return;
		}
		
		//流程步骤
		switch (budget.getStep()) {
		case 1: //申请人申请开始
			break;
		case 2: //是否是部门经理
			break;
		case 3://
			break;
		case 4: // 部门经理审批
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case4(budget,budgetRecord);
			break;
		case 5:// 是否市场部门监控费用
			break;
		case 6:// 市场部费用监控专员监控
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case4(budget,budgetRecord);
			break;
		case 7://金额大于700
			break;
		case 8://分管总监审批
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case8(budget,budgetRecord);
			break;
		case 9:// 
			break;
		case 10:// 周天石审批
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case10(budget,budgetRecord);
			break;
		case 11: //会计审批
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case11(budget,budgetRecord);
			break;
		case 12://财务经理审批
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case12(budget,budgetRecord);
			break;
		case 13://招待费用大于500或者大于1万
			break;
		case 14://张佳慧审批
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case14(budget,budgetRecord);
			break;
		case 15://曹宇审批
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case15(budget,budgetRecord);
			break;
		case 16://申请人打印报销
			break;
		}
	}
	
	
	 
	
	/**
	 * 流程审批办理 case8
	 * @param budget
	 * @param budgetRecord
	 */
	private void case8(Budget budget,BudgetRecord budgetRecord) throws Exception{
		//獲取當前用戶，并判斷當前用戶是否是周天石  ，是，则跳过当前审批
		User user = UserUtils.getUser();
		if(user != null && user.getName().equals("周天石")){
			case10(budget, budgetRecord);
			return;
		};
		
		//修改下一级审批人为周天石，并添加一条记录
		budget.setpId(UserUtils.getByName(UserUtils.USER_ZTS).getId());
		budget.setStep(10);
		budgetDao.update(budget);
		//清除缓存
		UserUtils.removeCache(UserUtils.USER_CACHE_NAME);
		//添加一条记录
		insertBudgetRecord(budgetRecord, UserUtils.getByName(UserUtils.USER_ZTS).getId());
	}
	
	
	/**
	 * case4
	 * @param budget
	 * @param budgetRecord
	 */
	private void case4(Budget budget,BudgetRecord budgetRecord) throws Exception{
		if(budget.getType() == 12 && budget.getStep() != 6){  //市场推广费用
			//查询市场部监控专员（会计部门陈英姿）
			List<String> userIdList = roleDao.getUserByRoleName(UserUtils.USER_ROLE_SCJK);  //对应数据库的enname
			budget.setpId(userIdList.get(0));
			budget.setStep(6);
			budgetDao.update(budget);
			
			//添加一条审批记录
			insertBudgetRecord(budgetRecord, userIdList.get(0));
			
		}else{
			//判断金额是否大于1000，是找周天石，不是，找分管总监
			if(Double.valueOf(budget.getMoney()) > 1000){
				case8(budget, budgetRecord);
			}else{
				Office office = UserUtils.getUser().getOffice();
				User pUser = officeDao.get(office.getParentId()).getPrimaryPerson();
				budget.setpId(pUser.getId());
				budget.setStep(8);
				budgetDao.update(budget);
				
				//添加一条审批记录
				insertBudgetRecord(budgetRecord, pUser.getId());
			}
		}
	}
	
	
	/**
	 * case10
	 * @param budget
	 * @param budgetRecord
	 */
	private void case10(Budget budget,BudgetRecord budgetRecord) throws Exception{
		//查询审核会计
		List<String> userIdList = roleDao.getUserByRoleName(UserUtils.USER_ROLE_SHKJ);
		budget.setpId(userIdList.get(0));
		budget.setStep(11);
		budgetDao.update(budget);
		
		//添加一条审批记录
		insertBudgetRecord(budgetRecord, userIdList.get(0));
	}
	
	
	/**
	 * case11
	 * @param budget
	 * @param budgetRecord
	 */
	private void case11(Budget budget,BudgetRecord budgetRecord) throws Exception{
		//查询财务总监
		List<String> userIdList = roleDao.getUserByRoleName(UserUtils.USER_ROLE_CWZJ);
		budget.setpId(userIdList.get(0));
		budget.setStep(12);
		budgetDao.update(budget);
		
		//添加一条审批记录
		insertBudgetRecord(budgetRecord, userIdList.get(0));
	}
	
	
	/**
	 * case12
	 * @param budget
	 * @param budgetRecord
	 */
	private void case12(Budget budget,BudgetRecord budgetRecord)  throws Exception{
		//判断是否是招待费并且判断金额  9 招待费
		if(budget.getType() != 9 && Double.valueOf(budget.getMoney()) > 10000){
			
			//判断张佳慧此前是否审批过 是 则跳过 不是 则张佳慧审批.
			String userId = isApproval(budgetRecord,UserUtils.USER_ZJH);
			if(StringUtils.isNotEmpty(userId)){
				case14(budget, budgetRecord);
				return;
			}
			//张佳慧审批
			budget.setpId(userId);
			budget.setStep(14);
			budgetDao.update(budget);
			//清除缓存
			UserUtils.removeCache(UserUtils.USER_CACHE_NAME);
			
			//添加一条记录
			insertBudgetRecord(budgetRecord, UserUtils.getByName(UserUtils.USER_ZJH).getId());
		}else{
			budget.setStep(16);
			//已完成
			budget.setbStatus(1);
			budget.setpDate(new Date());
			if(budgetRecord.getNote() != null ){
				budget.setpNote(budgetRecord.getNote());
			}
			budget.setpStatus(budgetRecord.getResult());
			budgetDao.update(budget);
		}
		
	}
	
	
	
	/**
	 * case14
	 * @param budget
	 * @param budgetRecord
	 */
	private void case14(Budget budget,BudgetRecord budgetRecord) throws Exception{
		//判断曹宇之前是否审批过
		String userId = isApproval(budgetRecord, UserUtils.USER_CY);
		if(StringUtils.isNotEmpty(userId)){
			case15(budget, budgetRecord);
			return;
		}
		//曹宇审批
		budget.setpId(userId);
		budget.setStep(15);//流程结束
		budgetDao.update(budget);
		
		//添加一条审批记录
		insertBudgetRecord(budgetRecord, UserUtils.getByName(UserUtils.USER_CY).getId());
	}
	
	/**
	 * case15
	 * @param budget
	 * @param budgetRecord
	 */
	private void case15(Budget budget,BudgetRecord budgetRecord) throws Exception{
		budget.setpId(UserUtils.getByName(UserUtils.USER_CY).getId());
		budget.setStep(16);//流程结束
		budget.setbStatus(1);
		budget.setpDate(new Date());
		if(budgetRecord.getNote() != null ){
			budget.setpNote(budgetRecord.getNote());
		}
		budget.setpStatus(budgetRecord.getResult());
		budgetDao.update(budget);
	}
	
	
	/*
	 * 判断用户之前是否审批过此申请
	 */
	protected String isApproval(BudgetRecord budgetRecord,String userName){
		User user = UserUtils.getByName(userName);
		if(user == null ){
			throw new RuntimeException("用户【"+userName+"】不存在,请核实人员信息！");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("bId", budgetRecord.gettId());
		map.put("uId", user.getId());
		budgetRecord = budgetRecordDao.getParam(map);
		if(budgetRecord != null ){
			return user.getId();
		}
		return null;
	}
	
	
	protected void insertBudgetRecord(BudgetRecord budgetRecord,String uId){
		budgetRecord.setId(IdGen.uuid());
		budgetRecord.setuId(uId);
		budgetRecord.setStatus(0);
		budgetRecord.setNote("");
		budgetRecordDao.insert(budgetRecord);
	}
	
	
	/**
	 * 获取请假信息
	 * @param id
	 */
	public BudgetRecord getLeaveInfo(String id){
		return budgetRecordDao.getLeaveById(id);
	}
	
	
	
	
	/**
	 * 请假审批
	 * @param budgetRecord
	 */
	@Transactional(readOnly = false)
	public void updateLeaveRecord(BudgetRecord budgetRecord) throws Exception{
		budgetRecord.setStatus(1);
		//获取流程信息
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
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case_1(approval,budgetRecord);
			break;
		case 2:
			budgetRecordDao.updateBudgetRecord(budgetRecord);
			case_2(approval,budgetRecord);
			break;
		}
		//通过
		
	}
	
	
	
	private void case_1(Approval approval,BudgetRecord budgetRecord){
		
		User _user = UserUtils.get(approval.getuId());
		if(_user == null){
		 throw new RuntimeException("用户不存在！");
		}
		
		//判断该申请人是不是朱念洋
		if(_user.getName().equals(UserUtils.USER_ZNY)){
			case_2( approval, budgetRecord);
			return;
		};
		//设置下一级审批人  朱念洋
		approval.setpId(UserUtils.getByName(UserUtils.USER_ZNY).getId());
		approval.setStep(2);
		approvalDao.update(approval);
		//添加一条审批记录。
		insertBudgetRecord(budgetRecord, UserUtils.getByName(UserUtils.USER_ZNY).getId());
	}
	
	
	
	private void case_2(Approval approval,BudgetRecord budgetRecord){
		approval.setbStatus(1);
		approval.setpDate(new Date());//流程完成
		approval.setpStatus(1);
		approval.setResult(budgetRecord.getResult());
		approvalDao.update(approval);
	}
}
