package com.qFun.qFun.modules.apply.web;


import java.text.ParseException;
import java.text.SimpleDateFormat;

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
import com.qFun.qFun.modules.apply.entity.LeaveApproval;
import com.qFun.qFun.modules.apply.service.ApprovalService;
import com.qFun.qFun.modules.apply.service.BudgetRecordService;
import com.qFun.qFun.modules.apply.service.LeaveApprovalService;
import com.qFun.qFun.modules.sys.entity.User;
import com.qFun.qFun.modules.sys.utils.UserUtils;


@Controller
@RequestMapping(value = "${adminPath}/apply/leave")
public class LeaveController extends BaseController{
	
	@Autowired
	private	ApprovalService approvalService;
	
	@Autowired
	private	LeaveApprovalService leaveApprovalService;
	
	@Autowired
	private BudgetRecordService  budgetRecordService;
	
	
	
	/**
	 * 获取我提交的申请列表
	 * @param approval
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresPermissions("apply:budget:view")
	@RequestMapping(value = "list")
	public String getList(Approval approval,Model model,HttpServletRequest request,HttpServletResponse response){
		//获取当前用户信息
		User user= UserUtils.getUser();
		approval.setuId(user.getId());
		Page<Approval> page = approvalService.findPage(new Page<Approval>(request, response), approval);
		model.addAttribute("page", page);
		return "modules/apply/approvalLeaveList";
		
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
	public String _getList(BudgetRecord budgetRecord,Approval approval,HttpServletRequest request,HttpServletResponse response,Model model) throws ParseException{
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
			approval.setStartDate(sdf.parse(startDate));
		}
		String endDate = request.getParameter("endDate");
		if(StringUtils.isNotEmpty(endDate)){
			approval.setEndDate(sdf.parse(endDate));;
		}
		budgetRecord.setApproval(approval);
		Page<BudgetRecord> page = budgetRecordService.getPage(new Page<BudgetRecord>(request, response), budgetRecord);
		model.addAttribute("page", page);
		return "modules/apply/leaveRecordList";
	}
	
	
	/**
	 * 审批申请跳转
	 * @return
	 */
	@RequiresPermissions("apply:budget:edit")
	@RequestMapping(value = "_add")
	public String addLeave(){
		return "modules/apply/addLeave";
	}

	
	/***
	 * 请假审批提交
	 * @param budget
	 * @param request
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("apply:budget:edit")
	@RequestMapping(value = "save")
	public String save(LeaveApproval leaveApproval,HttpServletRequest request,HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
		
		try {
			leaveApprovalService.saveLeaveApproval(leaveApproval);
			addMessage(redirectAttributes, "提交成功！");
		} catch (Exception e) {
			addMessage(redirectAttributes, e.getMessage());
			return "redirect:" + adminPath + "/apply/leave/_add";
		}
		return "redirect:" + adminPath + "/apply/leave/list";
	} 
	
	
	/**
	 * 请假审批跳转
	 * @return
	 */
	@RequiresPermissions("apply:budget:edit")
	@RequestMapping(value = "handle")
	public String handle(@RequestParam("id")String id,Model model){
		//获取请假相关信息
		model.addAttribute("budgetRecord", budgetRecordService.getLeaveInfo(id));
		return "modules/apply/leaveHandle";
	}
	
	
	/**
	 * 请假审批办理
	 * @param budgetRecord
	 * @param request
	 * @param redirectAttributes
	 */
	@RequiresPermissions("apply:budget:edit")
	@RequestMapping(value="doHandle")
	public String doHandle(BudgetRecord budgetRecord,Model model,HttpServletRequest request,RedirectAttributes redirectAttributes){
			try {
				budgetRecordService.updateLeaveRecord(budgetRecord);
				addMessage(redirectAttributes, "操作成功！");
			} catch (Exception e) {
				e.printStackTrace();
				addMessage(redirectAttributes, "操作失败！");
			}
		return "redirect:" + adminPath + "/apply/leave/_list";
	}
	
}
