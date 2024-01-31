package com.group.libraryapp.domain.user;

import com.group.libraryapp.domain.book.loanhistory.UserLoanHistory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    private Integer age;

    private Integer money;

    private String email;

    private String password;

    @Builder
    public User(String name, Integer age, Integer money, String email, String password) {
        this.name = name;
        this.age = age;
        this.money = money;
        this.email = email;
        this.password = password;
    }



    public void chargeMoney(Integer money) {
        this.money += money;
    }
}
