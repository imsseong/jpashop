package me.seongim.jpabook.domain.item;

import lombok.Getter;
import lombok.Setter;
import me.seongim.jpabook.domain.Category;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //상속 관계 매핑은 부모 클래스에 전략 지정
@DiscriminatorColumn(name = "dtype") //싱글 테이블이라 저장할때 구분값 필요
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
