package com.harshi_solution.product.dto;

import java.io.Serializable;

import com.harshi_solution.product.util.CommonUtil;
import com.harshi_solution.product.util.Constants;
import com.harshi_solution.product.util.ErrorMessageEnum;

public class BaseUIResponse<T> implements Serializable {
	private static final long serialVersionUID = 7980140018L;
	private String code;
	private String message;
	private String extendedMessage;
	private String status;
	private boolean hasError = false;
	private T responsePayload;

	public T getResponsePayload() {
		return responsePayload;
	}

	public void setResponsePayload(T responsePayload) {
		this.setCode(Constants.STATUS_SUCCESS_CODE);
		this.setStatus(Constants.STATUS_SUCCESS);
		this.responsePayload = responsePayload;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getExtendedMessage() {
		return extendedMessage;
	}

	public void setExtendedMessage(String extendedMessage) {
		this.extendedMessage = extendedMessage;
	}

	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	public void setEmptyResponsePayload() {
		this.setCode(Constants.STATUS_SUCCESS_CODE);
		this.setStatus(Constants.STATUS_SUCCESS);
	}

	public void setError(ErrorMessageEnum error) {
		String errorCode = CommonUtil.extractErrorCode(error.toString());
		this.setCode(errorCode);
		this.setMessage(error.getCustomMessage());
		this.setHasError(true);
	}

	@Override
	public String toString() {
		return "BaseUIResponse [code=" + code + ", message=" + message + ", extendedMessage=" + extendedMessage
				+ ", status=" + status + ", hasError=" + hasError + ", responsePayload=" + responsePayload + "]";
	}
}
