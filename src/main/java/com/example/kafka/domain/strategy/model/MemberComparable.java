package com.example.kafka.domain.strategy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberComparable implements Comparable<MemberComparable> {
  private String name;
  private Integer age;

  @Override
  public int compareTo(MemberComparable o) {
    return Integer.compare(this.age, o.age);
  }
}

class CompareMember {
  void compare() {
    List<MemberComparable> list = new ArrayList<>();
    Collections.sort(list);

    list.forEach(System.out::println);

    MemberComparable memberComparable = MemberComparable.builder().build();
  }
}
