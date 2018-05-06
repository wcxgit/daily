package com.zhide.daily.utils.server;
/**
 * 服务器响应码
 * @author wuchenxi
 * @date 2018年4月25日 下午1:48:53
 *
 */

public enum ResponseCode {

	SUCCESS(200,"SUCCESS"),
	ERROR(500,"FAIL");
	
	
	private Integer code;
	private String msg;
	
	ResponseCode (Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
