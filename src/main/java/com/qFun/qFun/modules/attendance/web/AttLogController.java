package com.qFun.qFun.modules.attendance.web;




import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.qFun.qFun.common.persistence.Page;
import com.qFun.qFun.common.utils.DateUtils;
import com.qFun.qFun.common.utils.StringUtils;
import com.qFun.qFun.common.utils.excel.ExportExcel;
import com.qFun.qFun.common.web.BaseController;
import com.qFun.qFun.modules.attendance.entity.DayAtt;
import com.qFun.qFun.modules.attendance.entity.MonthAtt;
import com.qFun.qFun.modules.attendance.service.AttLogService;
import com.qFun.qFun.modules.attendance.service.DayAttService;
import com.qFun.qFun.modules.attendance.service.MonthAttService;
import com.qFun.qFun.modules.sys.dao.UserDao;
import com.qFun.qFun.modules.sys.entity.User;
import com.qFun.qFun.modules.sys.utils.UserUtils;


@Controller
@RequestMapping(value = "${adminPath}/attendance/attLog")
public class AttLogController extends BaseController{
	
	
	@Autowired
	private AttLogService attLogService;
	
	@Autowired
	private DayAttService dayAttService;
	
	@Autowired
	private MonthAttService monthAttService;
	
	@Autowired
	private UserDao userDao;
	
	
	
	
	
	
	/*
	 * 打卡记录存入数据库
	 */
	@RequestMapping("saveAttLog")
	public void saveAttLogs(){
		try {
			attLogService.saveAttLog();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	@RequiresPermissions("attendance:attLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(DayAtt dayAtt, HttpServletRequest request, HttpServletResponse response, Model model) {
		//获取当前用户
		User user = UserUtils.getUser();
		if(user == null ) throw new RuntimeException("当前用户不存在");
		dayAtt.setuId(user.getId());
		Page<DayAtt> page = dayAttService.findPage(new Page<DayAtt>(request, response), dayAtt);
        model.addAttribute("page", page);
		return "modules/attendance/attList";
		
		
	}
	
	@RequiresPermissions("attendance:attLog:view")
	@RequestMapping(value = {"_list", ""})
	public String _list(MonthAtt monthAtt,User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		//获取当前用户
		/*User user = UserUtils.getUser();
		if(user == null ) throw new RuntimeException("当前用户不存在");
		
		*/
		String name = request.getParameter("name");
		if(StringUtils.isNoneEmpty(name)){
			user.setName(name);
		}
		String loginName  = request.getParameter("loginName");
		if(StringUtils.isNoneEmpty(loginName)){
			user.setLoginName(loginName);
		}
		String enrollNumber = request.getParameter("enrollNumber");
		if(StringUtils.isNotEmpty(enrollNumber)){
			user.setEnrollNumber(Integer.valueOf(enrollNumber));
		}
		monthAtt.setUser(user);
		/*if(user != null){
			List<User> result = userDao.getUserByParams(user);
			if(result.size() > 0){
				user = result.get(0);
				monthAtt.setuId(user.getId());
			}
		}*/
		Page<MonthAtt> page = monthAttService.findPage(new Page<MonthAtt>(request, response), monthAtt);
        model.addAttribute("page", page);
		return "modules/attendance/userAttList";
		
	}
	
	
	
	/**
	 * 导出个人考勤数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("attendance:attLog:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DayAtt dayAtt,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "考勤数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            User user = UserUtils.getUser();
    		if(user == null ) throw new RuntimeException("当前用户不存在");
    		dayAtt.setuId(user.getId());
    		Page<DayAtt> page = dayAttService.findPage(new Page<DayAtt>(request, response), dayAtt);
    		new ExportExcel("考勤数据", DayAtt.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/attendance/attLog/list";
    }


	

	/**
	 * 导出用户考勤数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("attendance:attLog:view")
    @RequestMapping(value = "export_", method=RequestMethod.POST)
    public String exportFile_(MonthAtt monthAtt,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "考勤数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            User user = UserUtils.getUser();
    		if(user == null ) throw new RuntimeException("当前用户不存在");
    		monthAtt.setuId(user.getId());
    		Page<MonthAtt> page = monthAttService.findPage(new Page<MonthAtt>(request, response), monthAtt);
    		new ExportExcel("考勤数据", MonthAtt.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/attendance/attLog/_list";
    }

}
