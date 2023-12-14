package com.example.aftas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RankId implements Serializable {

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "competition_code")
    private String competitionCode;
}
