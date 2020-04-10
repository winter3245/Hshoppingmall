package spring.myapp.shoppingmall.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Refund 
{
	private String merchant_id;
	private int amount;
	private String refundholder;
	private String refundbank;
	private String refundaccount;
}
