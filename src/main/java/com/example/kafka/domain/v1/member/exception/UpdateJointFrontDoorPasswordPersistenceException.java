package com.example.kafka.domain.v1.member.exception;

public class UpdateJointFrontDoorPasswordPersistenceException extends RuntimeException {
	private static final String ERROR_MESSAGE = "공동현관 비밀번호 변경에 실패했습니다.";

	public UpdateJointFrontDoorPasswordPersistenceException() {
		super(ERROR_MESSAGE);
	}
}
