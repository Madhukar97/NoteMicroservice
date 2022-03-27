package com.fundoo.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Response {

	private Integer statusCode;
	private String statusMessage;
	private Object token;

}
