package spring.myapp.shoppingmall.controller;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import spring.myapp.shoppingmall.dto.User;
import spring.myapp.shoppingmall.service.CheckCommand;
import spring.myapp.shoppingmall.service.JoinUser;
import spring.myapp.shoppingmall.service.UpdatePasswordCommand;
import spring.myapp.shoppingmall.service.authIdCommand;
import spring.myapp.shoppingmall.service.authPwCommand;
import spring.myapp.shoppingmall.service.checkNowPasswordCommand;
import spring.myapp.shoppingmall.service.findUserId;
import spring.myapp.shoppingmall.service.findUserPw;
import spring.myapp.shoppingmall.service.repasswordCommand;

@Controller
public class UserController 
{
	@Autowired
	private JoinUser joinuser;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private authIdCommand authId;
	
	@Autowired
	private authPwCommand authPw;
	
	@Autowired
	private findUserId finduserid;
	
	@Autowired
	private findUserPw finduserpw;
	
	@Autowired
	private repasswordCommand repwCommand;
	
	@Autowired
	private CheckCommand checkCommand;
	
	@Autowired
	private UpdatePasswordCommand updatePasswordCommand;
	
	@Autowired
	private checkNowPasswordCommand checknowPasswordCommand;
	
	@RequestMapping("/joinform")
	public String JoinForm(Model model) {
		model.addAttribute("user",new User());
		return "joinform";
	}
	
