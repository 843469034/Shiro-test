package natapp.liujinliang.dao;

import java.util.List;

import natapp.liujinliang.vo.User;

public interface UserDao {

	User getUserByUsername(String username);

	List<String> getRolesByUserName(String username);

}
