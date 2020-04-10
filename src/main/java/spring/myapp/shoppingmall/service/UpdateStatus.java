package spring.myapp.shoppingmall.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import spring.myapp.shoppingmall.controller.MallController;
import spring.myapp.shoppingmall.dao.MallDao;

@Service
public class UpdateStatus 
{
	@Autowired
	private MallDao Malldao;
	@Autowired
	TransactionTemplate transactionTemplate2;
	@Autowired
	TransactionTemplate transactionTemplate3;
	
	public void updatestatus(String updatestatus,String merchant_uid,String imp_uid,String paymethod,String price,String vbankholder,String vbanknum,String vbankcode)
	{	
		transactionTemplate2.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transstatus) {
			try {
				Malldao.statusupdate(updatestatus,merchant_uid,imp_uid,paymethod);
				////////////////////////////////////////////////// 수정중
				Malldao.subordergoodsVbank(merchant_uid);
				//////////////////////////////////////////////////
			} catch(Exception e) {
				 String cancelstatus = null;
				  try {
					JSONObject json = new JSONObject();
					String imp_key = URLEncoder.encode("2645427372556228", "UTF-8");
					String imp_secret =	URLEncoder.encode("75USkRpzuQ8T8WeQcJrO1GFKEERYRDAYIuR2lgCQ6LKfHY5THxIJenuS2mRTZsSHWJKiZm967TlPRrJz", "UTF-8");
					json.put("imp_key",imp_key);
					json.put("imp_secret", imp_secret);
					System.out.println(json);
					System.out.println("imp_key : " + json.get("imp_key"));
					System.out.println("imp_secret : " + json.get("imp_secret"));
					
					JSONObject obj = new JSONObject();
					String token = MallController.getToken(null,null,json,"https://api.iamport.kr/users/getToken");
					String reason;
					
					int amount = Integer.valueOf(price); //Malldao.getfindprice(merchant_uid)
					int cancel_request_amount = Integer.valueOf(price);
					
					if(amount - cancel_request_amount <= 0)
						System.out.println("이미 전액환불된 상품입니다.");
					
					obj.put("imp_uid",imp_uid); //Malldao.getimp_uid(merchant_uid)
					obj.put("cancel_request_amount",price);
					obj.put("refund_holder",vbankholder);
					obj.put("refund_bank",vbankcode);
					obj.put("refund_account",vbanknum);
					
					JSONObject getcanceldata = null;		//아임포트 서버에서 환불 받은 정보
					
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
					}catch(Exception ex) {
						ex.printStackTrace();
					}
					}catch(Exception ex){
						ex.printStackTrace();
					}
					
					System.out.println("환불 정보 : " + getcanceldata);
					 cancelstatus = String.valueOf(getcanceldata.get("status"));
					 //deletemerchantid(merchant_id);
				  } catch (Exception excep) {
					  excep.printStackTrace();
				  }
				  
				  //Malldao.statusupdatecancel(merchant_id,cancelstatus);
			///////////////////////////////////////////////////////////
				 System.out.println("merchant_id in Rollback : " + merchant_uid);
				 transstatus.setRollbackOnly();    
			}
		  }
		});
	}

	public void updatestatusandorder(String status, String merchant_uid, String imp_uid, String paymethod,
			String merchant_id, String[] list, Integer[] integerilist, String price){
	transactionTemplate2.execute(new TransactionCallbackWithoutResult() {
		@Override
		protected void doInTransactionWithoutResult(TransactionStatus transstatus) {
				try {
					Malldao.insertgoods(merchant_id,list,integerilist);
					Malldao.insertPrice(Integer.valueOf(price),merchant_uid);
					Malldao.statusupdate(status,merchant_uid,imp_uid,paymethod);
			 } catch(Exception e) {
				 String cancelstatus = null;
				  try {
					JSONObject json = new JSONObject();
					String imp_key = URLEncoder.encode("2645427372556228", "UTF-8");
					String imp_secret =	URLEncoder.encode("75USkRpzuQ8T8WeQcJrO1GFKEERYRDAYIuR2lgCQ6LKfHY5THxIJenuS2mRTZsSHWJKiZm967TlPRrJz", "UTF-8");
					json.put("imp_key",imp_key);
					json.put("imp_secret", imp_secret);
					System.out.println(json);
					System.out.println("imp_key : " + json.get("imp_key"));
					System.out.println("imp_secret : " + json.get("imp_secret"));
					
					JSONObject obj = new JSONObject();
					String token = MallController.getToken(null,null,json,"https://api.iamport.kr/users/getToken");
					String reason;
					
					int amount = Integer.valueOf(price); //Malldao.getfindprice(merchant_uid)
					int cancel_request_amount = Integer.valueOf(price);
					
					if(amount - cancel_request_amount <= 0)
						System.out.println("이미 전액환불된 상품입니다.");
					
					obj.put("imp_uid",imp_uid); //Malldao.getimp_uid(merchant_uid)
					obj.put("cancel_request_amount",price);
					
					JSONObject getcanceldata = null;		//아임포트 서버에서 환불 받은 정보
					
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
					}catch(Exception ex) {
						ex.printStackTrace();
					}
					}catch(Exception ex){
						ex.printStackTrace();
					}
					
					System.out.println("환불 정보 : " + getcanceldata);
					 cancelstatus = String.valueOf(getcanceldata.get("status"));
					 //deletemerchantid(merchant_id);
				  } catch (Exception excep) {
					  excep.printStackTrace();
				  }
				  
				  //Malldao.statusupdatecancel(merchant_id,cancelstatus);
			///////////////////////////////////////////////////////////
				 System.out.println("merchant_id in Rollback : " + merchant_id);
				 transstatus.setRollbackOnly();
			 }
	  }
  });
	transactionTemplate3.execute(new TransactionCallbackWithoutResult() {
		@Override
		protected void doInTransactionWithoutResult(TransactionStatus transstatus) {
			try {
				 if(Malldao.getMerchantid(merchant_id).getStatus().equals("not paid")) {
					 //Malldao.statusupdate("cancelled",merchant_uid,imp_uid,paymethod);
					 System.out.println("rollback 성공 :" + merchant_id);
					 Malldao.rollbackdeletemerchantid(merchant_id);
				 }
			} catch(Exception e) {
				transstatus.setRollbackOnly();
				e.printStackTrace();
			}
		}
	});
  }
}
