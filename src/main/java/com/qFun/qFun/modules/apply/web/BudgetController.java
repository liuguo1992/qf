package com.qFun.qFun.modules.apply.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.qFun.qFun.common.utils.IdGen;
import com.qFun.qFun.common.utils.StringUtils;
import com.qFun.qFun.common.web.BaseController;
import com.qFun.qFun.modules.apply.entity.Budget;
import com.qFun.qFun.modules.apply.entity.BudgetRecord;
import com.qFun.qFun.modules.apply.entity.ChargeDetail;
import com.qFun.qFun.modules.apply.service.BudgetRecordService;
import com.qFun.qFun.modules.apply.service.BudgetService;
import com.qFun.qFun.modules.apply.service.ChargeDetailService;
import com.qFun.qFun.modules.sys.entity.User;
import com.qFun.qFun.modules.sys.utils.UserUtils;

@Controller
@RequestMapping(value = "${adminPath}/apply/budget")
public class BudgetController  extends BaseController{
	
	@Autowired
	private BudgetService budgetService;
	
	@Autowired
	private BudgetRecordService budgetRecordService;
	
	@Autowired
	private ChargeDetailService chargeDetailService;
	
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
	public String getList(Budget budget,HttpServletRequest request,HttpServletResponse response,Model model){
		//获取当前用户id
		User user = UserUtils.getUser();
		budget.setbId(user.getId());
		Page<Budget> page = budgetService.findPage(new Page<Budget>(request, response), budget);
		model.addAttribute("page", page);
		System.out.println("page-------------"+page.getCount());
		return "modules/apply/budgetList";
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
	public String _getList(BudgetRecord budgetRecord,Budget budget,HttpServletRequest request,HttpServletResponse response,Model model) throws ParseException{
		
		System.out.println("-------------------"+budgetRecord.getStatus());
		
		//获取当前用户id
		User user = UserUtils.getUser();
		budgetRecord.setuId(user.getId());
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
		budgetRecord.setUser(user);
		String startDate = request.getParameter("startDate");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(StringUtils.isNotEmpty(startDate)){
			budget.setStartDate(sdf.parse(startDate));
		}
		String endDate = request.getParameter("endDate");
		if(StringUtils.isNotEmpty(endDate)){
			budget.setEndDate(sdf.parse(endDate));;
		}
		budgetRecord.setBudget(budget);
		Page<BudgetRecord> result = budgetRecordService.findPage(new Page<BudgetRecord>(request, response), budgetRecord);
		model.addAttribute("page", result);
		return "modules/apply/budgetRecordList";
	}
	
	/**
	 * 审批申请跳转
	 * @return
	 */
	@RequiresPermissions("apply:budget:view")
	@RequestMapping(value = "_add")
	public String _addBudget(){
		return "modules/apply/addBudget";
	}
	
	
	/***
	 * 费用报销审批提交
	 * @param budget
	 * @param request
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("apply:budget:edit")
	@RequestMapping(value = "save")
	public String save(ChargeDetail chargeDetail,HttpServletRequest request,HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
		Budget budget = new Budget();
		budget.setbId(IdGen.uuid());
		String bNote = request.getParameter("bNote");
		if(!StringUtils.isEmpty(bNote)){
			budget.setbNote(bNote);
		}
		budget.setType(Integer.valueOf(request.getParameter("type")));
		budget.setCreateBy(UserUtils.getUser());
		budget.setCreateDate(new Date());
		budget.setStartDate(new Date());
		
		try {
			budgetService.saveBudget(budget,chargeDetail);
			addMessage(redirectAttributes, "提交成功！");
		} catch (Exception e) {
			addMessage(redirectAttributes, "提交失败，请重新提交！");
			return "redirect:" + adminPath + "/apply/budget/_add";
		}
		return "redirect:" + adminPath + "/apply/budget/list";
	} 
	
	
	/**
	 * 办理页面跳转
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("apply:budget:edit")
	@RequestMapping(value = "handle")
	public  String handle(@RequestParam("id") String id ,Model model){
		//查询该条申请记录
		model.addAllAttributes(budgetRecordService.getBudgetInfo(id));
		return "modules/apply/budgetHandle";
		
	}
	
	
	
	/**
	 * 费用审批办理
	 * @param budgetRecord
	 * @param request
	 * @param redirectAttributes
	 */
	@RequiresPermissions("apply:budget:edit")
	@RequestMapping(value="doHandle")
	public String doHandle(BudgetRecord budgetRecord,Model model,HttpServletRequest request,RedirectAttributes redirectAttributes){
		budgetRecord.settId(request.getParameter("bId"));
		budgetRecord.setStatus(1);
			try {
				budgetRecordService.updateBudgetRecord(budgetRecord);
				addMessage(redirectAttributes, "操作成功！");
			} catch (Exception e) {
				e.printStackTrace();
				addMessage(redirectAttributes, "操作失败！");
			}
		return "redirect:" + adminPath + "/apply/budget/_list";
	}
	
	
	/**
	 * 刪除審批記錄
	 * @param budgetRecord
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("apply:budget:edit")
	@RequestMapping(value = "delete")
	public String delete(BudgetRecord budgetRecord,RedirectAttributes redirectAttributes){
		//邏輯刪除
		budgetRecord.setIsDel(1);
		budgetRecordService.delete(budgetRecord);
		addMessage(redirectAttributes, "記錄刪除成功！");
		return "redirect:" + adminPath + "/apply/budget/_list";
		
	}
	
	
	/**
	 * 表单打印跳转
	 * @param budget
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "printForm")
	public String printForm(Budget budget ,Model model,HttpServletRequest request){
		Map<String,Object> map = budgetService.getBudgetInfo(budget);
		model.addAllAttributes(map);
		return "modules/apply/budgetForm";
	}
}
