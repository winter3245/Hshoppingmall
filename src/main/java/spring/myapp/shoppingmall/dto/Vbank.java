package spring.myapp.shoppingmall.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Vbank {
	private String merchant_id;
	private String vbanknum;
	private String vbankdate;
	private String vbankname;
	private String vbankholder;
	private String vbankperson;
	private String vbankcode;
}
