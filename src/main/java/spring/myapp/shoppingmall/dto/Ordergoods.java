package spring.myapp.shoppingmall.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Ordergoods 
{
	private String merchant_id;
	private String name;
	private int qty;
	private String goodsprofile;
}
