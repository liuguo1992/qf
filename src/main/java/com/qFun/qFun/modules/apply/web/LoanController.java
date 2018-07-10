package com.qFun.qFun.modules.apply.web;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qFun.qFun.common.persistence.Page; 
import com.qFun.qFun.common.utils.StringUtils;
import com.qFun.qFun.common.web.BaseController;
import com.qFun.qFun.modules.apply.entity.Approval;
import com.qFun.qFun.modules.apply.entity.BudgetRecord;
import com.qFun.qFun.modules.apply.entity.Loan;
import com.qFun.qFun.modules.apply.service.ContractService;
import com.qFun.qFun.modules.apply.service.LoanService;
import com.qFun.qFun.modules.sys.entity.User;
import com.qFun.qFun.modules.sys.utils.UserUtils;


/**
 * 借款申请
 * @author lg
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/apply/loan")
public class LoanController extends BaseController{
	
	@Autowired
	private LoanService loanService;
	
	
	@Autowired
	private ContractService contractServcie;
	
	
	
	
	/**
	 * 获取所有我提交的审批
	 * @param budget
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("apply:budget:view")
	@RequestMapping(value = "list")
	public String getList(String type,Loan loan,HttpServletRequest request,HttpServletResponse response,Model model){
		Integer _type = Integer.valueOf(type);
		//获取当前用户id
		User user = UserUtils.getUser();
		loan.setuId(user.getId());
		loan.setType(_type);//  1 费用报销审批 2 请假审批 3 借款审批 4 费用预算
		Page<Loan> page = loanService.findPage(new Page<Loan>(request, response), loan);
		model.addAttribute("page", page);
		if(_type == 3){
			return "modules/apply/loanList";//借款
		}
		if(_type == 4){
			return "modules/apply/contractList";   //合同费用预算
		}
		return null;
	}

	/**
	 * 获取所有由我审批的列表
	 * @param budget
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException 
	 */
	@RequiresPermissions("apply:budget:view")
	@RequestMapping(value = "_list")
	public String _getList(String type,Approval approval,Loan loan,BudgetRecord budgetRecord,HttpServletRequest request,HttpServletResponse response,Model model) throws ParseException{
		
		//获取当前用户id
		User user = UserUtils.getUser();
		loan.setuId(user.getId());
		loan.setType(Integer.valueOf(type));
		user.setLoginName(null);
		user.setName(null);
		String loginName = request.getParameter("loginName");
		if(StringUtils.isNotEmpty(loginName)){
			user.setLoginName(loginName);
		}
		String name = request.getParameter("name");
		if(StringUtils.isNotEmpty(name)){
			user.setName(name);
		}
		loan.setUser(user);
		String startDate = request.getParameter("startDate");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(StringUtils.isNotEmpty(startDate)){
			approval.setStartDate(sdf.parse(startDate));
		}
		String endDate = request.getParameter("endDate");
		if(StringUtils.isNotEmpty(endDate)){
			approval.setEndDate(sdf.parse(endDate));;
		}
		loan.setApproval(approval);
		String status = request.getParameter("status");
		if(StringUtils.isNotEmpty(status)){
			budgetRecord.setStatus(Integer.valueOf(status));
		}
		loan.setBudgetRecord(budgetRecord);
		Page<Loan> result = loanService.getPage(new Page<Loan>(request, response), loan);
		model.addAttribute("page", result);
		if(type.equals("3")){
			return "modules/apply/loanRecordList";
		}
		if(type.equals("4")){
			return "modules/apply/contractRecordList";
		}
		return null;
		
	}
	
	/**
	 * 审批申请跳转
	 * @return
	 */
	@RequiresPermissions("apply:budget:view")
	@RequestMapping(value = "_add")
	public String _addLoan(String type){
		Integer _type = Integer.valueOf(type);
		if(_type == 3){
			return "modules/apply/addLoan";
		}
		if(_type == 4){
			return "modules/apply/addContract";
		}
		return null;
	}
	
	/**
	 *添加申请
	 * @return
	 */
	@RequiresPermissions("apply:budget:view")
	@RequestMapping(value = "save")
	public String save(Loan loan,HttpServletRequest request,HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
		try {
			loanService.saveLoan(loan);
			addMessage(redirectAttributes, "提交成功！");
		} catch (Exception e) {
			addMessage(redirectAttributes, e.getMessage());
			return "redirect:" + adminPath + "/apply/loan/_add?type=3";
		}
		return "redirect:" + adminPath + "/apply/loan/list?type=3";
	}
	
	
	
	
	
	@RequiresPermissions("apply:budget:view")
	@RequestMapping(value = "saveContract")
	public String saveContract(Loan loan,HttpServletRequest request,HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
		try {
			contractServcie.saveContract(loan);
			addMessage(redirectAttributes, "提交成功！");
		} catch (Exception e) {
			addMessage(redirectAttributes, e.getMessage());
			return "redirect:" + adminPath + "/apply/loan/_add?type=4";
		}
		return "redirect:" + adminPath + "/apply/loan/list?type=4";
	}
	
	/**
	 * 借款审批跳转
	 * @return
	 */
	@RequiresPermissions("apply:budget:edit")
	@RequestMapping(value = "handle")
	public String handle(@RequestParam("id")String id,Model model){
		
		//获取借款相关信息
		try {
			model.addAllAttributes(loanService.getLoanInfo(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "modules/apply/loanHandle";
	}
	
	/**
	 * 预算审批跳转
	 * @return
	 */
	@RequiresPermissions("apply:budget:edit")
	@RequestMapping(value = "contractHandle")
	public String contractHandle(@RequestParam("id")String id,Model model){
		
		//获取借款相关信息
		try {
			model.addAllAttributes(loanService.getLoanInfo(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "modules/apply/contractHandle";
	}
	
	
	/**
	 * 借款审批办理
	 * @param budgetRecord
	 * @param request
	 * @param redirectAttributes
	 */
	@RequiresPermissions("apply:budget:edit")
	@RequestMapping(value="doHandle")
	public String doHandle(BudgetRecord budgetRecord,Model model,HttpServletRequest request,RedirectAttributes redirectAttributes){
			try {
				loanService.updateLoanRecord(budgetRecord);
				addMessage(redirectAttributes, "操作成功！");
			} catch (Exception e) {
				e.printStackTrace();
				addMessage(redirectAttributes, "操作失败！");
			}
		return "redirect:" + adminPath + "/apply/loan/_list?type=3";
	}
	
	
	/**
	 * 预算审批办理
	 * @param budgetRecord
	 * @param request
	 * @param redirectAttributes
	 */
	@RequiresPermissions("apply:budget:edit")
	@RequestMapping(value="doContractHandle")
	public String doContractHandle(BudgetRecord budgetRecord,Model model,HttpServletRequest request,RedirectAttributes redirectAttributes){
			try {
				System.out.println("=====================控制器");
				contractServcie.updateContractRecord(budgetRecord);
				addMessage(redirectAttributes, "操作成功！");
			} catch (Exception e) {
				e.printStackTrace();
				addMessage(redirectAttributes, e.getMessage());
				//addMessage(redirectAttributes, "操作失败！");
			}
		return "redirect:" + adminPath + "/apply/loan/_list?type=4";
	}
	
	
	/**
	 * 表单打印跳转
	 * @param budget
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "printForm")
	public String printForm(String id,Loan loan ,Model model,HttpServletRequest request){
		Map<String, Object> map = null;
		try {
			map = loanService.getLoanFormInfo(loan);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAllAttributes(map);
		return "modules/apply/loanForm";
	}
	
	

	/**
	 * 表单打印跳转
	 * @param budget
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "printContractForm")
	public String printContractForm(String id,Loan loan ,Model model,HttpServletRequest request){
		Map<String, Object> map = null;
		try {
			map = loanService.getLoanFormInfo(loan);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAllAttributes(map);
		return "modules/apply/contractForm";
	}
}
