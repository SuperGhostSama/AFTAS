package com.example.aftas.repository;

import com.example.aftas.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByIdentityNumber(String identityNumber);

    //Search by name or membership number or family name
    @Query(value =
            "SELECT * FROM member WHERE membership_number = :searchTerm " +
                    "OR name LIKE %:searchTerm% OR family_name LIKE %:searchTerm% OR identity_number LIKE %:searchTerm% ", nativeQuery = true)
    List<Member> findByMembershipNumberOrIdentityNumberOrNameOrFamilyName(@Param("searchTerm") String searchTerm);
}
