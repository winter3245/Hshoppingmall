package spring.myapp.shoppingmall.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import spring.myapp.shoppingmall.dto.User;

@Repository
public class UserDao {
private JdbcTemplate template;
	
	@Inject
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserDao(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}
	
	public User getUser(String Id) {	//회원 정보 가져오기
		String sql = "select * from user where Id = ?";
		User user;
		
		user = template.queryForObject(sql,new Object[] {Id},new RowMapper<User>() {
			public User mapRow(ResultSet rs,int RowNum) throws SQLException{
				User user = new User();
				user.setId(rs.getString("Id"));
				user.setAddress(rs.getString("address"));
				user.setPhoneNumber(rs.getString("phonenumber"));
				user.setName(rs.getString("name"));
				
				return user;
			}
		});
		
		return user;
	}
	
	public void join(String Id,String Password,String Name,String Address,String Sex,int Age,String PhoneNumber,String email) {
		String sql = "insert into user (Id,Password,Name,Address,Sex,Age,PhoneNumber,email,Authorities,Enabled) values(?,?,?,?,?,?,?,?,'ROLE_USER',1)";
		template.update(sql,new Object[] {Id,Password,Name,Address,Sex,Age,PhoneNumber,email});
	}
	
	public boolean authId(String e_mail,String phoneNumber,String name)
	{
		String sql = "select * from user where name = ? and phonenumber = ? and email = ?";
		User user;
		try {
		user = template.queryForObject(sql,new Object[] {name,phoneNumber,e_mail},new RowMapper<User>() {
			public User mapRow(ResultSet rs,int rowNum) throws SQLException
			{
				User user = new User();
				user.setEmail(rs.getString("email"));
				
				return user;
			}
		});
			return true;
		}
		catch(EmptyResultDataAccessException e)
		{
			return false;
		}
		/*
		if(user.getUseremail().equals(e_mail))
			return true;
		else
			return false;
		*/
	}
	
	public boolean authPw(String cId,String e_mail,String phoneNumber,String name)
	{
		String sql = "select * from user where id = ? and name = ? and email = ? and phonenumber = ?";
		User user;
		
		try {
		user = template.queryForObject(sql,new Object[] {cId,name,e_mail,phoneNumber},new RowMapper<User>() {
			public User mapRow(ResultSet rs,int rowNum) throws SQLException
			{
				User user = new User();
				user.setEmail(rs.getString("email"));
				
				return user;
			}
		});
			return true;
		}
		catch(EmptyResultDataAccessException e)
		{
			return false;
		}
		/*
		if(user.getUseremail().equals(e_mail))
			return true;
		else
			return false;
		*/
	}
	
	public User findId(String name,String phoneNumber)
	{
		String sql = "select * from user where name = ? and phonenumber = ?";
		User user;
		
		try {
		  user = template.queryForObject(sql,new Object[] {name,phoneNumber},new RowMapper<User>() {
			  public User mapRow(ResultSet rs,int rowNum) throws SQLException
			  {
				  User user = new User();
				  user.setId(rs.getString("Id"));
				  
				  return user;
			  }
		  });
		}catch(Exception e)
		{
			return null;
		}
		
		System.out.println("UserId: "+user.getId());
		return user;
	}
	
	public User findPw(String cId)
	{
		String sql = "select * from user where Id = ?";
		User user;
		
		try {
		  user = template.queryForObject(sql,new Object[] {cId},new RowMapper<User>() {
			  public User mapRow(ResultSet rs,int rowNum) throws SQLException
			  {
				  User user = new User();
				  //String cPw = rs.getString("cPw").substring(6);
				  //String cId = rs.getString("cId");
				  //user.setUserpassword(cPw);
				  user.setId(rs.getString("Id"));
				  
				  return user;
			  }
		  });
		}catch(Exception e)
		{
			return null;
		}
		
		return user;
	}
	public void repassword(String userId,String repassword)
	{
		String sql = "update user set Password = ? where Id = ?";
		String encPassword = passwordEncoder.encode(repassword);
		template.update(sql,new Object[] {encPassword,userId});
	}
	
	public int check(String id)
	{
		String sql = "select * from user where Id = ?";
		List<User> list = null;
		
		list = template.query(sql,new Object[] {id},new RowMapper<User>() {
			public User mapRow(ResultSet rs,int rowNum) throws SQLException
			{
				User user = new User();
				user.setId(rs.getString("Id"));
				return user;
			}
		});
		
		if(list != null)
			return list.size();
		else
			return 0;
	}
	
	public boolean checkNowPassword(String Userid,String Password)
	{
		String sql = "select * from user where Id = ?";
		//Password = "{noop}"+Password;
		
		User user = template.queryForObject(sql,new Object[] {Userid},new RowMapper<User>() {
			public User mapRow(ResultSet rs,int rowNum) throws SQLException
			{
				User user = new User();
				user.setPassword((rs.getString("Password")));
				
				return user;
			}
		});
		//if(user.getUserpassword().equals(Password))
		if(passwordEncoder.matches(Password, user.getPassword()))
			return true;
		else
			return false;
	}
	
	public void updatePassword(String Userid,String Password)
	{
		String sql = "update user set Password = ? where Id = ?";
		//Password = "{noop}"+Password;
		template.update(sql,new Object[] {Password,Userid});
	}
}
