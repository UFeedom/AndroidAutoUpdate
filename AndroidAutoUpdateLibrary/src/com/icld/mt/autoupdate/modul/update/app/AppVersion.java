package com.icld.mt.autoupdate.modul.update.app;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class AppVersion implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("Code")
	private int verCode;

	@SerializedName("Name")
	private String verName;

	@SerializedName("Url")
	private String updateUrl;

	
	@SerializedName("Message")
	private String message;
	
	private boolean isNeedUpdate;

	
	
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isNeedUpdate() {
		return isNeedUpdate;
	}
	public void setNeedUpdate(boolean isNeedUpdate) {
		this.isNeedUpdate = isNeedUpdate;
	}
	public int getVerCode() {
		return verCode;
	}
	public void setVerCode(int verCode) {
		this.verCode = verCode;
	}
	public String getVerName() {
		return verName;
	}
	public void setVerName(String verName) {
		this.verName = verName;
	}

	public String getUpdateUrl() {
		return updateUrl;
	}
	public void setUpdateUrl(String updateUrl) {
		this.updateUrl = updateUrl;
	}
	
	
	@Override
	public String toString() {
		return "AppVersion [verCode=" + verCode + ", verName=" + verName
				+ ", updateUrl=" + updateUrl + ", message=" + message
				+ "]";
	}
	
	
	
	
	
	

}
