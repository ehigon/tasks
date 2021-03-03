package com.ehigon.tasks.finished;

import com.ehigon.tasks.tasks.Task;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Finished {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Task task;
    @Column(nullable = false)
    private LocalDateTime finishDate;
}
