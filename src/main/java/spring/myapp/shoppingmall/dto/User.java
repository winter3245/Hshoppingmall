package spring.myapp.shoppingmall.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class User 
{
	@NotNull(message = "최소 다섯 글자 이상 열 글자 이하 아이디를 입력하셔야 합니다.")
	@Size(min =5,max = 10,message = "최소 다섯 글자 이상 열 글자 이하 아이디를 입력하여야 합니다.")
	private String Id;
	@NotNull(message = "최소 다섯 글자 이상 열 글자 이하 비밀번호를 입력하셔야 합니다.")
	@Size(min =5,max = 10,message = "최소 다섯 글자 이상 열 글자 이하 비밀번호를 입력하여야 합니다.")
	private String Password;
	@NotNull(message = "이름을 입력하셔야 합니다.")
	@Size(min =2,message = "최소 두 글자 이상 이름을 입력하여야 합니다.")
	private String Name;
	@NotEmpty(message = "주소를 입력하셔야 합니다.")
	private String Address;
	@NotEmpty(message = "성별을 입력하셔야 합니다.")
	private String Sex;
	@NotNull(message = "나이를 입력하셔야 합니다.")
	private int Age;
	@NotEmpty(message = "전화번호를 입력해주시기 바랍니다.")
	private String PhoneNumber;
	@NotEmpty(message = "이메일을 입력해주시기 바랍니다.")
	private String email;
}