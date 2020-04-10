package spring.myapp.shoppingmall.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import spring.myapp.shoppingmall.dto.Coupon;
import spring.myapp.shoppingmall.dto.Order;
import spring.myapp.shoppingmall.dto.Ordergoods;
import spring.myapp.shoppingmall.dto.Refund;
import spring.myapp.shoppingmall.dto.Reply;
import spring.myapp.shoppingmall.dto.Shoppingbasket;
import spring.myapp.shoppingmall.dto.User;
import spring.myapp.shoppingmall.dto.Vbank;
import spring.myapp.shoppingmall.dto.goods;

@Repository
public class MallDao 
{
	private JdbcTemplate template;
	@Autowired
	private TransactionTemplate transactionTemplate1;
	@Autowired
	private TransactionTemplate transactionTemplate2;
	
	@Autowired
	public MallDao(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}
	
	private Integer couponresult;
	private Integer receivecoupon;
	/*
	public void insertOrder(String phoneNumber,String address) {
		String sql = "insert into ordertable (phoneNumber,address) values (?,?)";
		template.update(sql,new Object[] {phoneNumber,address});
	}
	*/
	public void insertMerchant(String id,String merchant_id,String phoneNumber,String address,String buyer_name,String memo) {
		//String sql = "insert into ordertable (id,merchant_id,status,phoneNumber,address,imp_uid,name) values (?,?,?,?,?,250,?)"; //Test
		String sql = "insert into ordertable (id,merchant_id,status,phoneNumber,address,name,memo) values (?,?,?,?,?,?,?)";

		template.update(sql,new Object[] {id,merchant_id,"not paid",phoneNumber,address,buyer_name,memo});
	}

	public void setShoppingbasket(int gid,String User_ID,int price,int qty,String name) {	//장바구니 담기 + 주문하기
		String sql = "select * from goods where Id = ?";
		goods good;
		good = template.queryForObject(sql,new Object[] {gid},new RowMapper<goods>() {
			public goods mapRow(ResultSet rs,int rowNum) throws SQLException{
				goods good = new goods();
				good.setGoodsprofile(rs.getString("goodsprofile"));
				good.setRemain(rs.getInt("remain"));
				return good;
			}
		});
		
		if(good.getRemain() < qty) {
			/*try {
				String requestURL = "https://localhost:8443/shoppingmall/alertsystem";
				CloseableHttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
				HttpGet getRequest = new HttpGet(requestURL); //GET 메소드 URL 생성
				client.execute(getRequest);
			}catch(Exception e) {
				e.printStackTrace();
			}*/
			return;
		}
		
		sql = "insert into shoppingbasket (goods_id,user_id,price,thumbnail,name,qty) values (?,?,?,?,?,?)";
		template.update(sql,new Object[] {gid,User_ID,price,good.getGoodsprofile(),name,qty});
	}
	
	public List<Shoppingbasket> getShoppingbasket(String User_ID){ 					//장바구니 담았던 물품 정보들
		String sql = "select * from shoppingbasket where user_id = ?";
		List<Shoppingbasket> goods;
		goods = template.query(sql,new Object[]{User_ID},new RowMapper<Shoppingbasket>() {
			public Shoppingbasket mapRow(ResultSet rs,int rowNum) throws SQLException{
				Shoppingbasket good = new Shoppingbasket();
				good.setGoods_id(rs.getInt("goods_id"));
				good.setUser_id(rs.getString("user_id"));
				good.setPrice(rs.getInt("price"));
				good.setThumbnail(rs.getString("thumbnail"));
				good.setName(rs.getString("name"));
				good.setQty(rs.getInt("qty"));
				good.setPnum(rs.getInt("pnum"));
				
				return good;
			}
		});
		return goods;
	}
	
