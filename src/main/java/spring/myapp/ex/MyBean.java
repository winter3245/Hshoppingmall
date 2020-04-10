package spring.myapp.ex;

public class MyBean 
{
	private String beanId;
	
	public MyBean(String beanId) {
		this.beanId = beanId;
	}
	
	public String getBeanId() {
		return this.beanId;
	}
}