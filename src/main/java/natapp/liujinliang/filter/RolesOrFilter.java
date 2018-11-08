package natapp.liujinliang.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

public class RolesOrFilter extends AuthorizationFilter {
	protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
		Subject subject = getSubject(servletRequest,servletResponse);
		String[] roles = (String[]) o;
		if(roles==null || roles.length==0){
			return  true;
		}
		for (String role : roles){
			if(subject.hasRole(role)){
				return true;
			}

		}


		return false;
	}
/*
	@Override
	protected boolean isAccessAllowed(ServletRequest req,
			ServletResponse resp, Object object) throws Exception {
		
		Subject subject = getSubject(req, resp);
		
		String[] roles = (String[]) object;
		
		if (roles == null || roles.length == 0) {
			return true;
		}
		
		for (String role : roles) {
			if (subject.hasRole(role)) {
				return true;
			}
		}
		
		return false;
	}*/

}
