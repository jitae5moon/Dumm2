package org.project.admin.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "title", length = 50)
    private String title;

    @Setter
    @Column(name = "content")
    private String content;

    @Setter
    private Integer viewCount;

    @Setter
    private Integer likeCount;

    @Setter
    private Boolean isNotice;

    @Setter
    private Boolean isDeleted;

    private Post(String title, String content, Boolean isNotice) {
        this.title = title;
        this.content = content;
        this.viewCount = 0;
        this.likeCount = 0;
        this.isNotice = isNotice;
        this.isDeleted = false;
    }

    public static Post of(String title, String content) {
        return new Post(title, content, false);
    }

}
