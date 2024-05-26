package com.example.kafka.domain.v1.member.exception;

public class UpdateDeliveryMessageException extends RuntimeException {
	private static final String ERROR_MESSAGE = "배송 메세지 변경에 실패했습니다.";

	public UpdateDeliveryMessageException() {
		super(ERROR_MESSAGE);
	}
}
