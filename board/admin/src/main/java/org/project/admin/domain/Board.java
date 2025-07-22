package org.project.admin.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Boolean isDeleted;

    private Board(String name, String description) {
        this.name = name;
        this.description = description;
        this.isDeleted = false;
    }

    public static Board of(String name, String description) {
        return new Board(name, description);
    }

}
