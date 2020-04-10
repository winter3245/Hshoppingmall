package spring.myapp.shoppingmall.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import spring.myapp.shoppingmall.dto.Coupon;
import spring.myapp.shoppingmall.dto.Order;
import spring.myapp.shoppingmall.dto.Ordergoods;
import spring.myapp.shoppingmall.dto.Reply;
import spring.myapp.shoppingmall.dto.User;
import spring.myapp.shoppingmall.dto.Vbank;
import spring.myapp.shoppingmall.dto.goods;
import spring.myapp.shoppingmall.paging.Paging;
import spring.myapp.shoppingmall.service.AddComment;
import spring.myapp.shoppingmall.service.CartSpace;
import spring.myapp.shoppingmall.service.CommentList;
import spring.myapp.shoppingmall.service.ContentReplyDelete;
import spring.myapp.shoppingmall.service.ContentReplyModify;
import spring.myapp.shoppingmall.service.DeleteAllShoppingmall;
import spring.myapp.shoppingmall.service.DeleteMerchantid;
import spring.myapp.shoppingmall.service.DeleteShoppingBasket;
import spring.myapp.shoppingmall.service.FindImpUid;
import spring.myapp.shoppingmall.service.FindJeongbo;
import spring.myapp.shoppingmall.service.FindMerchantid;
import spring.myapp.shoppingmall.service.FindPrice;
import spring.myapp.shoppingmall.service.GetCoupons;
import spring.myapp.shoppingmall.service.GetGoodsInfo;
import spring.myapp.shoppingmall.service.GetOrderGoods;
import spring.myapp.shoppingmall.service.GetOrderInfo;
import spring.myapp.shoppingmall.service.GetProductDetails;
import spring.myapp.shoppingmall.service.GetShoppingBasket;
import spring.myapp.shoppingmall.service.GetVbankInfo;
import spring.myapp.shoppingmall.service.Goodsprofileupload;
import spring.myapp.shoppingmall.service.InsertDbPrice;
import spring.myapp.shoppingmall.service.InsertGoods;
import spring.myapp.shoppingmall.service.InsertVbank;
import spring.myapp.shoppingmall.service.MerChantId;
import spring.myapp.shoppingmall.service.MobileMerchantuidCheck;
import spring.myapp.shoppingmall.service.OrderProduct;
import spring.myapp.shoppingmall.service.ReceiveCoupon;
import spring.myapp.shoppingmall.service.RemainCheck;
import spring.myapp.shoppingmall.service.RequestRefund;
import spring.myapp.shoppingmall.service.SetShoppingBasket;
import spring.myapp.shoppingmall.service.ShoppinglistService;
import spring.myapp.shoppingmall.service.UpdateDiscountPercent;
import spring.myapp.shoppingmall.service.UpdateStatus;
import spring.myapp.shoppingmall.service.UpdateStatusCancel;
import spring.myapp.shoppingmall.service.UpdateUseCouponService;
import spring.myapp.shoppingmall.service.UseCouponService;

@Controller
public class MallController 
{
	@Autowired
	private ShoppinglistService shoppinglistservice;
	
	@Autowired
	private Goodsprofileupload goodsimgupload; //��ǰ �̹��� ���ε�
	
	@Autowired
	private GetGoodsInfo getgoodsinfo;
	
	@Autowired
	private GetProductDetails getproductdetails;
	
	@Autowired
	private SetShoppingBasket setshoppingbasket;
	
	@Autowired
	private GetShoppingBasket getshoppingbasket;
	
	@Autowired
	private DeleteShoppingBasket dshoppingbasket;
	
	@Autowired
	private OrderProduct orderproduct;
	
	@Autowired
	private MerChantId merchantid;
	
	@Autowired
	private InsertDbPrice insertprice;
	
	@Autowired
	private FindPrice findprice;
	
	@Autowired
	private UpdateStatus updatestatus;
	
	@Autowired
	private FindMerchantid findmerchantid;
	
	@Autowired
	private FindImpUid findimpuid;
	
	@Autowired
	private DeleteMerchantid deletemerchantid;
	
	@Autowired
	private UpdateStatusCancel updatestatuscancel;
	
	@Autowired
	private InsertVbank insertvbank;
	
	@Autowired
	private InsertGoods insertgoods;
	
	@Autowired
	private GetOrderInfo getorderinfo;
	
	@Autowired
	private GetOrderGoods getordergoods;
	
	@Autowired
	private GetVbankInfo getvbankinfo;
	
	@Autowired
	private RequestRefund requestrefund;
	
	@Autowired
	private RemainCheck remaincheck;
	
	@Autowired
	private CartSpace cartspace;
	
	@Autowired
	private Paging paging;
	
	@Autowired
	private GetCoupons getcoupons;
	
	@Autowired
	private UseCouponService usecouponservice;
	
	@Autowired
	private ReceiveCoupon receivecoupon;
	
	@Autowired
	private UpdateDiscountPercent updatediscountpercent;
	
	@Autowired
	private UpdateUseCouponService updateusecouponservice;
	
	@Autowired
	private FindJeongbo findjeongbo;
	
	@Autowired
	private DeleteAllShoppingmall deleteallshoppingmall;
	
	@Autowired
	private CommentList commentlist;
	
	@Autowired
	private AddComment addcomment;
	
	@Autowired
	private ContentReplyModify contentreplymodify;
	
	@Autowired
	private ContentReplyDelete contentreplydelete;
	
	@Autowired
	private MobileMerchantuidCheck mobilemerchantuidcheck;
	
	@RequestMapping("/home") //Ȩ������
	public String home(Model model) {
		
		return "home";
	}
	
	@RequestMapping("/shop") //��ǰ ī�װ� + ��ǰ ����Ʈ
	public String shop(@RequestParam(value = "kind", required = false) String kind,Model model,HttpServletRequest request)
	{
		//model.addAttribute("list",getgoodsinfo.getGoodsInfo(kind));
		if(kind == null)
			kind = "chair";
		pagingModel(model,request,kind);
		model.addAttribute("kind",kind);
		return "shop";
	}
	
	@RequestMapping("/product")	//��ǰ �󼼺���
	public String product(@RequestParam(value = "gId",required = false) String gId,@RequestParam(value = "goods_id",required = false) String goods_id,Model model,HttpSession session,HttpServletRequest request)
	{	
		//int gid = 0;
		String gid;
		if(goods_id == null)
		{
			goods_id = "1";
			//gid = Integer.valueOf(goods_id);
			gid = goods_id;
			model.addAttribute("good",getproductdetails.getproductdetails(Integer.valueOf(gid)));
		}
		else {
			//gid = Integer.valueOf(goods_id);
			gid = goods_id;
			model.addAttribute("good",getproductdetails.getproductdetails(Integer.valueOf(gid)));
		}
		gId = getproductdetails.getproductdetails(Integer.valueOf(gid)).getName();
		pagingModelReply(model,request,gId);
		return "product";
	}

