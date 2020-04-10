package spring.myapp.shoppingmall.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Shoppingbasket 
{
	private int pnum;
	private int goods_id;
	private String user_id;
	private int price;
	private String thumbnail;
	private String name;
	private int qty;
}