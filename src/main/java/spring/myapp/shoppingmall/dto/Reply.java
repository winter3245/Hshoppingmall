package spring.myapp.shoppingmall.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Reply 
{
	private String gid;
	private int rid;
	private String user_id;
	private String content;
}
