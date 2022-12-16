package com.example.quest.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "post")
@Data
public class Post {

    @Id
    Long id;

    Long userId;

    Long title;

    @Lob
    @Column(columnDefinition = "text")
    String text;
}