	public goods getGoodsInfo(int goods_id) { 							//하나의 물품 정보 확인
		String sql = "select * from goods where Id = ?";
		goods good;
		good = template.queryForObject(sql,new Object[] {goods_id},new RowMapper<goods>() {
			public goods mapRow(ResultSet rs,int rowNum) throws SQLException{
				goods good = new goods();
				good.setName(rs.getString("Name"));
				good.setPrice(rs.getInt("Price"));
				good.setGoodsprofile(rs.getString("goodsprofile"));
				good.setKind(rs.getString("kind"));
				good.setRemain(rs.getInt("remain"));
				good.setId(rs.getInt("Id"));
				good.setGoodscontent(rs.getString("goodscontent"));
				good.setWriter(rs.getString("writer"));
				good.setWcompany(rs.getString("wcompany"));
				good.setTcontent(rs.getString("tcontent"));
				
				return good;
			}
		});
		return good;
	}
	
	public List<goods> getShoppingGoodsInfo(String kind) { 							//하나의 물품 종류에 대하여 모든 물품을 가져옴 
		String sql = "select * from goods where kind = ?";
		List<goods> goods;
		goods = template.query(sql,new Object[] {kind},new RowMapper<goods>() {
			public goods mapRow(ResultSet rs,int rowNum) throws SQLException{
				goods good = new goods();
				good.setName(rs.getString("Name"));
				good.setPrice(rs.getInt("Price"));
				good.setGoodsprofile(rs.getString("goodsprofile"));
				good.setRemain(rs.getInt("remain"));
				
				return good;
			}
		});
		return goods;
	}
	
	public void uploadgoods(String thumbnail,String name,int price,String kind,int remain,String content,String writer,String wcompany
			,String tcontent) {
		String sql = "insert into goods (goodsprofile,name,price,kind,remain,goodscontent,writer,wcompany,tcontent) values (?,?,?,?,?,?,?,?,?)";
	    if(content.contains("\r\n"))
	    	content = content.replace("\r\n","<br>");
	    if(tcontent.contains("\r\n"))
	    	tcontent = tcontent.replace("\r\n","<br>");
	    
		template.update(sql,new Object[] {thumbnail,name,price,kind,remain,content,writer,wcompany,tcontent});
	}
	
	public void uploadProfilegoods(int goods_id,String uploadurl) {    //이미 등록되어 있는 물품의 프로필을 등록
		String sql = "update goods set goodsprofile = ? where Id = ?";
		template.update(sql,new Object[] {uploadurl,goods_id});
	}

	/////////////////////////////////////////페이징 처리///////////////////////////////////////////
	public List<goods> getsearchinfo(int curPageNum,String search){
		search = search.trim();
		if(search.contains("'") || search.contains(" "))
		{
			search = search.replace("'","");
			search = search.replace(" ","");
		}
		System.out.println("search : " + search);
		String sql = "select R1.* FROM( SELECT * FROM goods where name like '%" + search + "%' order by Id asc) R1 LIMIT ?, ?";
		List<goods> goods = null;
		goods = template.query(sql,new Object[] {6 * (curPageNum - 1),6},new RowMapper<goods>() {
			public goods mapRow(ResultSet rs,int rowNum) throws SQLException{
				goods good = new goods();
				good.setId(rs.getInt("Id"));
				good.setName(rs.getString("Name"));
				good.setPrice(rs.getInt("Price"));
				good.setGoodsprofile(rs.getString("goodsprofile"));
				good.setRemain(rs.getInt("remain"));
				good.setKind(rs.getString("kind"));
				
				return good;
			}
		});
		return goods;
	}
	
	public int getCountKwd(String search) {
		search = search.trim();
		if(search.contains("'") || search.contains(" "))
		{
			search = search.replace("'","");
			search = search.replace(" ","");
		}
		String sql = "select * from goods where name like '%" + search + "%'";
		List<goods> goods = null;
		goods = template.query(sql,new RowMapper<goods>() {
			public goods mapRow(ResultSet rs,int rowNum) throws SQLException{
				goods good = new goods();
				good.setId(rs.getInt("Id"));
				return good;
			}
		});
		return goods.size();
	}
	
