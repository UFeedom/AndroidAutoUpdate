package com.icld.mt.autoupdate.infrastructure.wapapi.net;

public class TResponse<T> extends Response {

	public T Result;

	public T getResult() {
		
		return Result;
	}

	public void setResult(T result) {
		Result = result;
	}
}
