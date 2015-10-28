package com.itheima.core.service.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.itheima.common.core.web.Constants;

import redis.clients.jedis.Jedis;

/**
		 * session	提供类		远程session	保存session到redis服务器
		 * @author Iverson
		 *
		 */
public class SessionProviderImpl implements SessionProvider {

		@Autowired
		private Jedis jedis;
		/**
		 * 用户名存活时间		单位分钟
		 */
		private Integer exp =30;
		
			/**
			 * 验证码存活时间	单位分钟
			 */
		private Integer expCode=1;
		public void setExp(Integer exp) {
			this.exp = exp;
		}

		public void setExpCode(Integer expCode) {
			this.expCode = expCode;
		}

		
		@Override
	public void setAttributeForUsername(String key, String value) {
				//用户名
				//key	csessionid+user_name		
			jedis.set(key+" :"+Constants.USER_NAME, value);
					//设置存活时间
			jedis.expire(key +":"+Constants.USER_NAME, 60*exp);
	}

	@Override
	public void setAttributeForCode(String key, String value) {
				
			jedis.set(key+":"+Constants.CODE_NAME, value);
			jedis.expire(key+":"+Constants.CODE_NAME, 60*expCode);
	}

	@Override
	public String getAttributeForUsername(String key) {
			String value = jedis.get(key+":"+Constants.USER_NAME);
		if	(value != null){
					//设置存活时间
			jedis.expire(key+":"+Constants.USER_NAME, 60*exp);
		}
		return value;
	}

	@Override
	public String getAttributeCode(String key) {
		String value =jedis.get(key+":"+Constants.CODE_NAME);
		if(value != null){
		  jedis.del(key+":"+Constants.CODE_NAME);
		}
		return value;
	}

}