	public List<goods> getCurPageDtos(int curPageNum,String kind) {
		String sql = "select R1.* FROM( SELECT * FROM goods where kind = ? order by Id asc) R1 LIMIT ?, ?";   
		List<goods> goods = null;
		goods = template.query(sql,new Object[] {kind,6 * (curPageNum - 1),6},new RowMapper<goods>() { // 6 * (curPageNum -1 )
			public goods mapRow(ResultSet rs,int rowNum) throws SQLException{
				goods good = new goods();
				good.setId(rs.getInt("Id"));
				good.setName(rs.getString("Name"));
				good.setPrice(rs.getInt("Price"));
				good.setGoodsprofile(rs.getString("goodsprofile"));
				good.setRemain(rs.getInt("remain"));
				good.setKind(rs.getString("kind"));
				
				return good;
			}
		});
		return goods;
	}

	public int getCount(String kind) 
	{
		String sql = "select * from goods where kind = ?";
		List<goods> goods = null;
		goods = template.query(sql,new Object[] {kind},new RowMapper<goods>() {
			public goods mapRow(ResultSet rs,int rowNum) throws SQLException{
				goods good = new goods();
				good.setId(rs.getInt("Id"));
				return good;
			}
		});
		return goods.size();
	}
	
	public int getCountOrder(String id) {
		String sql = "select * from ordertable where id = ?";
		List<Order> orders = null;
		orders = template.query(sql,new Object[] {id},new RowMapper<Order>() {
			public Order mapRow(ResultSet rs,int rowNum) throws SQLException{
				Order order = new Order();
				order.setId(rs.getString("id"));
				return order;
			}
		});
		return orders.size();
	}
	///////////////////////////////////////////////////////////////////////////////////////////

	public void deletebasket(int pnum) {
		String sql = "delete from shoppingbasket where pnum = ?";
		template.update(sql,new Object[] {pnum});
	}
	public void insertPrice(int price,String merchant_uid) {
	 transactionTemplate1.execute(new TransactionCallbackWithoutResult() {
		 @Override
		 protected void doInTransactionWithoutResult(TransactionStatus status) {
		String sql = "update ordertable set price = ? where merchant_id = ?";
		template.update(sql,new Object[] {price,merchant_uid});
		 }
	 });
	}
	
	public int getfindprice(String merchant_uid) {
		String sql = "select * from ordertable where merchant_id = ?";
		int price;
		Order order;
		order = template.queryForObject(sql,new Object[] {merchant_uid},new RowMapper<Order>() {
			public Order mapRow(ResultSet rs,int rowNum) throws SQLException{
				Order order = new Order();
				order.setPrice(rs.getInt("price"));
				
				return order;
			}
		});
		
		return order.getPrice();
	}
	public void statusupdate(String updatestatus,String merchant_uid,String imp_uid,String paymethod) {
	 transactionTemplate1.execute(new TransactionCallbackWithoutResult(){
		@Override
		protected void doInTransactionWithoutResult(TransactionStatus status) {
		if(imp_uid != null)
		{	
			String sql = "update ordertable set status = ?,imp_uid = ?,paymethod = ? where merchant_id = ?";	//결제 완료
			template.update(sql,new Object[] {updatestatus,imp_uid,paymethod,merchant_uid}); 
		}
		else
		{
			String sql = "update ordertable set status = ? where merchant_id = ?"; //결제 미완료 ex)무통장 입금
			template.update(sql,new Object[] {updatestatus,merchant_uid});
		}
	  }
	 });
	}
	public Order getMerchantid(String merchant_id) {
		String sql = "select * from ordertable where merchant_id = ?";
		Order order = null;
	try {
		order = template.queryForObject(sql,new Object[] {merchant_id},new RowMapper<Order>() {
			public Order mapRow(ResultSet rs,int rowNum) throws SQLException
			{
				Order order = new Order();
				order.setMerchant_id(rs.getString("merchant_id"));
				order.setPrice(rs.getInt("price"));
				order.setStatus(rs.getString("status"));
			
				return order;
			}
		});
	}catch (Exception e) {
		order = null;
	}
		return order;
	}
	public String getimp_uid(String merchant_uid) {
		String sql = "select * from ordertable where merchant_id = ?";
		Order order = null;
		order = template.queryForObject(sql, new Object[] {merchant_uid},new RowMapper<Order>() {
			public Order mapRow(ResultSet rs,int rowNum) throws SQLException{
				Order order = new Order();
				order.setImp_uid(rs.getString("imp_uid"));
				
				return order;
			}
		});
		return order.getImp_uid();
	}
	public void deltemerchantid(String merchant_id) {
		String sql = "delete from ordertable where merchant_id = ?";
		template.update(sql,new Object[] {merchant_id});
	}
	
