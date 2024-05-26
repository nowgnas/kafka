package com.example.kafka.domain.v1.member.exception;

public class UpdateMemberInformationWithHistoryException extends RuntimeException {
	private static final String ERROR_MESSAGE = "개인 통관 고유번호 변경에 실패했습니다";

	public UpdateMemberInformationWithHistoryException() {
		super(ERROR_MESSAGE);
	}
}
