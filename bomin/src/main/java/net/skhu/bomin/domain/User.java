package net.skhu.bomin.domain;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //아무런 매개변수가 없는 생성자를 생성하되, 다른 패키지에 소속된 클래스는 접근 불허한다 protected User(){} 생성한것과 같음
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 생성을 데이터베이스에 위임한다.
    private Long Id;
    private String email;
    private String pw;
    private String phoneNumber;
    private String name;

    @Enumerated(EnumType.STRING) //enum의 값(USER)을 DB에 저장하는 방법.
    private Role role;

    @Builder
    public User(String email, String pw, String phoneNumber, String name, Role role){
        this.email=email;
        this.pw=pw;
        this.phoneNumber=phoneNumber;
        this.name=name;
        this.role=role;
    }
}