	public void statusupdatecancel(String merchant_id, String cancel) {
		String sql = "update ordertable set status = ? where merchant_id = ?";
		template.update(sql,new Object[] {cancel,merchant_id});
	}
	
	public void insertvbank(String merchant_id, String vbanknum, String vbankname, String vbankdate,
			String vbankholder,String vbankperson,String vbankcode) {
		String sql = "insert into vbank (merchant_id,vbanknum,vbankname,vbankdate,vbankholder,vbankperson,vbankcode) values (?,?,?,?,?,?,?)";
		template.update(sql,new Object[] {merchant_id,vbanknum,vbankname,vbankdate,vbankholder,vbankperson,vbankcode});
	}
	
	public void insertgoods(String merchant_id, String[] list, Integer[] glist) 
	{
	 transactionTemplate1.execute(new TransactionCallbackWithoutResult() {
		@Override
		protected void doInTransactionWithoutResult(TransactionStatus status) {
		//String sql = "insert into ordergoods (merchant_id,name,qty) values (?,?,?)";
		String sql = null;
		String sql2 = null;
		System.out.println("list : " + list);
		System.out.println("glist : " + glist);
		System.out.println("list size : " + list.length);
		String name;
		int g;
		
		for(int i = 0; i < list.length; i++)
		{
			sql = "insert into ordergoods (merchant_id,name,qty) values (?,?,?)";
			name = list[i];
			g = glist[i];
			template.update(sql,new Object[] {merchant_id,name,g});
			//System.out.println("list : " + list[i]);
			sql = "select * from goods where name = ?";
			goods good = null;
			good = template.queryForObject(sql,new Object[] {name},new RowMapper<goods>() {
				public goods mapRow(ResultSet rs,int  rowNum) throws SQLException{
					goods good = new goods();
					good.setGoodsprofile(rs.getString("goodsprofile"));
					return good;
				}
			});
			
			sql = "update ordergoods set goodsprofile = ? where name = ?";
			template.update(sql,new Object[] {good.getGoodsprofile(),name});
		}
		
		
		for(int j=0; j<list.length;j++) {
			sql = "select * from goods where name = ?";
			goods good;
			good = template.queryForObject(sql,new Object[] {list[j]},new RowMapper<goods>() {
				public goods mapRow(ResultSet rs,int rowNum) throws SQLException{
					goods good = new goods();
					good.setRemain(rs.getInt("remain"));
					
					return good;
				}
			});
			
			sql = "update goods set remain = " + (good.getRemain() - glist[j]) + " where name = ?";
			template.update(sql,new Object[] {list[j]});
		}
	}
	 });
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void insertVbankgoods(String merchant_id, String[] list, Integer[] glist) 
	{
	 transactionTemplate1.execute(new TransactionCallbackWithoutResult() {
		@Override
		protected void doInTransactionWithoutResult(TransactionStatus status) {
		//String sql = "insert into ordergoods (merchant_id,name,qty) values (?,?,?)";
		String sql = null;
		String sql2 = null;
		System.out.println("list : " + list);
		System.out.println("glist : " + glist);
		System.out.println("list size : " + list.length);
		String name;
		int g;
		
		for(int i = 0; i < list.length; i++)
		{
			sql = "insert into ordergoods (merchant_id,name,qty) values (?,?,?)";
			name = list[i];
			g = glist[i];
			template.update(sql,new Object[] {merchant_id,name,g});
			//System.out.println("list : " + list[i]);
			sql = "select * from goods where name = ?";
			goods good = null;
			good = template.queryForObject(sql,new Object[] {name},new RowMapper<goods>() {
				public goods mapRow(ResultSet rs,int  rowNum) throws SQLException{
					goods good = new goods();
					good.setGoodsprofile(rs.getString("goodsprofile"));
					return good;
				}
			});
			
			sql = "update ordergoods set goodsprofile = ? where name = ?";
			template.update(sql,new Object[] {good.getGoodsprofile(),name});
		}
		
		/*
		for(int j=0; j<list.length;j++) {
			sql = "select * from goods where name = ?";
			goods good;
			good = template.queryForObject(sql,new Object[] {list[j]},new RowMapper<goods>() {
				public goods mapRow(ResultSet rs,int rowNum) throws SQLException{
					goods good = new goods();
					good.setRemain(rs.getInt("remain"));
					
					return good;
				}
			});
			
			sql = "update goods set remain = " + (good.getRemain() - glist[j]) + " where name = ?";
			template.update(sql,new Object[] {list[j]});
		}
		*/
	}
	 });
	}
	
	public void subordergoodsVbank(String merchant_uid) {
		String sql = "select * from ordergoods where merchant_id = ?";
		List<Ordergoods> ordergoods = null;
		ordergoods = template.query(sql,new Object[] {merchant_uid},new RowMapper<Ordergoods>() {
			public Ordergoods mapRow(ResultSet rs,int RowNum) throws SQLException {
				Ordergoods ordergoods = new Ordergoods();
				ordergoods.setName(rs.getString("name"));
				ordergoods.setQty(rs.getInt("qty"));
				return ordergoods;
			}
		});
		
		for(int j=0; j<ordergoods.size();j++) {
			sql = "select * from goods where name = ?";
			goods good;
			good = template.queryForObject(sql,new Object[] {ordergoods.get(j).getName()},new RowMapper<goods>() {
				public goods mapRow(ResultSet rs,int rowNum) throws SQLException{
					goods good = new goods();
					good.setRemain(rs.getInt("remain"));
					
					return good;
				}
			});
			
			sql = "update goods set remain = " + (good.getRemain() - ordergoods.get(j).getQty()) + " where name = ?";
			template.update(sql,new Object[] {ordergoods.get(j).getName()});
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<Order> getorderinfo(String id,int curPageNum) {
		//String sql = "select *,DATE_SUB(time, INTERVAL 9 Hour) from ordertable where id = ? order by time desc LIMIT ?,?";
		
		String sql = "select *,DATE_ADD(time, INTERVAL 9 Hour) from ordertable where id = ? order by time desc LIMIT ?,?";
		List<Order> orders = null;
		
		orders = template.query(sql,new Object[] {id,6 * (curPageNum - 1),6},new RowMapper<Order>() {
			public Order mapRow(ResultSet rs,int rowNum) throws SQLException{
				Order order = new Order();
				order.setId(rs.getString("id"));
				order.setMerchant_id(rs.getString("merchant_id"));
				order.setPrice(rs.getInt("price"));
				order.setStatus(rs.getString("status"));
				order.setTekbenumber(rs.getString("tekbenumber"));
				order.setPaymethod(rs.getString("paymethod"));
				order.setAddress(rs.getString("address"));
				order.setTime(rs.getTimestamp("DATE_ADD(time, INTERVAL 9 Hour)"));
				
				return order;
			}
		});
		return orders;
	}
	public List<Ordergoods> getordergoods(String merchant_id) {
		String sql = "select * from ordergoods where merchant_id = ?";
		List<Ordergoods> ordergoods = null;
		
		ordergoods = template.query(sql,new Object[] {merchant_id},new RowMapper<Ordergoods>() {
			public Ordergoods mapRow(ResultSet rs,int rowNum) throws SQLException{
				Ordergoods ordergoods = new Ordergoods();
				ordergoods.setName(rs.getString("name"));
				ordergoods.setQty(rs.getInt("qty"));
				ordergoods.setMerchant_id(rs.getString("merchant_id"));
				ordergoods.setGoodsprofile(rs.getString("goodsprofile"));
				
				return ordergoods;
			}
		});
		
		return ordergoods;
	}
	public Vbank getvbankinfo(String merchant_id){
		String sql = "select * from vbank where merchant_id = ?";
		Vbank vbank = null;
	 try 
	 {	
		vbank = template.queryForObject(sql,new Object[] {merchant_id},new RowMapper<Vbank>() {
			public Vbank mapRow(ResultSet rs,int rowNum) throws SQLException{
				Vbank vbank = new Vbank();
				vbank.setMerchant_id(rs.getString("merchant_id"));
				vbank.setVbankname(rs.getString("vbankname"));
				vbank.setVbankdate(rs.getString("vbankdate"));
				vbank.setVbanknum(rs.getString("vbanknum"));
				vbank.setVbankholder(rs.getString("vbankholder"));
				vbank.setVbankperson(rs.getString("vbankperson"));
				vbank.setVbankcode(rs.getString("vbankcode"));
				
				return vbank;
			}
		});
	 }catch(Exception e) 
	 {
		 vbank = new Vbank();
	 }
	 	return vbank;
	}
	public void requestrefund(String merchant_id, Integer amount, String holder, String bank, String account) {
		String sql = "insert into requestrefund (merchant_id,amount,refund_holder,refund_bank,refund_account) values (?,?,?,?,?)";
		template.update(sql,new Object[] {merchant_id,amount,holder,bank,account});
	}
	public Refund getrefund(String merchant_id) {
		String sql = "select * from requestrefund where merchant_id = ?";
		Refund refund = null;
	try {
		refund = template.queryForObject(sql,new Object[] {merchant_id},new RowMapper<Refund>() {
			public Refund mapRow(ResultSet rs,int rowNum) throws SQLException{
				Refund refund = new Refund();
				refund.setMerchant_id(rs.getString("merchant_id"));
				refund.setAmount(rs.getInt("amount"));
				refund.setRefundaccount(rs.getString("refund_account"));
				refund.setRefundbank(rs.getString("refund_bank"));
				refund.setRefundholder(rs.getString("refund_holder"));
				
				return refund;
			}
		});
	}catch(Exception e) {
		return null;
	}
	
		return refund;
	}
	public int remaincheck(String[] newname, Integer[] newqty) {
		String sql = null;
		goods good = null;
		
		for(int i=0;i<newname.length;i++) {
			sql = "select * from goods where name = ?";
			good = template.queryForObject(sql,new Object[] {newname[i]},new RowMapper<goods>() {
				public goods mapRow(ResultSet rs,int rowNum) throws SQLException{
					goods good = new goods();
					good.setRemain(rs.getInt("remain"));
					return good;
				}
			});
			if(good.getRemain() < newqty[i])		//return 0 이면 실패, return 1이면 성공
				return 0;
		}
		return 1;
	}
	public int cartspace(String Id) {
		String sql = "select * from shoppingbasket where user_id = ?";
		List<Shoppingbasket> carts = null;
		
		try {
			carts = template.query(sql,new Object[] {Id},new RowMapper<Shoppingbasket>() {
				public Shoppingbasket mapRow(ResultSet rs,int rowNum) throws SQLException {
					Shoppingbasket basket = new Shoppingbasket();
					basket.setUser_id(rs.getString("user_id"));
					
					return basket;
				}
			});
		} catch(Exception e) {
			return carts.size();
		}
		
		return carts.size();
	}

	public void rollbackdeletemerchantid(String merchant_id) {
		String sql = "delete from ordertable where merchant_id = ?";
		template.update(sql,new Object[] {merchant_id});
	}
	
	public Integer usecoupon(String cnumber) 
	{
	 transactionTemplate1.execute(new TransactionCallbackWithoutResult() {
		 @Override
		 protected void doInTransactionWithoutResult(TransactionStatus status) {
		  try {
			 String sql = "select * from coupon where Id = ?";
			 Coupon coupon = null;
			 try {
				 coupon = template.queryForObject(sql,new Object[] {cnumber},new RowMapper<Coupon>() {
					 public Coupon mapRow(ResultSet rs,int Rownum) throws SQLException {
						 Coupon coupon = new Coupon();
						 coupon.setType(rs.getInt("type"));
						 coupon.setUsecheck(rs.getString("usecheck"));
						 return coupon;
					 }
				 });
			 } catch(Exception e) {
				 couponresult = 10;
			 }
			 
			// if(coupon.getUsecheck().equals("yes"))
			//	 couponresult = 11;
			 
			 if(coupon != null && coupon.getUsecheck().equals("no")) 
			 {
				 System.out.println("아이디 삽입");
				 sql = "insert into usecoupon (Id) values (?)";
				 template.update(sql,new Object[] {cnumber});
				 System.out.println("쿠폰 사용");
				 sql = "update coupon set usecheck = 'yes' where Id = ?";
				 template.update(sql,new Object[] {cnumber});
				 
				 couponresult = coupon.getType();
			 }
			 else 
				 couponresult = 10;
			 
		   }catch(Exception e)
		   {
			  System.out.println("롤백");
			  status.setRollbackOnly();
		   }
		 }
	 });
	 	return couponresult;
	}
	 
	 
	public Integer receivecoupon(String id) {
    transactionTemplate1.execute(new TransactionCallbackWithoutResult() {
    	 @Override
		 protected void doInTransactionWithoutResult(TransactionStatus status) {
		  try {
		String sql = "select * from coupon where user = ? and receive = 'no'";
		List<Coupon> coupons = new ArrayList<Coupon>();
		try {
			coupons = template.query(sql,new Object[] {id},new RowMapper<Coupon>() {
				public Coupon mapRow(ResultSet rs,int rowNum) throws SQLException {
					Coupon coupon = new Coupon();
					coupon.setId(rs.getString("Id"));
					coupon.setReceive(rs.getString("receive"));
					
					return coupon;
				}
			});
		} catch(Exception e) {
			
		}
		
		if(coupons.size() > 0)
		{
			sql = "update coupon set receive = ? where user = ?";
			template.update(sql,new Object[] {"yes",id});
			receivecoupon = 1;
		}
		else
		{
			receivecoupon = 0;
		}
	} catch(Exception e) {
		status.setRollbackOnly();
	}
   }
  });
    return receivecoupon;
  }

	public List<Coupon> getcoupons(String id) {
		final String sql = "select * from coupon where Id = ?";
		List<Coupon> coupons;
		coupons = template.query(sql,new Object[] {id},new RowMapper<Coupon>() {
			public Coupon mapRow(ResultSet rs,int rowNum) throws SQLException {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getString("Id"));
				coupon.setMaketime(rs.getTimestamp("maketime"));
				coupon.setType(rs.getInt("type"));
				coupon.setUsecheck(rs.getString("usecheck"));
				
				return coupon;
			}
		});
		
		return coupons;
	}

	public int getNumberCoupons(String id) {
		final String sql = "select * from coupon where user = ?";
		List<Coupon> coupons = null;
		coupons = template.query(sql,new Object[] {id},new RowMapper<Coupon>() {
			public Coupon mapRow(ResultSet rs,int rowNum) throws SQLException
			{
				Coupon coupon = new Coupon();
				coupon.setId(rs.getString("Id"));
				
				return coupon;
			}
		});
		return coupons.size();
	}

	public List<Coupon> getCurPageCoupons(int curPageNum, String id) 
	{
		String sql = "select R1.* FROM( SELECT * FROM coupon where user = ? and receive = 'yes' order by Id asc) R1 LIMIT ?, ?";
		List<Coupon> coupons = null;
		coupons = template.query(sql,new Object[] {id,6 * (curPageNum - 1),6},new RowMapper<Coupon>() {
			public Coupon mapRow(ResultSet rs,int rowNum) throws SQLException{
				Coupon coupon = new Coupon();
				coupon.setId(rs.getString("Id"));
				coupon.setType(rs.getInt("type"));
				coupon.setUsecheck(rs.getString("usecheck"));
				
				return coupon;
			}
		});
		return coupons;
	}

	public void updateusecouponservice(String yes,String cnumber) 
	{
		transactionTemplate2.execute(new TransactionCallbackWithoutResult() {
		 @Override
		 protected void doInTransactionWithoutResult(TransactionStatus status) {
			try {
			 String sql = "select * from coupon where Id = ?";
			 Coupon coupon;
			 coupon = template.queryForObject(sql,new Object[] {cnumber},new RowMapper<Coupon>() {
				public Coupon mapRow(ResultSet rs,int rowNum) throws SQLException {
					Coupon coupon = new Coupon();
					coupon.setUsecheck(rs.getString("usecheck"));
					
					return coupon;
				}
			 });
			 
			if(coupon.getUsecheck().equals("no")) 
			{	
				System.out.println("쿠폰 사용");
				sql = "update coupon set usecheck = ? where Id = ?";
				template.update(sql,new Object[] {yes,cnumber});
			}
		}catch(Exception e) {
			System.out.println("롤백");
			status.setRollbackOnly();
		}
	}
  });
 }

	public void updatediscountpercent(String couponId,String merchant_id) {
		final String sql = "update ordertable set couponId = ? where merchant_id = ?";
		template.update(sql,new Object[] {couponId,merchant_id});
	}

	public User getJeongbo(String id) {
		 String sql = "select * from user where Id = ?";
		 User user = null;
		 user = template.queryForObject(sql,new Object[] {id},new RowMapper<User>() {
			public User mapRow(ResultSet rs,int rowNum) throws SQLException {
				User user = new User();
				user.setAddress(rs.getString("address"));
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));
				user.setPhoneNumber(rs.getString("phonenumber"));
				
				return user;
			}
		 });
		return user;
	}

