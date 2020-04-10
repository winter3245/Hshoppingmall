package spring.myapp.shoppingmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;
import spring.myapp.shoppingmall.dto.Reply;

@Service
public class CommentList 
{
	@Autowired
	private MallDao Malldao;
	
	public List<Reply> commentlist(String gId)
	{
		return Malldao.commentlist(gId);
	}
}
