package spring.myapp.shoppingmall.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController 
{
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request,Model model)
	{
		PagingListModel();
		return "/board/list";
	}
	
	private void PagingListModel()
	{
		
	}
}
