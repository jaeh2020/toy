package com.projext.domain.member;

import lombok.Data;

@Data
public class Member {

    private Long id;
    private String loginId;
    private String name;
    private String password;

    public Member(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Member() {
    }
}
