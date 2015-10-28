package com.itheima.core.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itheima.common.core.bean.user.Buyer;
import com.itheima.common.utils.RequestUtils;
import com.itheima.common.utils.SecurityUtils;
import com.itheima.core.service.user.BuyerService;
import com.itheima.core.service.user.SessionProvider;

@Controller
public class LoginController {

	
	@Autowired
	private SessionProvider sessionProvider;
	
	@Autowired
	private BuyerService buyerService;
	
	@RequestMapping(value="/shopping/login.aspx",method=RequestMethod.GET)
	public String login(){
		
		return "buyer/login";
	}
	
	@RequestMapping(value="/shopping/login.aspx",method=RequestMethod.POST)
	public String login(String username,String password,String code,String returnUrl,HttpServletResponse response,HttpServletRequest request,Model model){
				//获取cookie为csessionid
		String csessionid=getCSESSIONID(response, request);
							//1.验证码不能为空
			if(null != code){
						//2.验证码正确
					String c=sessionProvider.getAttributeCode(csessionid);
				if(code.equalsIgnoreCase(c)){
					if(username != null){
							Buyer buyer = buyerService.selectBuyerByUsername(username);
						if(buyer !=null){
							if( password != null){
										//把输入密码进行加密
								String encodePassword = SecurityUtils.encodePassword(password);
								if(buyer.getPassword().equals(encodePassword)){
										sessionProvider.setAttributeForUsername(csessionid, buyer.getUsername());
										return "redirect:"+returnUrl;
								
								}else{
									model.addAttribute("error", "密码不能为空");
									return "buyer/login";
									
								}
							}else {
								model.addAttribute("error", "密码不能为空");
								
							}
						}else {
							model.addAttribute("error", "用户名不正确");
							return "buyer/login";
						}
						
					}else{
							model.addAttribute("error", "用户名不能为空");
							return "buyer/login";
					}
					
				}else{
					model.addAttribute("error", "验证码输入错误");
					return "buyer/login";
				}
				
				
			}else{
				model.addAttribute("error", "验证码不能为空");
				return "buyer/login";
			}
		
		
			return "buyer/login";
		
	}
	
	private String getCSESSIONID(HttpServletResponse response , HttpServletRequest request){
		return RequestUtils.getCSESSIONID(request, response);
	}
	
	
	@RequestMapping(value="/shopping/getCodeImage.aspx")
	public void getCodeImage(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("#######################生成数字和字母的验证码#######################");
		BufferedImage img = new BufferedImage(68, 22, BufferedImage.TYPE_INT_RGB);
		// 得到该图片的绘图对象
		Graphics g = img.getGraphics();

		Random r = new Random();

		Color c = new Color(200, 150, 255);

		g.setColor(c);

		// 填充整个图片的颜色

		g.fillRect(0, 0, 68, 22);

		// 向图片中输出数字和字母

		StringBuffer sb = new StringBuffer();

		char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

		int index, len = ch.length;

		for (int i = 0; i < 4; i++) {

			index = r.nextInt(len);

			g.setColor(new Color(r.nextInt(88), r.nextInt(188), r.nextInt

			(255)));

			g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 22));
			// 输出的 字体和大小

			g.drawString("" + ch[index], (i * 15) + 3, 18);
			// 写什么数字，在图片 的什么位置画

			sb.append(ch[index]);

		}
		// 把上面生成的验证码放到Session域中
	//	sessionProvider.setAttributeForCode(RequestUtils.getCSESSIONID(response, request), sb.toString());
		try {
			ImageIO.write(img, "JPG", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
	
}
