package com.example.kafka.domain.strategy.service;

import com.example.kafka.domain.strategy.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocialMemberStrategyService implements MemberStrategy {

  @Override
  public void withdrawal(String id) {}

  @Override
  public void memberJoin(Member member) {
    System.out.println("social join");
  }
}
