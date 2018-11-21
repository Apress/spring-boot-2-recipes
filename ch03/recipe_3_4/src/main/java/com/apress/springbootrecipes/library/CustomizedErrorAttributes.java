package com.apress.springbootrecipes.library;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

public class CustomizedErrorAttributes extends DefaultErrorAttributes {

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
    var errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
		errorAttributes.put("parameters", webRequest.getParameterMap());
		return errorAttributes;
	}
}
