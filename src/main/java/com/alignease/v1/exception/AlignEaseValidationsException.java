package com.alignease.v1.exception;

import com.alignease.v1.utils.RequestStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlignEaseValidationsException extends RuntimeException{
	private String status;
	private String code;

    public AlignEaseValidationsException() {
        super();
    }

    public AlignEaseValidationsException(String message) {
        super(message);
    }

    public AlignEaseValidationsException(String statusCode, String message) {
		super(message);
        this.status = RequestStatus.FAILURE.getStatus();
        this.code = statusCode;
	}

    public AlignEaseValidationsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlignEaseValidationsException(Throwable cause) {
        super(cause);
    }

    protected AlignEaseValidationsException(String message, Throwable cause, boolean enableSuppression,
                                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
