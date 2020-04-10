package spring.myapp.shoppingmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;
import spring.myapp.shoppingmall.dto.goods;

@Service
public class GetSearchInfo 
{
	@Autowired
	private MallDao Malldao;
	
	public List<goods> getsearch(int curPageNum,String search){
		return Malldao.getsearchinfo(curPageNum,search);
	}
}
