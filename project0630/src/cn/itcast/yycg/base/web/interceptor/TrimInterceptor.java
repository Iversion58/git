package cn.itcast.yycg.base.web.interceptor;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;


public class TrimInterceptor extends AbstractInterceptor {
	
	/** serialVersionUID*/
	private static final long serialVersionUID = 5284703370663664600L;

	public void destroy() {
	}
	public void init() {

	}
	/*  
     * @Description:拦截所有参数,去掉参数空格 
     * @see com.opensymphony.xwork2.interceptor.Interceptor#intercept(com.opensymphony.xwork2.ActionInvocation) 
     */  
    public String intercept(ActionInvocation invocation) throws Exception {  
          
        Map map=invocation.getInvocationContext().getParameters();  
        Set keys = map.keySet();  
                  Iterator iters = keys.iterator();  
                while(iters.hasNext()){  
                    Object key = iters.next();  
                    Object value = map.get(key);  
                    if(value instanceof String[]){
                    	String[] value_l = transfer((String[])value);
                    	if(value_l.length==1 && value_l[0]==null){
                    		value_l = null;
                    	}
                    	map.put(key, value_l); 
                    }
                     
                }  
        return invocation.invoke();  
    }  
      
    /** 
     * @Description: 遍历参数数组里面的数据，取出空格 
     * @param params 
     * @return 
     */  
    private String[] transfer(String[] params){  
        for(int i=0;i<params.length;i++){  
           if(params[i]!=null){
        	   params[i]=params[i].trim();  
               if(params[i]!=null && params[i].equals("")){
               	params[i] = null;
               }
           }
            
        }  
        return params;  
    }  



}