	public void deleteall(String id) {
		String sql = "delete from shoppingbasket where user_id = ?";
		template.update(sql,new Object[]{id});
	}

	public List<Reply> commentlist(String gId) 
	{
		String sql = "select * from reply where gId = ? order by rId";
		
		List<Reply> replies;
		replies = template.query(sql,new Object[] {gId},new RowMapper<Reply>() {
			@Override
			public Reply mapRow(ResultSet rs, int rowNum) throws SQLException {
				Reply reply = new Reply();
				reply.setGid(rs.getString("gId"));
				reply.setContent(rs.getString("content"));
				reply.setUser_id(rs.getString("user_id"));
				reply.setRid(rs.getInt("rId"));
				return reply;
			}
		});
		return replies;
	}

	public boolean addComment(String gId, String user_id, String reply) {
		String sql = "insert into reply (gId,user_id,content) values (?,?,?)";
		template.update(sql,new Object[] {gId,user_id,reply});
		return true;
	}

	public void contentreplymodify(String gId, String rId, String content) 
	{
		String sql = "update reply set content = ? where gId = ? and rId = ?";
		template.update(sql,new Object[] {content,gId,Integer.valueOf(rId)});
	}

	public void contentreplydelete(String gId, String rId) 
	{
		String sql = "delete from reply where gId = ? and rId = ?";
		template.update(sql,new Object[] {gId,Integer.valueOf(rId)});
	}

