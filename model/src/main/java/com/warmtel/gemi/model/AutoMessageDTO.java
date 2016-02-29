package com.warmtel.gemi.model;

import java.util.List;

public class AutoMessageDTO {
	private String resultCode;
	private String resultInfo;
	private List<AutoDTO> info;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}

	public List<AutoDTO> getInfo() {
		return info;
	}

	public void setInfo(List<AutoDTO> info) {
		this.info = info;
	}

	


}
