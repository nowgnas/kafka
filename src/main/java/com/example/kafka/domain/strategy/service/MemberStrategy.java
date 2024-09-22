package com.example.kafka.domain.strategy.service;

import com.example.kafka.domain.strategy.model.Member;

public interface MemberStrategy {
    void withdrawal(String id);
    void memberJoin(Member member);
}
