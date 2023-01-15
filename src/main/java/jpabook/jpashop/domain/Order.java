package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name ="order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id") // 매핑 : fk name이 member_id가 된다 // * 연관관계의 주인
    private Member member;
}
