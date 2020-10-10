/**
 * 
 */
package com.graymatter.service;

import java.time.format.DateTimeFormatter;

/**
 * @author vimukthi_r
 *
 */
public class CommonUtil {
	private CommonUtil() {
	}
	
	public static final String SUCCESS_MESSAGE = "success_message";
	public static final String ERROR_MESSAGE = "error_message";
	
	public static final DateTimeFormatter CREATEDTIME_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");

}
