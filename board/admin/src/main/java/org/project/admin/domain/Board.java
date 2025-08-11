package org.project.admin.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(length = 100)
    private String name;

    @Setter
    @Column(length = 5000)
    private String description;

    @Setter
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "board",  fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    private Board(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static Board of(String name, String description) {
        return new Board(name, description);
    }

    public void addPost(Post post) {
        post.setBoard(this);
        posts.add(post);
    }

    public void removePost(Post post) {
        posts.remove(post);
        post.setBoard(null);
    }

}
