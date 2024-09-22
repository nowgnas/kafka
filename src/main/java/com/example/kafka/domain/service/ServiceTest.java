package com.example.kafka.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceTest {
  public void dontMakeCollectionNullable(Membership membership) {
    List<Member> members = getNormalMember(membership);
    System.out.println(members.toString());
    List<Member> collect =
        members.stream().filter(item -> item.getAge() > 10).collect(Collectors.toList());
    Optional<Member> optionalMember = getOptionalMember();
    optionalMember.ifPresent(item -> item.doSomething());

  }

  private Optional<Member> getOptionalMember() {
    return Optional.of(Member.builder().build());
  }

  private List<Member> getNormalMember(Membership membership) {
    return membership.getMembers().isEmpty() ? null : new ArrayList<>(membership.getMembers());
  }
}

@Getter
@Builder
class Member {
  private String name;
  private String id;
  private Integer age;

  public void doSomething() {
    // do something
  }
}

@Getter
@Builder
class Membership {
  private List<Member> members;
}
