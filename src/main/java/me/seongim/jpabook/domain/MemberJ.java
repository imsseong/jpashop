package me.seongim.jpabook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class MemberJ {

    @Id @GeneratedValue
    @Column(name = "memberJ_id")
    private Long id;

    @NotEmpty
    private String name;

    @Embedded
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "memberJ") //member와 order는 1:다, 매핑 되는 것 읽기 전용
    private List<Order> orders = new ArrayList<>();
}
