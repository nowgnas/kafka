package com.example.kafka.domain.strategy.model;

import com.example.kafka.domain.strategy.service.MemberStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
  private String name;
  private String id;
  private MemberStrategy memberStrategy;

  public void join() {
    memberStrategy.memberJoin(this);
  }

  public void withdrawal() {
    memberStrategy.withdrawal(this.id);
  }
}
