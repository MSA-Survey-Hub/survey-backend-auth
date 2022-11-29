package com.cloud.auth.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(catalog = "user_db")
public class UserGroup {
    @Id
    @Column(name = "id", nullable = false)
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}