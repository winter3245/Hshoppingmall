package spring.myapp.shoppingmall.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Service;

@Service
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private String loginidname;
	private String defaultUrl;
 
    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStratgy = new DefaultRedirectStrategy();

    protected void resultRedirectStrategy(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
         SavedRequest savedRequest = requestCache.getRequest(request, response);
    	//HttpSession session = request.getSession();
    	//SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUES");
    	System.out.println("savedRequest: "+savedRequest); //savedRequest: DefaultSavedRequest[http://localhost:8090/myapp/board/list?bBoard=AngularJS]

    	
        if(savedRequest!=null) //���� ������ ������ �α��� ������ �̵�
        {
            String targetUrl = savedRequest.getRedirectUrl(); //http://localhost:8090/myapp/board/list?bBoard=AngularJS
            redirectStratgy.sendRedirect(request, response, targetUrl); 
        } else {			//���� ������ URL�� �α��� ���� ����
            redirectStratgy.sendRedirect(request, response, defaultUrl);
        }
    }
    
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session==null) return;
        	session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
    		Authentication authentication) throws IOException, ServletException  //�α��� �����Ŀ� ȣ��� �޼ҵ�
    {
    	clearAuthenticationAttributes(request);
    	request.getSession().setAttribute("Userid",request.getParameter("id"));
    	if(request.getParameter("isRemember") != null && request.getParameter("isRemember").equals("on"))
    	{
    		Cookie cookie = new Cookie("UserId",request.getParameter("id"));
    		cookie.setMaxAge(60*60*24*30);
    		response.addCookie(cookie);
    	}
    	resultRedirectStrategy(request, response, authentication);
    }
 
    public String getLoginidname() {
        return loginidname;
    }
 
    public void setLoginidname(String loginidname) {
        this.loginidname = loginidname;
    }
 
    public String getDefaultUrl() {
        return defaultUrl;
    }
 
    public void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }
}
