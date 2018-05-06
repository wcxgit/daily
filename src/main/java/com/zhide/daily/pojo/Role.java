package com.zhide.daily.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

/**
 * 用户角色pojo
 * @author wuchenxi
 * @date 2018/4/30
 */
@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 角色名
     */
    @Column(name = "role_name")
    private String roleName;
}
