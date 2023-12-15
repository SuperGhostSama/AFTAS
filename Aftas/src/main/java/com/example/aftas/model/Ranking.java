package com.example.aftas.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Ranking {


    @EmbeddedId
    private RankId id;

    @Column(columnDefinition = "integer default 0")
    private int rank;

    @Column(columnDefinition = "integer default 0")
    private int score;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @MapsId("memberId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "competition_id")
    @MapsId("competitionId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Competition competition;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;
}
