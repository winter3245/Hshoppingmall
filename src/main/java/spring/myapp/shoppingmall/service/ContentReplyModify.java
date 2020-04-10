package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;

@Service
public class ContentReplyModify {
	@Autowired
	private MallDao Malldao;
	
	public void contentreplymodify(String gId, String rId, String content) 
	{
		Malldao.contentreplymodify(gId,rId,content);
	}
}
