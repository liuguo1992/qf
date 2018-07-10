package com.qFun.qFun.common.dingding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.qFun.qFun.common.utils.JsonHelp;
import com.qFun.qFun.common.utils.PropertiesFileUtil;
import com.qFun.qFun.common.utils.PropertiesLoader;

public class DDtalkUtil {
	
	
	PropertiesLoader propertiesLoader;
	//加载配置文件
	//corpid
	public static final String CORPID = PropertiesFileUtil.getInstance("qFun").get("CORPID");
	//corpsecret
	public static final String CORPSECRET = PropertiesFileUtil.getInstance("qFun").get("CORPSECRET");
	public static final String ACCESS_TOKEN_URL = PropertiesFileUtil.getInstance("qFun").get("ACCESS_TOKEN_URL");
	
	
	//获取access_token
	public static AccessToken getAccessToken() throws ClientProtocolException,IOException{
		AccessToken accessToken = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("CORPID", CORPID).replace("CORPSECRET", CORPSECRET);
		JSONObject jsonObject = doGetStr(url);
		if(jsonObject != null ){
			accessToken.setErrcode(jsonObject.getInt("errcord"));
			accessToken.setErrmsg(jsonObject.getString("errmsg"));
			accessToken.setAccessToken(jsonObject.getString("access_token"));
		}
		return accessToken;
	}
	 
		
	/**
	 * 获取json数据
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static JSONObject doGetStr(String url) throws ClientProtocolException,IOException{
		@SuppressWarnings("deprecation")
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		HttpResponse response = client.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if(entity != null){
			String result =EntityUtils.toString(entity,"utf-8");
			jsonObject= JSONObject.fromObject(result);
		}
		return jsonObject;
		
	}
	
	
	/**
	 * 获取部门信息 
	 * @param appId
	 * @param secrect
	 * @return
	 */
	public DepartmentResult getDepartmentInfo(){
		DepartmentResult departmentResult = null;
		try {
			String token = getAccessToken().getAccessToken();
			String url = "https://oapi.dingtalk.com/department/list?access_token=" + token;
			JSONObject jsonObject = doGetStr(url);
			departmentResult = (DepartmentResult)JsonHelp.JSONToObj(jsonObject.toString(), DepartmentResult.class);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return departmentResult;
		
		
	}
	
	/**
	 * 获取企业下的所有员工。
	 * @return
	 */
	public List<DUser> GetUserInfoList(){
		try {
			String token = getAccessToken().getAccessToken();
			DepartmentResult departmentResult = getDepartmentInfo();
			if(departmentResult.getErrcode() != 0 ){
				return null;
			}
			//List<UserList> list = new ArrayList<UserList>();
			for (Department department: departmentResult.getDepartment()) {
				//根据部门ID获取部门人员详情
				String url = "https://oapi.dingtalk.com/user/list?access_token=" + token + "&department_id=" + department.getId();
				JSONObject jsonObject = doGetStr(url);
				
				UserList userResult = (UserList) JsonHelp.JSONToObj(jsonObject.toString(), UserList.class);
				
				if (userResult.getErrcode() == 0 && userResult.getUserList() != null && userResult.getUserList().size() > 0)
				{
					return userResult.getUserList();//添加人员信息到集合中
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	 
	
	
		
}
