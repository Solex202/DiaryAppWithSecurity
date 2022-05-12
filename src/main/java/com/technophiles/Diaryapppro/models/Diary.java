package com.technophiles.Diaryapppro.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Validated
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties("user")
public class Diary {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(nullable = false)
    @NotBlank
    @NotNull
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private LocalDateTime creationTime;

    @OneToMany(mappedBy = "diary",
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Entry> entries;

    @Override
    public String toString() {
        return String.format("id:%d\ttitle:%s" ,id,title);
    }

    public Diary(String title){
        this.title = title;
    }
}
