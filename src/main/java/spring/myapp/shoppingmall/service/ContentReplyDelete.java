package spring.myapp.shoppingmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.MallDao;

@Service
public class ContentReplyDelete {
	@Autowired
	private MallDao Malldao;
	
	public void contentreplydelete(String gId, String rId) 
	{
		Malldao.contentreplydelete(gId,rId);
	}

}
