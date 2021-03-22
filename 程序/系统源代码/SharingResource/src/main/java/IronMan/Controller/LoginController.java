package IronMan.Controller;

import IronMan.Model.FileCategory;
import IronMan.Model.User;
import IronMan.Service.FileCategoryService;
import IronMan.Service.UserService;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;

@Controller
public class LoginController
{
	@Autowired
	UserService userService;
	@Autowired
	FileCategoryService fileCategoryService;
	@RequestMapping(value = "/", method = { RequestMethod.POST, RequestMethod.GET })
	public String begin()
	{
		return "login";
	}
	@RequestMapping(value = "/gotoLogin", method = { RequestMethod.POST, RequestMethod.GET })
	public String gotoLogin(HttpSession session)
	{
		Enumeration em = session.getAttributeNames();
		while(em.hasMoreElements()){
			session.removeAttribute(em.nextElement().toString());
		}
		return "login";
	}

	@RequestMapping(value = "/gotoMain", method = { RequestMethod.POST, RequestMethod.GET })
	public String gotoMain(@Param("type") String type) {
		if (type.compareTo("1")==0)
			return "backMain/main";
		return "home";
	}
	@ResponseBody
	@RequestMapping("/login")//登陆
	public JSONObject login(HttpSession session,@Param("email") String email,@Param("password") String password)
	{
		User user=userService.selectByEmailAndPassword(email,password);
		JSONObject temp=new JSONObject();
		if (user!=null)//正确登录
		{
			List<FileCategory> allFileCategory=fileCategoryService.selectAll();
			session.setAttribute("allFileCategory",allFileCategory);
			session.setAttribute("username",user.getUsername());
			session.setAttribute("currentUserId",user.getId());
			temp.put("code",2);//成功登陆
			if (user.getCategory().compareTo("Admin")==0)//管理员登录
			{
				temp.put("type",1);
				return temp;
			}
			temp.put("type",2);//普通用户登陆
			return temp;
		}
		else {
			temp.put("code",1);//登陆失败
			return temp;
		}

	}
}
