package spring.myapp.shoppingmall.service.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.myapp.shoppingmall.dao.BoardDao;

@Service
public class ContentViewService {
	@Autowired
	private BoardDao Boarddao;

	
}
