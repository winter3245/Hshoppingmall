package spring.myapp.shoppingmall.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

@Repository
public class BoardDao 
{
	private JdbcTemplate template;
	@Autowired
	private TransactionTemplate transactionTemplate1;
	@Autowired
	private TransactionTemplate transactionTemplate2;
	
	@Autowired
	public BoardDao(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}
	
}
