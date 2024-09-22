package com.example.kafka.domain.method;

import com.example.kafka.domain.strategy.service.LpointMemberStrategy;
import com.example.kafka.domain.strategy.service.MemberService;
import com.example.kafka.domain.strategy.service.SocialMemberStrategyService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackages = "com.example.kafka.domain.strategy")
public class MethodPracticeTest {
  @Autowired private LpointMemberStrategy lpointMemberStrategy;
  @Autowired private SocialMemberStrategyService socialMemberStrategyService;

  @Test
  public void test() {
    MemberService memberService = new MemberService(lpointMemberStrategy,
            socialMemberStrategyService);
    memberService.process();
  }
}
