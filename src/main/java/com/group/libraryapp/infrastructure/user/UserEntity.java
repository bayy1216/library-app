package com.group.libraryapp.infrastructure.user;

import com.group.libraryapp.domain.model.user.User;
import com.group.libraryapp.infrastructure.book.loanhistory.UserLoanHistoryEntity;
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
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    private Integer age;

    private Integer money;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserLoanHistoryEntity> userBuyHistories = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserLoanHistoryEntity> userLoanHistories = new ArrayList<>();

    /**
     * [User] 생성하기 위한 builder.
     * 처음에 money는 0으로 초기화
     */
    @Builder
    public UserEntity(Long id, String name, Integer age, Integer money,String email, String password) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.money = money;
        this.email = email;
        this.password = password;
    }

    public User toDomain() {
        return User.builder()
                .id(id)
                .name(name)
                .age(age)
                .money(money)
                .email(email)
                .password(password)
                .build();
    }

    public static UserEntity fromDomain(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .money(user.getMoney())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }


}
