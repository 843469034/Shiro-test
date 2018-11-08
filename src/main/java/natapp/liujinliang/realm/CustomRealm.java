package natapp.liujinliang.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import natapp.liujinliang.dao.UserDao;
import natapp.liujinliang.vo.User;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class CustomRealm extends AuthorizingRealm {
	
	@Resource
	private UserDao userDao;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		
		String username = (String) principals.getPrimaryPrincipal();
		
		// 从数据库或者缓存中获取角色数据
		Set<String> roles = getRolesByUserName(username);
		
		// 从数据库或者缓存中获取权限数据
		Set<String> permissions = getPermissionsByUserName(username);
		
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(roles);
		authorizationInfo.setStringPermissions(permissions);
		
		return authorizationInfo;
	}

	private Set<String> getPermissionsByUserName(String username) {
		Set<String> sets = new HashSet<String>();
		sets.add("user:add");
		sets.add("user:delete");
		sets.add("user:post");
		sets.add("user:query");
		return sets;
	}

	private Set<String> getRolesByUserName(String username) {
		List<String> list = userDao.getRolesByUserName(username);
		Set<String> sets = new HashSet<String>(list);
		return sets;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
	
		// 1、从主体传过来的认证信息中获取用户名称
		String username = (String) token.getPrincipal();
		
		// 2、通过用户名到数据库中获取凭证信息
		String password = getPasswordByUsername(username);
		
		if (password == null) {
			return null;
		}
		
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, "customRealm");
		authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("liujinliang")); // 加盐值
		return authenticationInfo;
	}

	private String getPasswordByUsername(String username) {
		User user = userDao.getUserByUsername(username);
		if (user != null) {
			return user.getPassword();
		}
		return null;
	}

	public static void main(String[] args) {
		Md5Hash md5Hash = new Md5Hash("111111", "liujinliang");
		System.out.println(md5Hash);
	}
}