	@RequestMapping(value = "/cart",method = RequestMethod.POST)
	@ResponseBody
	public void cart(@RequestParam String goods_id,@RequestParam String price,@RequestParam String name,@RequestParam String qty,@RequestParam String user_id)
	{
		setshoppingbasket.shoppingbasket(Integer.valueOf(qty), Integer.valueOf(goods_id), Integer.valueOf(price), user_id, name);
	}
	
	@RequestMapping("/alertsystem")
	public String alertsystem(Model model)
	{
		model.addAttribute("alertsystem","alertsystem");
		return "redirect:/showbasket";
	}
	
	@RequestMapping(value = "/cartspace",method = RequestMethod.POST)
	@ResponseBody
	public int cartspace(@RequestBody HashMap<String,Object> map)///////////���� @RequestParam String Id,
	{
		return cartspace.cartspace((String)(map.get("Id")));
	}
	
	 @RequestMapping(value = "/jeongbo",method = RequestMethod.POST)
	 @ResponseBody
	 public JSONObject jeongbo(@RequestBody HashMap<String,Object> map)
	 {
		 String Id = (String)(map.get("Id"));
		 JSONObject data = new JSONObject();
		 User user = findjeongbo.getJeongbo(Id);
		 data.put("name",user.getName());
		 data.put("address",user.getAddress());
		 data.put("phone",user.getPhoneNumber());
		 //data.put("phone1",user.getPhoneNumber().substring(0,3));
		 //data.put("phone2",user.getPhoneNumber().substring(3,7));
		 //data.put("phone3",user.getPhoneNumber().substring(7, 11));
		 data.put("email",user.getEmail());
		 
		 return data;
	 }
	
	
	
	
	@RequestMapping(value = "/shoppingbasket",method = {RequestMethod.POST,RequestMethod.GET}) //��ǰ�� īƮ�� ���� ���
	public String shoppingbasket(HttpServletRequest request,HttpSession session,Model model,@RequestParam(required = false) String check)  //�ֹ��� �ϳ��� ��ǰ�� ���� ����
	{
		/*
		String strreferer = request.getHeader("referer");
		if(strreferer == null) {
			return "redirect:/error";
		}
		
		if(session.getAttribute("Userid") == null)
			return "redirect:/error";
		*/
		int qty;		
		int gid;
		int price;
		String name;
		
		if(request.getParameter("qty") == null && request.getParameter("goods_id") == null && request.getParameter("price") == null && request.getParameter("name") == null)
			model.addAttribute("list",getshoppingbasket.getshoppingbasketlist((String)(session.getAttribute("Userid"))));
		else {
			qty = Integer.valueOf(request.getParameter("qty"));	
			gid = Integer.valueOf(request.getParameter("goods_id"));
			price = Integer.valueOf(request.getParameter("price"));
			name = request.getParameter("name");
		    //setshoppingbasket.shoppingbasket(qty, gid,price,"User_ID",name);
			setshoppingbasket.shoppingbasket(qty, gid,price,(String)(session.getAttribute("Userid")),name);
			model.addAttribute("list",getshoppingbasket.getshoppingbasketlist((String)(session.getAttribute("Userid"))));
			//model.addAttribute("list",getshoppingbasket.getshoppingbasketlist("User_ID"));
		}
		//model.addAttribute("User",session.getAttribute("Userid"));
		model.addAttribute("User",shoppinglistservice.getUser((String)(session.getAttribute("Userid"))));
		if(check != null)
			model.addAttribute("check",check);
		return "redirect:/showbasket";
	}
	
	@RequestMapping("/showbasket") //īƮ�� ��ǰ�� ���� �ʰ� ��ٷ� �α��� �Ŀ� ��ٱ��Ͽ� ��ǰ�� ������ ���
	public String showbasket(HttpServletRequest request,Model model,HttpSession session)
	{	
		model.addAttribute("list",getshoppingbasket.getshoppingbasketlist((String)(session.getAttribute("Userid"))));
		model.addAttribute("User",shoppinglistservice.getUser((String)(session.getAttribute("Userid"))));

		return "shoppingbasket";
	}
	
	@RequestMapping("/remaincheck")
	@ResponseBody
	public int remaincheck(@RequestParam String[] newname,@RequestParam Integer[] newqty)
	{
		int check;
		return remaincheck.remaincheck(newname, newqty);
	}
	
