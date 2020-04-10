package spring.myapp.shoppingmall.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Coupon {
	private String Id;
	private String usecheck;
	private Timestamp maketime;
	private int type;
	private String receive;
}