	public int getNumberReply(String gId) 
	{
		final String sql = "select * from reply where gId = ?";
		List<Reply> replies = null;
		replies = template.query(sql,new Object[] {gId},new RowMapper<Reply>() {
			public Reply mapRow(ResultSet rs,int rowNum) throws SQLException
			{
				Reply reply = new Reply();
				reply.setRid(rs.getInt("rId"));
				return reply;
			}
		});
		return replies.size();
	}

	public List<Reply> getCurPageReplies(int curPageNum, String gId) {
		String sql = "select R1.* FROM( SELECT * FROM reply where gId = ? order by rId desc) R1 LIMIT ?, ?";
		List<Reply> replies = null;
		replies = template.query(sql,new Object[] {gId,6 * (curPageNum - 1),6},new RowMapper<Reply>() {
			public Reply mapRow(ResultSet rs,int rowNum) throws SQLException{
				Reply reply = new Reply();
				reply.setContent(rs.getString("content"));
				reply.setUser_id(rs.getString("user_id"));
				reply.setRid(rs.getInt("rId"));
				reply.setGid(rs.getString("gId"));
				
				return reply;
			}
		});
		return replies;
	}

	public boolean mobilecheck(String merchant_uid) {
		String sql = "select * from ordergoods where merchant_id = ?";
		Order order = null;
		try {
		order = template.queryForObject(sql,new Object[] {merchant_uid},new RowMapper<Order>() {
			public Order mapRow(ResultSet rs,int rowNum) throws SQLException {
				Order order = new Order();
				order.setMerchant_id(rs.getString("merchant_id"));
				
				return order;
			}
			});
			return true;
		} catch(EmptyResultDataAccessException e) {
			return false;
		}
	}
}
