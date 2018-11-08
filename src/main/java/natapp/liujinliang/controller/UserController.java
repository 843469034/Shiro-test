package natapp.liujinliang.controller;

import natapp.liujinliang.vo.User;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
	
	@RequestMapping("/test.do")
	@ResponseBody
	public String test() {
		return "test";
	}
	
	@RequestMapping(
			value="/subLogin", 
			method=RequestMethod.POST,
			produces="application/json;charset=utf-8")
	@ResponseBody
	public String subLogin(User user) {
		
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
		
		try {
			subject.login(token);
		} catch (Exception e) {
			return e.getMessage();
		}
		
		// 编码方式判断是否具有管理员身份
		if (subject.hasRole("admin")) {
			return "有admin权限";
		}
		
		return "无admin权限";
	}
	
	@RequiresRoles("admin")
	@RequestMapping(value="/testRole", method=RequestMethod.GET)
	@ResponseBody
	public String testRole() {
		return "test role success";
	}
	
	@RequestMapping(value="/testRoles", method=RequestMethod.GET)
	@ResponseBody
	public String testRoles() {
		return "test roles success";
	}
	
	@RequestMapping(value="/testRoles1", method=RequestMethod.GET)
	@ResponseBody
	public String testRoles1() {
		return "test roles1 success";
	}
}
