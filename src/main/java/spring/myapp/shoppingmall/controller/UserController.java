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
	
	@RequestMapping("/loginForm") //로그인 폼
	public String loginForm(@RequestParam(value = "error",required = false) String error,@RequestParam(value = "logout",required = false) String logout,Model model,@CookieValue(value = "UserId",required = false) Cookie cookie,@RequestParam(value = "duplsession",required = false) String duplsession)
	{			
		if(cookie != null)
		{
			User user = new User();
			user.setId(cookie.getValue());
			model.addAttribute("User",user);
		}
		
		if(error != null)
			model.addAttribute("error","아이디 또는 비밀번호가 일치하지 않습니다.");
		if(logout != null)
			model.addAttribute("logout","로그아웃 되었습니다.");
		if(duplsession != null)
			model.addAttribute("duplsession","중복 로그인을 하였습니다.");
		
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
	public Integer auth(HttpServletResponse response_email,@RequestParam String e_mail,@RequestParam String phoneNumber,@RequestParam String name,Model model) throws IOException //(DB에 있는 이름,핸드폰번호,이메일로 조회)만약 DB에 저장되어 있는 이메일이 맞다면,String 이메일을 보내고,이메일에 인증 번호를 보낸다.만약 DB에 정보가 일치하지 않으면,그렇지 않으면 null값을 보낸다
	{
		if(authId.authId(e_mail, phoneNumber, name))
		{
			Random r = new Random();
			int dice = r.nextInt(4589362) + 49311; // 이메일로 받는 인증코드 부분 (난수)

			String setfrom = "1692078@gmail.com";
			String title = "인증 이메일 입니다."; // 제목
			String content = //메일 내용
					System.getProperty("line.separator") + // 한줄씩 줄간격을 두기위해 작성
					System.getProperty("line.separator") +
					"안녕하세요 회원님 저희 홈페이지를 찾아주셔서 감사합니다" +
					System.getProperty("line.separator") +
					System.getProperty("line.separator") +
					" 인증번호는 " + dice + " 입니다. " +
					System.getProperty("line.separator") +
					System.getProperty("line.separator") +
					"받으신 인증번호를 홈페이지에 입력해 주시면 다음으로 넘어갑니다."; // 내용
			try 
			{
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

				messageHelper.setFrom(setfrom); // 보내는사람 생략하면 정상작동을 안함
				messageHelper.setTo(e_mail);
				messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
				messageHelper.setText(content); // 메일 내용

				mailSender.send(message);
			} catch (Exception e) {
				System.out.println(e);
			}
			
			model.addAttribute("datacheck","인증을 성공하였습니다.");
			return dice; //이메일 인증 성공
		}
		else
		{
			//return null; //이메일 인증 실패
			return 0;
		}
	}
	
	
	@RequestMapping(value = "/authpw",method = RequestMethod.POST)
	@ResponseBody
	public Integer authpw(HttpServletResponse response_email,@RequestParam String e_mail,@RequestParam String phoneNumber,@RequestParam String name,@RequestParam String cId,Model model) throws IOException //(DB에 있는 이름,핸드폰번호,이메일로 조회)만약 DB에 저장되어 있는 이메일이 맞다면,String 이메일을 보내고,이메일에 인증 번호를 보낸다.만약 DB에 정보가 일치하지 않으면,그렇지 않으면 null값을 보낸다
	{
		if(authPw.authPw(cId,e_mail, phoneNumber, name))
		{
			Random r = new Random();
			int dice = r.nextInt(4589362) + 49311; // 이메일로 받는 인증코드 부분 (난수)

			String setfrom = "1692078@gmail.com";
			String title = "인증 이메일 입니다."; // 제목
			String content = //메일 내용
					System.getProperty("line.separator") + // 한줄씩 줄간격을 두기위해 작성
					System.getProperty("line.separator") +
					"안녕하세요 회원님 저희 홈페이지를 찾아주셔서 감사합니다" +
					System.getProperty("line.separator") +
					System.getProperty("line.separator") +
					" 인증번호는 " + dice + " 입니다. " +
					System.getProperty("line.separator") +
					System.getProperty("line.separator") +
					"받으신 인증번호를 홈페이지에 입력해 주시면 다음으로 넘어갑니다."; // 내용
			try 
			{
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

				messageHelper.setFrom(setfrom); // 보내는사람 생략하면 정상작동을 안함
				messageHelper.setTo(e_mail);
				messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
				messageHelper.setText(content); // 메일 내용

				mailSender.send(message);
			} catch (Exception e) {
				System.out.println(e);
			}
			
			//model.addAttribute("datacheck","인증을 성공하였습니다.");
			return dice; //이메일 인증 성공
		}
		else
		{
			model.addAttribute("error","정보가 유효하지 않습니다.다시 입력해주십시오.");
			return 0; //이메일 인증 실패
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
			redirectAttributes.addFlashAttribute("error","현재 비밀번호가 틀렸습니다");
			return mv;
		}
		updatePasswordCommand.update(Userid,password);
		ModelAndView mv = new ModelAndView(); 
		mv.setViewName("home"); 
		
		return mv;
	}
}
