package com.itheima.core.service.user;

public interface SessionProvider {

	
			/**
			 * 把用户名保存到redis服务器中
			 * @param key
			 * @param value
			 */
		public void setAttributeForUsername(String key,String value);
		
			/**
			 * 把验证码保存到redis服务器中
			 * @param key
			 * @param value
			 */
		public void setAttributeForCode(String key,String value);
		
			/**
			 * 获取redis服务器中的用户名
			 * @param key
			 * @return
			 */
		public String getAttributeForUsername(String key);
	
				/**
				 * 	获取redis服务器中的验证码
				 * @param key
				 * @return
				 */
		public	String getAttributeCode(String key);
}
