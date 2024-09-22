package com.example.kafka.domain.reactive.model.value;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberInformation {
    private String memberNo;
    private String memberName;

    public static MemberInformation getValue(String number, String name) {
        return MemberInformation.builder()
                .memberNo(number)
                .memberName(name)
                .build();
    }
}
