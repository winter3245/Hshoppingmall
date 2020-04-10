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
	@NotNull(message = "�ּ� �ټ� ���� �̻� �� ���� ���� ���̵� �Է��ϼž� �մϴ�.")
	@Size(min =5,max = 10,message = "�ּ� �ټ� ���� �̻� �� ���� ���� ���̵� �Է��Ͽ��� �մϴ�.")
	private String Id;
	@NotNull(message = "�ּ� �ټ� ���� �̻� �� ���� ���� ��й�ȣ�� �Է��ϼž� �մϴ�.")
	@Size(min =5,max = 10,message = "�ּ� �ټ� ���� �̻� �� ���� ���� ��й�ȣ�� �Է��Ͽ��� �մϴ�.")
	private String Password;
	@NotNull(message = "�̸��� �Է��ϼž� �մϴ�.")
	@Size(min =2,message = "�ּ� �� ���� �̻� �̸��� �Է��Ͽ��� �մϴ�.")
	private String Name;
	@NotEmpty(message = "�ּҸ� �Է��ϼž� �մϴ�.")
	private String Address;
	@NotEmpty(message = "������ �Է��ϼž� �մϴ�.")
	private String Sex;
	@NotNull(message = "���̸� �Է��ϼž� �մϴ�.")
	private int Age;
	@NotEmpty(message = "��ȭ��ȣ�� �Է����ֽñ� �ٶ��ϴ�.")
	private String PhoneNumber;
	@NotEmpty(message = "�̸����� �Է����ֽñ� �ٶ��ϴ�.")
	private String email;
}