	@RequestMapping("/loginForm") //�α��� ��
	public String loginForm(@RequestParam(value = "error",required = false) String error,@RequestParam(value = "logout",required = false) String logout,Model model,@CookieValue(value = "UserId",required = false) Cookie cookie,@RequestParam(value = "duplsession",required = false) String duplsession)
	{			
		if(cookie != null)
		{
			User user = new User();
			user.setId(cookie.getValue());
			model.addAttribute("User",user);
		}
		
		if(error != null)
			model.addAttribute("error","���̵� �Ǵ� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
		if(logout != null)
			model.addAttribute("logout","�α׾ƿ� �Ǿ����ϴ�.");
		if(duplsession != null)
			model.addAttribute("duplsession","�ߺ� �α����� �Ͽ����ϴ�.");
		
		return "loginForm";
	}
	
	@RequestMapping(value = "/join",method = RequestMethod.POST)
	public String join(HttpServletRequest request,@Valid User user,BindingResult result)
	{
		if(result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			
			for(ObjectError error : errors) 
				System.out.println(error.getDefaultMessage());
			return "joinform";
		}
		
		joinuser.joinuser(request);
		return "redirect:/loginForm";
	}
	
	@RequestMapping("findId")
	public String findId(HttpServletRequest request)
	{
		return "email_findId";
	}
	
	@RequestMapping("findPassword")
	public String findPassword(HttpServletRequest request)
	{
		return "email_findPw";
	}
	
	@RequestMapping(value = "/auth",method = RequestMethod.POST)
	@ResponseBody
	public Integer auth(HttpServletResponse response_email,@RequestParam String e_mail,@RequestParam String phoneNumber,@RequestParam String name,Model model) throws IOException //(DB�� �ִ� �̸�,�ڵ�����ȣ,�̸��Ϸ� ��ȸ)���� DB�� ����Ǿ� �ִ� �̸����� �´ٸ�,String �̸����� ������,�̸��Ͽ� ���� ��ȣ�� ������.���� DB�� ������ ��ġ���� ������,�׷��� ������ null���� ������
	{
		if(authId.authId(e_mail, phoneNumber, name))
		{
			Random r = new Random();
			int dice = r.nextInt(4589362) + 49311; // �̸��Ϸ� �޴� �����ڵ� �κ� (����)

			String setfrom = "1692078@gmail.com";
			String title = "���� �̸��� �Դϴ�."; // ����
			String content = //���� ����
					System.getProperty("line.separator") + // ���پ� �ٰ����� �α����� �ۼ�
					System.getProperty("line.separator") +
					"�ȳ��ϼ��� ȸ���� ���� Ȩ�������� ã���ּż� �����մϴ�" +
					System.getProperty("line.separator") +
					System.getProperty("line.separator") +
					" ������ȣ�� " + dice + " �Դϴ�. " +
					System.getProperty("line.separator") +
					System.getProperty("line.separator") +
					"������ ������ȣ�� Ȩ�������� �Է��� �ֽø� �������� �Ѿ�ϴ�."; // ����
			try 
			{
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

				messageHelper.setFrom(setfrom); // �����»�� �����ϸ� �����۵��� ����
				messageHelper.setTo(e_mail);
				messageHelper.setSubject(title); // ���������� ������ �����ϴ�
				messageHelper.setText(content); // ���� ����

				mailSender.send(message);
			} catch (Exception e) {
				System.out.println(e);
			}
			
			model.addAttribute("datacheck","������ �����Ͽ����ϴ�.");
			return dice; //�̸��� ���� ����
		}
		else
		{
			//return null; //�̸��� ���� ����
			return 0;
		}
	}
	
	
	@RequestMapping(value = "/authpw",method = RequestMethod.POST)
	@ResponseBody
	public Integer authpw(HttpServletResponse response_email,@RequestParam String e_mail,@RequestParam String phoneNumber,@RequestParam String name,@RequestParam String cId,Model model) throws IOException //(DB�� �ִ� �̸�,�ڵ�����ȣ,�̸��Ϸ� ��ȸ)���� DB�� ����Ǿ� �ִ� �̸����� �´ٸ�,String �̸����� ������,�̸��Ͽ� ���� ��ȣ�� ������.���� DB�� ������ ��ġ���� ������,�׷��� ������ null���� ������
	{
		if(authPw.authPw(cId,e_mail, phoneNumber, name))
		{
			Random r = new Random();
			int dice = r.nextInt(4589362) + 49311; // �̸��Ϸ� �޴� �����ڵ� �κ� (����)

			String setfrom = "1692078@gmail.com";
			String title = "���� �̸��� �Դϴ�."; // ����
			String content = //���� ����
					System.getProperty("line.separator") + // ���پ� �ٰ����� �α����� �ۼ�
					System.getProperty("line.separator") +
					"�ȳ��ϼ��� ȸ���� ���� Ȩ�������� ã���ּż� �����մϴ�" +
					System.getProperty("line.separator") +
					System.getProperty("line.separator") +
					" ������ȣ�� " + dice + " �Դϴ�. " +
					System.getProperty("line.separator") +
					System.getProperty("line.separator") +
					"������ ������ȣ�� Ȩ�������� �Է��� �ֽø� �������� �Ѿ�ϴ�."; // ����
			try 
			{
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

				messageHelper.setFrom(setfrom); // �����»�� �����ϸ� �����۵��� ����
				messageHelper.setTo(e_mail);
				messageHelper.setSubject(title); // ���������� ������ �����ϴ�
				messageHelper.setText(content); // ���� ����

				mailSender.send(message);
			} catch (Exception e) {
				System.out.println(e);
			}
			
			//model.addAttribute("datacheck","������ �����Ͽ����ϴ�.");
			return dice; //�̸��� ���� ����
		}
		else
		{
			model.addAttribute("error","������ ��ȿ���� �ʽ��ϴ�.�ٽ� �Է����ֽʽÿ�.");
			return 0; //�̸��� ���� ����
		}
	}
	
	@RequestMapping(value = "/findUserId",method = RequestMethod.POST)
	public String findUserId(@RequestParam String name,@RequestParam String phoneNumber,Model model)
	{
		finduserid.find(name,phoneNumber,model);
		return "findIdResult";
	}
	
	@RequestMapping(value = "/findUserPw",method = RequestMethod.POST)
	public String findUserPw(@RequestParam String cId,Model model)
	{
		finduserpw.find(cId,model);
		
		return "findPwResult";
	}
	
	@RequestMapping(value = "/repassword",method = RequestMethod.POST)
	public String repassword(@RequestParam String userId,@RequestParam String repw)
	{
		repwCommand.execute(userId,repw);
		
		return "redirect:/home";
	}
	
	@RequestMapping("/check")
	@ResponseBody
	public int check(HttpServletRequest request,@RequestParam String id) 
	{
		int count = 0;
		count = checkCommand.execute(id); 
		return count;
	}
	
	@RequestMapping("/updatePasswordForm")
	public String updatePasswordForm()
	{
		return "updatePasswordForm";
	}
	
	@RequestMapping(value = "/updatePassword",method = RequestMethod.POST)
	public ModelAndView updatePassword(@RequestParam String Userid,@RequestParam String password,@RequestParam String nowPassword,RedirectAttributes redirectAttributes)
	{
		if(!checknowPasswordCommand.checkNowPassword(Userid, nowPassword))
		{
			ModelAndView mv = new ModelAndView(); 
			mv.setViewName("redirect:/updatePasswordForm"); 
			redirectAttributes.addFlashAttribute("error","���� ��й�ȣ�� Ʋ�Ƚ��ϴ�");
			return mv;
		}
		updatePasswordCommand.update(Userid,password);
		ModelAndView mv = new ModelAndView(); 
		mv.setViewName("home"); 
		
		return mv;
	}
}
