package jpabook.jpashop;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue // @GeneratedValue 데이터베이스가 자동 생성해줌
    private Long id;
    private String username;
}