	@RequestMapping(value = "/completeToken",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> completeToken(HttpServletRequest request,HttpServletResponse response,@RequestBody HashMap<String,Object> map) throws Exception
	{
		System.out.println("map : " + map);
		JSONObject json = new JSONObject();
		String imp_key = URLEncoder.encode("2645427372556228", "UTF-8");
		String imp_secret =	URLEncoder.encode("75USkRpzuQ8T8WeQcJrO1GFKEERYRDAYIuR2lgCQ6LKfHY5THxIJenuS2mRTZsSHWJKiZm967TlPRrJz", "UTF-8");
		json.put("imp_key",imp_key);
		json.put("imp_secret", imp_secret);
		System.out.println(json);
		System.out.println("imp_key : " + json.get("imp_key"));
		System.out.println("imp_secret : " + json.get("imp_secret"));
		
		String token = getToken(request,response,json,"https://api.iamport.kr/users/getToken");
		//System.out.println("token : " + token);
		
		JSONObject getdata = null; //���� ����
		
		try{
			String requestString = "";
			URL url = new URL("https://api.iamport.kr/payments/" + (String)map.get("imp_uid"));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true); 				
			connection.setInstanceFollowRedirects(false);  
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization", token);
			OutputStream os= connection.getOutputStream();
			connection.connect();
			StringBuilder sb = new StringBuilder(); 
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
				String line = null;  
				while ((line = br.readLine()) != null) {  
					sb.append(line + "\n");  
				}
				br.close();
				requestString = sb.toString();
			}
			os.flush();
			connection.disconnect();
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParser.parse(requestString);
			System.out.println("jsonObj " + jsonObj);
			if((Long)jsonObj.get("code")  == 0){
				getdata = (JSONObject) jsonObj.get("response");
				System.out.println("getpaymentdata==>>"+getdata );
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("merchant_uid : " + (String)(map.get("merchant_uid")));
		
		String amount = String.valueOf(getdata.get("amount"));	     //Ŭ���̾�Ʈ ������ ������ �ݾ�
		String status = String.valueOf(getdata.get("status"));
		int bepaid = getamount((String)(map.get("merchant_uid")));   //DB�� ó�� ����Ǿ� �ִ� �ݾ�
		//System.out.println("vbanknum : " + String.valueOf(getdata.get("vbanknum")));
		//String merchant_uid = (String)map.get("merchant_uid");
		/////////////////////////////////////////////////// �����ڵ�
		ArrayList<String> listArraylist = (ArrayList<String>)map.get("list");
		String[] list = listArraylist.toArray(new String[listArraylist.size()]);
		ArrayList<Double> glistArraylist = (ArrayList<Double>)map.get("glist");
		System.out.println("glistArraylist : " + glistArraylist);
		 double[] glist = new double[glistArraylist.size()];
		 for (int i=0; i < glist.length; i++)
		 {
		      glist[i] = glistArraylist.get(i).doubleValue();
		 }
		 
		 Integer[] Integerilist = new Integer[glistArraylist.size()];
		 
		 for (int i=0; i < glist.length; i++)
		 {
		      Integerilist[i] = (int)(glist[i]);
		 }
		//Integer[] glist = glistArraylist.toArray(new Integer[glistArraylist.size()]); //Integer[] glist = (Integer[])map.get("glist");
		String merchant_uid = (String)map.get("merchant_uid");
		String imp_uid = (String)map.get("imp_uid");
		String merchant_id = (String)map.get("merchant_id");
		double dprice = (double)map.get("price");
		int iprice = (int)(dprice);
		String price = Integer.toString(iprice);
		String paymethod = String.valueOf(getdata.get("pay_method"));
		System.out.println("completeToken list[0] : " + list[0]);
		System.out.println("completeToken Integerilist[0] : " + Integerilist[0]);

		//insertgoods.insergoods(merchant_id, list, Integerilist);
		//insertprice.insertprice(price, merchant_uid);
		
		
		////////////////////////////////////////////////////
		System.out.println("amount >= (Integer.toString(bepaid)) : " + (Integer.valueOf(amount) >= bepaid));
		System.out.println("amount : " + amount);
		System.out.println("status : " + status);
		System.out.println("imp_uid : " + (String)map.get("imp_uid"));
		System.out.println("price : " + price);
		System.out.println("merchant_id : " + merchant_id);
		System.out.println("merchant_uid : " + merchant_uid);
		JSONObject resjson = new JSONObject();
		
		if(Integer.valueOf(amount) >= bepaid)
		{
			System.out.println("paymethod : " + String.valueOf(getdata.get("pay_method")));
			//updatestatus.updatestatus(status,(String)(map.get("merchant_uid")),(String)map.get("imp_uid"),String.valueOf(getdata.get("pay_method")));
			System.out.println("buyer_name : " + String.valueOf(map.get("buyer_name")));
			switch(status) {
			case "ready":	//������� �߱�
				//////////////////////////////////////////// ������ �ڵ�
				String vbanknum = (String)(map.get("vbanknum"));
				String vbankname = (String)(map.get("vbankname"));
				String vbankdate = (String)(map.get("vbankdate"));
				String vbankholder = (String)(map.get("vbankholder"));
				String buyer_name = String.valueOf(getdata.get("buyer_name"));
				String vbank_code = String.valueOf(getdata.get("vbank_code"));
				insertvbank.InsertVbankAndUpdateStatus(merchant_uid,vbanknum,vbankname,vbankdate,vbankholder,buyer_name,vbank_code,status,imp_uid,paymethod,merchant_id,list,Integerilist,price);
				/////////////////////////////////////////////
				//insertvbank.InsertVbank(merchant_uid,vbanknum,vbankname,vbankdate,vbankholder,buyer_name,vbank_code);
				resjson.put("check","vbankIssued");
				resjson.put("data",getdata);
				resjson.put("vbanknum",getvbankinfo.getvbankinfo(merchant_uid).getVbanknum());
				return resjson;
			case "paid": // ���� �Ϸ�
				//updatestatus.updatestatus(status,(String)(map.get("merchant_uid")),(String)map.get("imp_uid"),String.valueOf(getdata.get("pay_method")));
				updatestatus.updatestatusandorder(status,merchant_uid,imp_uid,paymethod,merchant_id,list,Integerilist,price);
				resjson.put("check","success");
				resjson.put("data",getdata);
				return resjson;
			default :
				return null;
			}
		} else {
			resjson.put("check","fail");
			resjson.put("data",getdata);
			return resjson;
		}
	}
///////////////static ���� �ٲ� ����!!!!!!!!!!!!!!!!////////////////////////////////////////////////////////////
	public static String getToken(HttpServletRequest request,HttpServletResponse response,JSONObject json,String requestURL)
	{
		String _token = "";
		try{
			String requestString = "";
			URL url = new URL(requestURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true); 				
			connection.setInstanceFollowRedirects(false);  
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			OutputStream os= connection.getOutputStream();
			os.write(json.toString().getBytes());
			connection.connect();
			StringBuilder sb = new StringBuilder(); 
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
				String line = null;  
				while ((line = br.readLine()) != null) {  
					sb.append(line + "\n");  
				}
				br.close();
				requestString = sb.toString();
			}
			os.flush();
			connection.disconnect();
			try { 
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParser.parse(requestString);
			if((Long)jsonObj.get("code")  == 0){
				System.out.println("jsonObj : " + jsonObj);
				JSONObject getToken = (JSONObject) jsonObj.get("response");
				System.out.println("getToken : " + getToken);
				System.out.println("getToken.get('access_token')==>>"+getToken.get("access_token"));
				_token = (String)getToken.get("access_token");
			}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
			_token = "";
		}
		return _token;
	}
	
	@RequestMapping(value = "/InsertMerchantId",method = RequestMethod.POST)
	@ResponseBody
	public String InsertMerchantId(@RequestBody HashMap<String,Object> map) //,@RequestParam String merchant_id
	{
		merchantid.InsertMerchant((String)map.get("id"),(String)map.get("merchant_id"),(String)map.get("phoneNumber"),(String)map.get("address"),(String)map.get("buyer_name"),(String)map.get("memo"));
		return (String)map.get("merchant_id");
	}
	
	@RequestMapping(value = "/InsertPrice",method = RequestMethod.POST)
	@ResponseBody
	public void InsertPrice(@RequestParam String price,@RequestParam String merchant_uid)   //@RequestParam String price,@RequestParam String merchant_uid,@RequestParam List<Shoppingbasket> glist
	{
		insertprice.insertprice(price, merchant_uid);
	}
	
	@RequestMapping(value = "/InsertList",method = RequestMethod.POST)
	@ResponseBody
	public void InsertList(@RequestParam String merchant_id,String[] list,Integer[] glist)
	{
		insertgoods.insergoods(merchant_id, list, glist);
	}
	
	private int getamount(String merchant_uid)
	{
		return findprice.getpriceBymerchantid(merchant_uid);
	}
	
	
	@RequestMapping("/OrderResult")
	public String orderresult(@RequestParam String merchant_id,Model model,@RequestParam(required = false) String vbanknum,@RequestParam(required = false) String vbankname,@RequestParam(required = false) String vbankholder,@RequestParam(required = false) String vbankcode,@RequestParam(required = false) String vbankdate)
	{
	  if(vbanknum != null) {
		model.addAttribute("vbank_date",vbankdate);
		model.addAttribute("vbank_code",vbankcode); //���� �ڵ� ��ȣ
		model.addAttribute("vbank_holder",vbankholder); //������
		model.addAttribute("vbank_num",vbanknum); //���� ���¹�ȣ
		model.addAttribute("vbank_name",vbankname); //���� �̸�
	  }
	  	if(findmerchantid.getMerchantId(merchant_id) != null)
			model.addAttribute("Order",findmerchantid.getMerchantId(merchant_id));
		model.addAttribute("Ordergoods",getordergoods.getordergoods(merchant_id));
		return "orderresult";
	}
	
	@RequestMapping(value = "/refundadmin",method = RequestMethod.POST)
	@ResponseBody
	public void refundadmin(@RequestBody HashMap<String,Object> map)
	{
		String merchant_id = (String)map.get("merchant_uid");
		String amount = (String)map.get("cancel_request_amount");
		System.out.println("amount : " + amount);
		String holder = null;
		String bank = null;
		String account = null;
		if((String)map.get("refund_holder") != null) {
			holder = (String)map.get("refund_holder");
			bank = (String)map.get("refund_bank");
			account = (String)map.get("refund_account");
		}
		requestrefund.requestrefund(merchant_id,Integer.valueOf(amount), holder, bank, account);
	}
	
	@RequestMapping(value = "/cancel",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> cancel(HttpServletRequest request,HttpServletResponse response,@RequestBody HashMap<String,Object> map) throws Exception
	{
		JSONObject json = new JSONObject();
		String imp_key = URLEncoder.encode("2645427372556228", "UTF-8");
		String imp_secret =	URLEncoder.encode("75USkRpzuQ8T8WeQcJrO1GFKEERYRDAYIuR2lgCQ6LKfHY5THxIJenuS2mRTZsSHWJKiZm967TlPRrJz", "UTF-8");
		json.put("imp_key",imp_key);
		json.put("imp_secret", imp_secret);
		System.out.println(json);
		System.out.println("imp_key : " + json.get("imp_key"));
		System.out.println("imp_secret : " + json.get("imp_secret"));
		
		JSONObject obj = new JSONObject();
		String token = getToken(request,response,json,"https://api.iamport.kr/users/getToken");
		String reason;
		if((String)map.get("reason") != null)
		{
			reason = (String)map.get("reason");
			obj.put("reason",reason);
		}
		
		int amount = Integer.valueOf(findprice.getpriceBymerchantid((String)map.get("merchant_uid")));		//������ ��
		int cancel_request_amount = Integer.valueOf((String)map.get("cancel_request_amount"));				//ȯ�� �ݾ�
		
		//if(amount - cancel_request_amount <= 0)
			//System.out.println("�̹� ����ȯ�ҵ� ��ǰ�Դϴ�.");
		
		if((String)map.get("refund_holder") != null)				//������ �Ա��̸�
		{
			String imp_uid = findimpuid.getimp_uid((String)(map.get("merchant_uid")));
			obj.put("imp_uid",imp_uid);
			obj.put("merchant_uid",(String)(map.get("merchant_uid")));
			obj.put("cancel_request_amount",(String)map.get("cancel_request_amount"));
			obj.put("refund_holder",(String)map.get("refund_holder"));
			obj.put("refund_bank",(String)map.get("refund_bank"));
			obj.put("refund_account",(String)map.get("refund_account"));
			System.out.println("request obj : " + obj);
		}
		else
		{
			obj.put("imp_uid",findimpuid.getimp_uid((String)(map.get("merchant_uid"))));
			obj.put("cancel_request_amount",(String)map.get("cancel_request_amount"));
		}
		
		JSONObject getcanceldata = null;		//������Ʈ �������� ȯ�� ���� ����
		
		try{
			String requestString = "";
			URL url = new URL("https://api.iamport.kr/payments/cancel");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true); 				
			connection.setInstanceFollowRedirects(false);  
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Authorization", token);
			OutputStream os= connection.getOutputStream();
			os.write(obj.toString().getBytes());
			connection.connect();
			StringBuilder sb = new StringBuilder(); 
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
				String line = null;  
				while ((line = br.readLine()) != null) {  
					sb.append(line + "\n");  
				}
				br.close();
				requestString = sb.toString();
			}
			os.flush();
			connection.disconnect();
			try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParser.parse(requestString);
			System.out.println("jsonObj " + jsonObj);
			if((Long)jsonObj.get("code")  == 0){
				getcanceldata = (JSONObject) jsonObj.get("response");
				System.out.println("getcanceldata==>>"+getcanceldata );
				//_token = (String)getToken.get("access_token");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println("ȯ�� ���� : " + getcanceldata);
		return getcanceldata;
	}
	
	@RequestMapping(value = "/stop",method = RequestMethod.POST)
	@ResponseBody
	public void stop(@RequestBody HashMap<String,Object> map)
	{
		String merchant_id = (String)map.get("merchant_id");
		deletemerchantid.deletemerchantid(merchant_id);
	}
	
	@RequestMapping(value = "/cancelstatus",method = RequestMethod.POST)
	@ResponseBody
	public void cancelstatus(@RequestBody HashMap<String,Object> map)
	{
		String merchant_id = (String)map.get("merchant_id");
		String cancel = (String)map.get("cancel");
		updatestatuscancel.updatestatuscancel(merchant_id, cancel);
	}
	
	@RequestMapping(value = "/iamport-webhook",method = {RequestMethod.POST,RequestMethod.GET})
	public void webhook(@RequestParam(required = false) String imp_uid,@RequestParam(required = false) String merchant_uid,HttpServletRequest request,HttpServletResponse response,@RequestParam(required = false) String status,Model model)
	{
		if(!status.equals("paid")) //������¿� �Ա��߰ų� ���� �Ϸ�
			return;
		
		try {
		JSONObject json = new JSONObject();
		String imp_key = URLEncoder.encode("2645427372556228", "UTF-8");
		String imp_secret =	URLEncoder.encode("75USkRpzuQ8T8WeQcJrO1GFKEERYRDAYIuR2lgCQ6LKfHY5THxIJenuS2mRTZsSHWJKiZm967TlPRrJz", "UTF-8");
		json.put("imp_key",imp_key);
		json.put("imp_secret", imp_secret);
		String token = getToken(request,response,json,"https://api.iamport.kr/users/getToken");
		JSONObject getdata = null;
		
		try
		{
			String requestString = "";
			URL url = new URL("https://api.iamport.kr/payments/" + imp_uid);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true); 				
			connection.setInstanceFollowRedirects(false);  
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization", token);
			OutputStream os= connection.getOutputStream();
			connection.connect();
			StringBuilder sb = new StringBuilder(); 
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
				String line = null;  
				while ((line = br.readLine()) != null) {  
					sb.append(line + "\n");  
				}
				br.close();
				requestString = sb.toString();
			}
			os.flush();
			connection.disconnect();
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParser.parse(requestString);
			System.out.println("jsonObj " + jsonObj);
			if((Long)jsonObj.get("code")  == 0){
				getdata = (JSONObject) jsonObj.get("response");
				System.out.println("getwebhookresponsedata==>>"+getdata );
				int bepaid = getamount(merchant_uid);
				String amount = String.valueOf(getdata.get("amount"));	
				String mystatus = String.valueOf(getdata.get("status"));
				if(String.valueOf(getdata.get("vbank_num")) == null) //������ �Ա��� �ƴϸ�
					return;
				String vbankholder = String.valueOf(getdata.get("vbank_holder"));
				String vbanknum = String.valueOf(getdata.get("vbank_num"));
				String vbankcode = String.valueOf(getdata.get("vbank_code"));
				String paymethod = String.valueOf(getdata.get("pay_method"));
				if(bepaid <= Integer.valueOf(amount) && vbanknum != null) 
				{
					switch(mystatus) 
					{
						case "paid":	//������ �Աݸ� �ش�...
							updatestatus.updatestatus(mystatus, merchant_uid,imp_uid,paymethod,amount,vbankholder,vbanknum,vbankcode); //���� �Ϸ�� �ٲٱ�
					}	
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/mobile")
	public String mobile(@RequestParam String imp_uid,@RequestParam String merchant_uid,HttpServletRequest request,HttpServletResponse response,Model model,@RequestParam String[] listparam,@RequestParam Integer[] glistparam,@RequestParam String amount)
	{
		String referer = (String)request.getHeader("REFERER");
		System.out.println("referer : " + referer);
		try {
			JSONObject json = new JSONObject();
			insertprice.insertprice(amount, merchant_uid);
			String imp_key = URLEncoder.encode("2645427372556228", "UTF-8");
			String imp_secret =	URLEncoder.encode("75USkRpzuQ8T8WeQcJrO1GFKEERYRDAYIuR2lgCQ6LKfHY5THxIJenuS2mRTZsSHWJKiZm967TlPRrJz", "UTF-8");
			json.put("imp_key",imp_key);
			json.put("imp_secret", imp_secret);
			
			String token = getToken(request,response,json,"https://api.iamport.kr/users/getToken");
			JSONObject getdata = null;
			
			try{
				String requestString = "";
				URL url = new URL("https://api.iamport.kr/payments/" + imp_uid);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true); 				
				connection.setInstanceFollowRedirects(false);  
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Authorization", token);
				OutputStream os= connection.getOutputStream();
				connection.connect();
				StringBuilder sb = new StringBuilder(); 
				if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
					String line = null;  
					while ((line = br.readLine()) != null) {  
						sb.append(line + "\n");  
					}
					br.close();
					requestString = sb.toString();
				}
				os.flush();
				connection.disconnect();
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObj = (JSONObject) jsonParser.parse(requestString);
				System.out.println("jsonObj " + jsonObj);
				if((Long)jsonObj.get("code")  == 0){
					getdata = (JSONObject) jsonObj.get("response");
					System.out.println("getmobileresponsedata==>>"+getdata );
					int bepaid = getamount(merchant_uid);
					String getamount = String.valueOf(getdata.get("amount"));	
					String mystatus = String.valueOf(getdata.get("status"));
					String paymethod = String.valueOf(getdata.get("pay_method"));
					System.out.println("mobile bepaid == Integer.valueOf(amount) : " + (bepaid == Integer.valueOf(amount)));
					System.out.println("mobile mystatus :" + mystatus);
					
					if(mystatus.equals("failed"))
						return "mobilefailed";
					else if(mystatus.equals("paid") && referer.contains("https://ksmobile.inicis.com/smart/mobileAcsCancel")) {
						return "redirect:/showorder";
					}
					
						
					if(bepaid <= Integer.valueOf(getamount)) {
						switch(mystatus) 
						{
							case "ready":
								String vbank_num = String.valueOf(getdata.get("vbank_num"));
								String vbank_code = String.valueOf(getdata.get("vbank_code"));
								String vbank_date = String.valueOf(getdata.get("vbank_date"));
								String vbank_name = String.valueOf(getdata.get("vbank_name"));
								String vbank_holder = String.valueOf(getdata.get("vbank_holder"));
								String vbank_person = String.valueOf(getdata.get("buyer_name"));
								
								if(mobilemerchantuidcheck.mobilecheckbymerchantuid(merchant_uid)) {
									model.addAttribute("vbank_date",vbank_date);
									model.addAttribute("vbank_holder",vbank_holder); //������
									model.addAttribute("vbank_num",getvbankinfo.getvbankinfo(merchant_uid).getVbanknum()); //���� ���¹�ȣ
									model.addAttribute("vbank_name",vbank_name); //���� �̸�
									model.addAttribute("vbank_code",vbank_code);
									model.addAttribute("Order",findmerchantid.getMerchantId(merchant_uid));
									model.addAttribute("Ordergoods",getordergoods.getordergoods(merchant_uid));
									model.addAttribute("method","mobile");
									return "orderresult";
								}
								
								insertvbank.InsertVbankAndUpdateStatus(merchant_uid,vbank_num,vbank_name,vbank_date,vbank_holder,vbank_person,vbank_code,mystatus,imp_uid,paymethod,merchant_uid,listparam,glistparam,getamount);
								model.addAttribute("vbank_date",vbank_date);
								model.addAttribute("vbank_holder",vbank_holder); //������
								model.addAttribute("vbank_num",getvbankinfo.getvbankinfo(merchant_uid).getVbanknum()); //���� ���¹�ȣ
								model.addAttribute("vbank_name",vbank_name); //���� �̸�
								model.addAttribute("vbank_code",vbank_code);
								model.addAttribute("Order",findmerchantid.getMerchantId(merchant_uid));
								model.addAttribute("Ordergoods",getordergoods.getordergoods(merchant_uid));
								model.addAttribute("method","mobile");
								//return "redirect:/showorder";
								return "orderresult";
							case "paid":
								if(mobilemerchantuidcheck.mobilecheckbymerchantuid(merchant_uid)) {
									model.addAttribute("Order",findmerchantid.getMerchantId(merchant_uid));
									model.addAttribute("Ordergoods",getordergoods.getordergoods(merchant_uid));
									model.addAttribute("method","mobile");
									return "orderresult";
								}
								updatestatus.updatestatusandorder(mystatus,merchant_uid,imp_uid,paymethod,merchant_uid,listparam,glistparam,getamount);
								model.addAttribute("Order",findmerchantid.getMerchantId(merchant_uid));
								model.addAttribute("Ordergoods",getordergoods.getordergoods(merchant_uid));
								model.addAttribute("method","mobile");
								//return "redirect:/showorder";
								return "orderresult";
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			}catch(Exception e) {
				e.printStackTrace();
			}
		return null;
	}
	
	@RequestMapping(value = "/mobilemaintainsession",method = RequestMethod.POST)
	@ResponseBody
	public void mobilemaintainsession(@RequestParam String Id,HttpSession session)
	{
		if(session.getAttribute("Userid") == null)
			session.setAttribute("Userid",Id);
	}
	
	@RequestMapping("/showorder")
	public String showorder(HttpServletRequest request,HttpSession session,Model model) throws Exception
	{
		/*
		String strreferer = request.getHeader("referer");
		if(strreferer == null) {
			return "redirect:/error";
		}
		*/
		String id = (String)session.getAttribute("Userid");
		
		int curPageNum; //���� ����ڰ� ���� ������
		String page = request.getParameter("page");
		System.out.println("page : " + page);
		if(page != null)
			curPageNum = Integer.valueOf(page);
		else
			curPageNum = 1; //ó�� �������� ���� ���(����¡ ó���� �������� ������ �ʾ��� ���)
		
		paging.makeBlock(curPageNum); //����� ù��° ������ �ѹ� ���ϱ�,����� ������ ������ �ѹ� ���ϱ�
		paging.lastPageNumOrder(id);  //�� ������ �� ���ϱ� ->KeyWorD ��� ������ ��!
		
		Integer blockStartNum = paging.getBlockStartNum();	//����� ���� ������ �ѹ�
		Integer blockLastNum = paging.getBlockLastNum();    //����� ������ ������ �ѹ�
		Integer lastPageNum = paging.getLastPageNum();		//������ ������
		
		System.out.println(id);
		List<Order> orders = getorderinfo.getorderinfo(id,curPageNum); //ordertable�� �ֹ� �����͵�
		model.addAttribute("orders",orders);
		System.out.println(orders.size());
		
		List<List<Ordergoods>> ordergoods = new ArrayList<List<Ordergoods>>(); 
		List<Vbank> vbanks = new ArrayList<Vbank>();
		
		
		for(int i=0;i<orders.size();i++) {
			ordergoods.add(getordergoods.getordergoods(orders.get(i).getMerchant_id()));    //�ֹ� ��ȣ�� ��Ų ��ǰ�� 
			vbanks.add(getvbankinfo.getvbankinfo(orders.get(i).getMerchant_id()));
			System.out.println("vbanks : " + vbanks);
			System.out.println(orders.get(i).getMerchant_id());
			//System.out.println(getvbankinfo.getvbankinfo(orders.get(i).getMerchant_id()));
		}
		model.addAttribute("ordergoods",ordergoods);
		model.addAttribute("vbanks",vbanks);
		model.addAttribute("curPageNum",curPageNum).addAttribute("blockStartNum",blockStartNum).addAttribute("blockLastNum",blockLastNum).addAttribute("lastPageNum",lastPageNum);
		
		return "orderinfo";
	}
	
	/*
	private Model pagingorderModel(Model model,HttpServletRequest request)
	{
		int curPageNum; //���� ����ڰ� ���� ������
		String page = request.getParameter("page");
		
		if(page != null)
			curPageNum = Integer.valueOf(page);
		else
			curPageNum = 1; //ó�� �������� ���� ���(����¡ ó���� �������� ������ �ʾ��� ���)
		
		paging.makeBlock(curPageNum); //����� ù��° ������ �ѹ� ���ϱ�,����� ������ ������ �ѹ� ���ϱ�
		paging.lastPageNum(kind);  //�� ������ �� ���ϱ� ->KeyWorD ��� ������ ��!
		
		Integer blockStartNum = paging.getBlockStartNum();	//����� ���� ������ �ѹ�
		Integer blockLastNum = paging.getBlockLastNum();    //����� ������ ������ �ѹ�
		Integer lastPageNum = paging.getLastPageNum();		//������ ������
		
		List<goods> list = paging.dtos(curPageNum,kind);
		
		model.addAttribute("list",list).addAttribute("curPageNum",curPageNum).addAttribute("blockStartNum",blockStartNum).addAttribute("blockLastNum",blockLastNum).addAttribute("lastPageNum",lastPageNum);
		return model;
	}
	*/
	
	
	@RequestMapping("/error")
	public String error()
	{
		return "/error/404code";
	}
	
	@RequestMapping("/deleteshoppingcart")
	@ResponseBody
	public int deleteshoppingcart(int pnum) {
		dshoppingbasket.deleteshoppingbasket(pnum);
		return pnum;
	}
	
	@RequestMapping("/download")
	public String download(HttpServletRequest request)
	{
		return null;
	}
	
	@RequestMapping("/search")
	public String search(@RequestParam String search,HttpServletRequest request,Model model)
	{
		pagingModelKwd(search,request,model);
		model.addAttribute("search",search);
		return "search";
	}
	
	
	private Model pagingModelKwd(String search,HttpServletRequest request,Model model)
	{
		int curPageNum; //���� ����ڰ� ���� ������
		String page = request.getParameter("page");
		
		if(page != null)
			curPageNum = Integer.valueOf(page);
		else
			curPageNum = 1; //ó�� �������� ���� ���(����¡ ó���� �������� ������ �ʾ��� ���)
		
		paging.makeBlock(curPageNum); //����� ù��° ������ �ѹ� ���ϱ�,����� ������ ������ �ѹ� ���ϱ�
		paging.lastPageNumKwd(search);  //�� ������ �� ���ϱ� ->KeyWorD ��� ������ ��!
		
		Integer blockStartNum = paging.getBlockStartNum();	//����� ���� ������ �ѹ�
		Integer blockLastNum = paging.getBlockLastNum();    //����� ������ ������ �ѹ�
		Integer lastPageNum = paging.getLastPageNum();		//������ ������
		
		List<goods> list = paging.dtoWithKwd(curPageNum,search);
		
		model.addAttribute("list",list).addAttribute("curPageNum",curPageNum).addAttribute("blockStartNum",blockStartNum).addAttribute("blockLastNum",blockLastNum).addAttribute("lastPageNum",lastPageNum);
		return model;
	}
	
	
	private Model pagingModel(Model model,HttpServletRequest request,String kind)
	{
		int curPageNum; //���� ����ڰ� ���� ������
		String page = request.getParameter("page");
		
		if(page != null)
			curPageNum = Integer.valueOf(page);
		else
			curPageNum = 1; //ó�� �������� ���� ���(����¡ ó���� �������� ������ �ʾ��� ���)
		
		paging.makeBlock(curPageNum); //����� ù��° ������ �ѹ� ���ϱ�,����� ������ ������ �ѹ� ���ϱ�
		paging.lastPageNum(kind);  //�� ������ �� ���ϱ� ->KeyWorD ��� ������ ��!
		
		Integer blockStartNum = paging.getBlockStartNum();	//����� ���� ������ �ѹ�
		Integer blockLastNum = paging.getBlockLastNum();    //����� ������ ������ �ѹ�
		Integer lastPageNum = paging.getLastPageNum();		//������ ������
		
		List<goods> list = paging.dtos(curPageNum,kind);
		
		model.addAttribute("list",list).addAttribute("curPageNum",curPageNum).addAttribute("blockStartNum",blockStartNum).addAttribute("blockLastNum",blockLastNum).addAttribute("lastPageNum",lastPageNum);
		return model;
	}
	/*
	@RequestMapping("/test")
	@ResponseBody
	public void test(@RequestParam String[] param)
	{
		System.out.println("param : " + param);
	}
	*/
	
	/*
	@RequestMapping(value = "/upload",method = RequestMethod.POST) //��ǰ �̹���,�Խ��� �̹��� ���ε� ,produces="text/plain;charset=UTF-8"
	@ResponseBody
	public String upload(MultipartFile file, HttpServletRequest request, HttpServletResponse response,@RequestParam(required = false) Integer goods_id) throws Exception 
	{
		String realFolder = null;
		
		if(goods_id != null)
			realFolder = request.getSession().getServletContext().getRealPath("goodsimgUpload"); //������ ���� �̹���
		else
			realFolder = request.getSession().getServletContext().getRealPath("boardUpload"); //�Խ��� ���� �̹���
		
		UUID uuid = UUID.randomUUID();
		
		// ���ε��� ���� �̸�
		String org_filename = file.getOriginalFilename();
		String str_filename = uuid.toString() + org_filename;

		System.out.println("���� ���ϸ� : " + org_filename); //.........photo.jpg
		System.out.println("������ ���ϸ� : " + str_filename); //ad340234fsdfbdfvfd0924309..........photo.jpg
		
		String filepath = realFolder + "\\" + str_filename; 
		
		System.out.println("���ϰ�� : " + filepath);

		File f = new File(filepath);
		
		if (!f.exists()) {
			f.mkdirs();
		}
		
		file.transferTo(f); //���� ���ε� �Ϸ�		
		
		if(goods_id != null) {
			byte[] data = file.getBytes();
			FileOutputStream fos = new FileOutputStream("C:\\SpringShoppingmall\\workplace\\ShoppingApp\\src\\main\\webapp\\goodsimgUpload\\" +  str_filename); //��Ŭ���� ���� goodsimgUpload ���� �ȿ� ������ ����
			fos.write(data);
			fos.close();
		}
		else
		{
			byte[] data = file.getBytes();
			FileOutputStream fos = new FileOutputStream("C:\\SpringShoppingmall\\workplace\\ShoppingApp\\src\\main\\webapp\\boardUpload\\" +  str_filename); //��Ŭ���� ���� boardUpload ���� �ȿ� ������ ����
			fos.write(data);
			fos.close();
		}
		if(goods_id != null)
		{
			//goodsimgupload.uploadProflie(goods_id,"http://localhost:8090/shoppingmall/goodsimgUpload/"+str_filename);
			return "https://localhost:8443/shoppingmall/goodsimgUpload/"+str_filename;
		}
		return "https://localhost:8443/shoppingmall/boardUpload/"+str_filename;
	}
	*/
	
	@RequestMapping(value = "/upload",method = RequestMethod.POST) //��ǰ �̹���,�Խ��� �̹��� ���ε� ,produces="text/plain;charset=UTF-8"
	@ResponseBody
	public Map<String,Object> upload(MultipartFile file, HttpServletRequest request, HttpServletResponse response,@RequestParam(required = false) Integer goods_id) throws Exception 
	{
		String realFolder = null;
		JSONObject json = new JSONObject();
		/*
		if(goods_id != null)
			realFolder = request.getSession().getServletContext().getRealPath("goodsimgUpload"); //������ ���� �̹���
		else
			realFolder = request.getSession().getServletContext().getRealPath("boardUpload"); //�Խ��� ���� �̹���
		*/
		UUID uuid = UUID.randomUUID();
		
		// ���ε��� ���� �̸�
		String org_filename = file.getOriginalFilename();
		String str_filename = uuid.toString() + org_filename;

		System.out.println("���� ���ϸ� : " + org_filename); //.........photo.jpg
		System.out.println("������ ���ϸ� : " + str_filename); //ad340234fsdfbdfvfd0924309..........photo.jpg
		/*
		String filepath = realFolder + "\\" + str_filename; 
		
		System.out.println("���ϰ�� : " + filepath);

		File f = new File(filepath);
		
		if (!f.exists()) {
			f.mkdirs();
		}
		
		file.transferTo(f); //���� ���ε� �Ϸ�		
		*/
		if(goods_id != null) {
			byte[] data = file.getBytes();
			//FileOutputStream fos = new FileOutputStream("C:\\SpringShoppingmall\\workplace\\ShoppingApp\\src\\main\\webapp\\goodsimgUpload\\" +  str_filename); //��Ŭ���� ���� goodsimgUpload ���� �ȿ� ������ ����
			FileOutputStream fos = new FileOutputStream("/opt/tomcat/webapps/ROOT/upload/" +  str_filename);
			fos.write(data);
			fos.close();
		}
		else
		{
			byte[] data = file.getBytes();
			FileOutputStream fos = new FileOutputStream("C:\\SpringShoppingmall\\workplace\\ShoppingApp\\src\\main\\webapp\\boardUpload\\" +  str_filename); //��Ŭ���� ���� boardUpload ���� �ȿ� ������ ����
			fos.write(data);
			fos.close();
		}
		if(goods_id != null)
		{
			//json.put("url","https://localhost:8443/shoppingmall/goodsimgUpload/"+str_filename);
			json.put("url","https://www.forallshoppingmall.com/upload/" +  str_filename);
			return json;
		}
		json.put("url","https://localhost:8443/shoppingmall/boardUpload/"+str_filename);
		json.put("orgname",str_filename);
		return json;
	}
	
	
	 @RequestMapping("/downloadFile")
	    public void downloadFile(@RequestParam String storedfilename,HttpServletResponse response) throws Exception{
	        byte[] fileByte = FileUtils.readFileToByteArray(new File("C:\\SpringShoppingmall\\workplace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\ShoppingApp\\boardUpload\\" + storedfilename));
	        response.setContentType("application/octet-stream");
	        response.setContentLength(fileByte.length);
	        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(storedfilename,"UTF-8")+"\";");
	        response.setHeader("Content-Transfer-Encoding", "binary");
	        response.getOutputStream().write(fileByte);
	        response.getOutputStream().flush();
	        response.getOutputStream().close();
	    }
	 
	 @RequestMapping("/jusoPopup")
	 public String jusoPopup()
	 {
		 return "jusoPopup";
	 }
	
	 @RequestMapping(value = "/usecoupon",method = RequestMethod.POST)
	 @ResponseBody
	 public Integer usecoupon(@RequestParam String cnumber)
	 {
		 Integer data = usecouponservice.usecoupon(cnumber);
	     return data;
	 }
	 
	 @RequestMapping(value = "/updatediscountpercent",method = RequestMethod.POST)
	 public void updatediscountpercent(@RequestParam String couponId,@RequestParam String merchant_id)
	 {
		 updatediscountpercent.updatediscountpercent(couponId,merchant_id);
	 }
	 
	 @RequestMapping(value = "/makecoupon",method = RequestMethod.POST)
	 @ResponseBody
	 public Integer makecoupon(@RequestParam String Id)
	 {
		 return receivecoupon.receivecoupon(Id);
	 }
	 
	 @RequestMapping("/showcoupon")
	 public String showcoupon(HttpServletRequest request,HttpSession session,Model model)
	 {
		 String Id = (String)(session.getAttribute("Userid"));
		 pagingModelCoupon(model,request,Id);
				 
		 return "showcoupon";
	 }
	 
	 /*
	 @RequestMapping(value = "/updateusecoupon",method = RequestMethod.POST)
	 public void updateusecoupon(@RequestParam String cnumber)
	 {
		 updateusecouponservice.updateusecouponservice("yes",cnumber);
	 }
	 */
	 
	 private Model pagingModelCoupon(Model model,HttpServletRequest request,String Id)
	 {
			int curPageNum; //���� ����ڰ� ���� ������
			String page = request.getParameter("page");
			if(page != null)
				curPageNum = Integer.valueOf(page);
			else
				curPageNum = 1; //ó�� �������� ���� ���(����¡ ó���� �������� ������ �ʾ��� ���)
			System.out.println("page : " + page);
			paging.makeBlock(curPageNum); //����� ù��° ������ �ѹ� ���ϱ�,����� ������ ������ �ѹ� ���ϱ�
			paging.lastPageNumCoupon(Id);  //�� ������ �� ���ϱ� ->KeyWorD ��� ������ ��!
			
			Integer blockStartNum = paging.getBlockStartNum();	//����� ���� ������ �ѹ�
			Integer blockLastNum = paging.getBlockLastNum();    //����� ������ ������ �ѹ�
			Integer lastPageNum = paging.getLastPageNum();		//������ ������
			
			List<Coupon> list = paging.coupons(curPageNum,Id);
			
			model.addAttribute("list",list).addAttribute("curPageNum",curPageNum).addAttribute("blockStartNum",blockStartNum).addAttribute("blockLastNum",blockLastNum).addAttribute("lastPageNum",lastPageNum);
			return model;
	 }
	 
	 private Model pagingModelReply(Model model,HttpServletRequest request,String gId)
	 {
			int curPageNum; //���� ����ڰ� ���� ������
			String page = request.getParameter("page");
			if(page != null)
				curPageNum = Integer.valueOf(page);
			else
				curPageNum = 1; //ó�� �������� ���� ���(����¡ ó���� �������� ������ �ʾ��� ���)
			System.out.println("page : " + page);
			paging.makeBlock(curPageNum); //����� ù��° ������ �ѹ� ���ϱ�,����� ������ ������ �ѹ� ���ϱ�
			paging.lastPageNumReply(gId);  //�� ������ �� ���ϱ� ->KeyWorD ��� ������ ��!
			
			Integer blockStartNum = paging.getBlockStartNum();	//����� ���� ������ �ѹ�
			Integer blockLastNum = paging.getBlockLastNum();    //����� ������ ������ �ѹ�
			Integer lastPageNum = paging.getLastPageNum();		//������ ������
			
			List<Reply> list = paging.reply(curPageNum,gId);
			System.out.println("list : " + list);
			model.addAttribute("list",list).addAttribute("curPageNum",curPageNum).addAttribute("blockStartNum",blockStartNum).addAttribute("blockLastNum",blockLastNum).addAttribute("lastPageNum",lastPageNum);
			return model;
	 }
	 
	 /*
	 @RequestMapping("/replypage")
	 public String replypage(@RequestParam String gId,Model model,HttpServletRequest request)
	 {
			pagingModelReply(model,request,gId);
			return "product";
	 }
	 */
	 
	 @RequestMapping("/deleteallshoppingcart")
	 @ResponseBody
	 public void deleteallshoppingmall(@RequestParam String Id)
	 {
		 deleteallshoppingmall.deleteall(Id);
	 }
	 /*
	 @RequestMapping(value = "/jeongbo",method = RequestMethod.POST)
	 @ResponseBody
	 public JSONObject jeongbo(@RequestParam String Id)
	 {
		 JSONObject data = new JSONObject();
		 User user = findjeongbo.getJeongbo(Id);
		 data.put("name",user.getName());
		 data.put("address",user.getAddress());
		 data.put("phone",user.getPhoneNumber());
		 //data.put("phone1",user.getPhoneNumber().substring(0,3));
		 //data.put("phone2",user.getPhoneNumber().substring(3,7));
		 //data.put("phone3",user.getPhoneNumber().substring(7, 11));
		 data.put("email",user.getEmail());
		 
		 return data;
	 }
	 */
	 
	 @RequestMapping("/commentList")
	 @ResponseBody
	 public List<Reply> commentList(@RequestParam String gId)
	 {
		 return commentlist.commentlist(gId);
	 }
	 
	@RequestMapping("/addComment")
	@ResponseBody
	public boolean addComment(@RequestParam String reply,@RequestParam String gId,@RequestParam String user_id)
	{
		return addcomment.addComment(gId,user_id,reply);
	}
	
	@RequestMapping("/contentreplymodify")
	@ResponseBody
	public void contentreplymodify(@RequestParam String gId,@RequestParam String rId,@RequestParam String content,HttpServletRequest request,HttpServletResponse response)
	{
		String strreferer = request.getHeader("referer");
		System.out.println("strreferer:"+strreferer);
		if(strreferer == null)
		{
			try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.print("<!DOCTYPE html><html><head><title>errorpage</title></head><body><h1>ErrorPage</h1><script>location.href='http://localhost:8090/myapp/';</script></body></html>");
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			return;
		}
		contentreplymodify.contentreplymodify(gId,rId,content);
	}
	
	@RequestMapping("/contentreplydelete")
	@ResponseBody
	public void contentreplydelete(@RequestParam String gId,@RequestParam String rId,HttpServletRequest request,HttpServletResponse response)
	{
		String strreferer = request.getHeader("referer");
		System.out.println("strreferer:"+strreferer);
		if(strreferer == null)
		{
			try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.print("<!DOCTYPE html><html><head><title>errorpage</title></head><body><h1>ErrorPage</h1><script>location.href='http://localhost:8090/myapp/';</script></body></html>");
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			return;
		}
		System.out.println("deletegId:"+gId);
		System.out.println("deleterId:"+rId);
		contentreplydelete.contentreplydelete(gId,rId);
	}
}