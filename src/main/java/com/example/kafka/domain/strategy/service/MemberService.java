package com.example.kafka.domain.strategy.service;

import com.example.kafka.domain.strategy.model.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
  private final LpointMemberStrategy lpointMemberStrategy;
  private final SocialMemberStrategyService socialMemberStrategyService;

  public void process() {
    Member lpntMember =
        Member.builder().name("name").id("id").memberStrategy(lpointMemberStrategy).build();
    lpntMember.join();
    lpntMember.withdrawal();

    HashMap<String, String> hashMap= new HashMap<>();
    List<String> list = new ArrayList<>();

  }
}
