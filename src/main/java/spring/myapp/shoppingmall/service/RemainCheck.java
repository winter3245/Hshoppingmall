package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;

@Service
public class RemainCheck 
{
	@Autowired
	private MallDao Malldao;
	
	public int remaincheck(String[] newname,Integer[] newqty)
	{
		return Malldao.remaincheck(newname,newqty);
	}
}
