package spring.myapp.shoppingmall.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Order 
{
	private String merchant_id;
	private String phoneNumber;
	private String address;
	private int price;
	private String status;
	private String imp_uid;
	private String id;
	private String paymethod;
	private String tekbenumber;
	private Timestamp time;
}
