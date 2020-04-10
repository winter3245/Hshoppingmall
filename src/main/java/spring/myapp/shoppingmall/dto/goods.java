package spring.myapp.shoppingmall.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class goods 
{
	private int Id;
	private String Name;
	private int Price;
	private String Kind;
	private int Remain;
	private String Goodsprofile;
	private String Goodscontent;
	private String writer;
	private String wcompany;
	private String tcontent;
}