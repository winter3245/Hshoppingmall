package spring.myapp.shoppingmall.admin;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import spring.myapp.shoppingmall.dto.Refund;
import spring.myapp.shoppingmall.service.AdminRegisterGoods;
import spring.myapp.shoppingmall.service.GetRefundInfo;

@Controller
@RequestMapping(value = "/admin")
public class AdminController 
{
	@Autowired
	private AdminRegisterGoods registergoods;
	
	@Autowired
	private GetRefundInfo getrefundinfo;
	
	@RequestMapping(value = "/registerGoods",method = RequestMethod.POST)
	public String registerGoods(@RequestParam String thumbnail,@RequestParam String name,@RequestParam String price,@RequestParam String kind
			,@RequestParam String remain,@RequestParam String content,@RequestParam(value = "writer",required = false) String writer,
			@RequestParam(value = "wcompany",required = false) String wcompany,@RequestParam(value = "tcontent",required = false) String tcontent) 
	{
		registergoods.register(thumbnail,name,Integer.valueOf(price),kind,Integer.valueOf(remain),content,writer,wcompany,tcontent);
		return "redirect:/admin/registerForm";
	}
	
	@RequestMapping("/registerForm")
	public String registerForm() {
		return "adminregistergoods";
	}
	
	@RequestMapping("/adminrefund")
	public String adminrefund() {
		return "adminrefund";
	}
	
	@RequestMapping(value = "/findrefund",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject findrefund(@RequestBody HashMap<String,Object> map)
	{
		Refund refund = getrefundinfo.getrefund((String)map.get("orderid"));
		if(refund != null) {
			Integer amount = refund.getAmount();
			String refundholder = refund.getRefundholder();
			String refundbank = refund.getRefundbank();
			String refundaccount = refund.getRefundaccount();
		
			JSONObject json = new JSONObject();
			json.put("amount", amount);
			json.put("holder",refundholder);
			json.put("bank",refundbank);
			json.put("account",refundaccount);
			
			return json;
		} else {
			return new JSONObject();
		}
	}
}
