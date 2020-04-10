package spring.myapp.shoppingmall.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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
public class InsertVbank {
	@Autowired
	private MallDao Malldao;
	@Autowired
	TransactionTemplate transactionTemplate2;
	@Autowired
	TransactionTemplate transactionTemplate3;
	
	public void InsertVbank(String merchant_id,String vbanknum,String vbankname,String vbankdate,String vbankholder,String vbankperson,String vbankcode)
	{
		Malldao.insertvbank(merchant_id,vbanknum,vbankname,vbankdate,vbankholder,vbankperson,vbankcode);
	}

	public void InsertVbankAndUpdateStatus(String merchant_uid, String vbanknum, String vbankname, String vbankdate,
			String vbankholder, String buyer_name, String vbank_code, String status, String imp_uid, String paymethod,
			String merchant_id, String[] list, Integer[] integerilist, String price) 
	{
	transactionTemplate2.execute(new TransactionCallbackWithoutResult(){
	  public void doInTransactionWithoutResult(TransactionStatus transstatus) {
	   try {
		//Malldao.insertgoods(merchant_id,list,integerilist);
		Malldao.insertVbankgoods(merchant_id, list, integerilist);
		Malldao.insertPrice(Integer.valueOf(price),merchant_uid);
		Malldao.insertvbank(merchant_uid, vbanknum, vbankname, vbankdate, vbankholder, buyer_name, vbank_code);
		Malldao.statusupdate(status,merchant_uid,imp_uid,paymethod);
	   } catch(Exception e) {
			 System.out.println("merchant_id in Rollback : " + merchant_id);
			 transstatus.setRollbackOnly();
	   }
	 }
  });
	/*
	transactionTemplate3.execute(new TransactionCallbackWithoutResult() {
		@Override
		protected void doInTransactionWithoutResult(TransactionStatus transstatus) {
				 if(Malldao.getMerchantid(merchant_id).getStatus().equals("not paid")) {
					 Malldao.statusupdate("vbank error",merchant_uid,imp_uid,paymethod);
					 System.out.println("rollback ¼º°ø :" + merchant_id);
				 }
			}
  });
  */
 }
}
