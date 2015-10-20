package com.itheima.common.convertion;

import org.springframework.core.convert.converter.Converter;

public class CustomTrimConverter implements Converter<String, String>{

	@Override
	public String convert(String source) {
			
		try {
			if(source!=null){
				source=source.trim();
				if(!"".equals(source))
					return source;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
