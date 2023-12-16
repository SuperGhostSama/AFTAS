package com.example.aftas.controller;

import com.example.aftas.dto.MemberRequestDTO;
import com.example.aftas.config.handlers.response.ResponseMessage;
import com.example.aftas.model.Member;
import com.example.aftas.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        if(members.isEmpty()) {
            return ResponseMessage.notFound("Member not found");
        }else {
            return ResponseMessage.ok(members, "Success");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getMemberById(@PathVariable Long id) {
        return ResponseMessage.ok( memberService.getMemberById(id), "Success");
    }

    @PostMapping
    public ResponseEntity addMember(@Valid @RequestBody MemberRequestDTO memberRequestDTO) {
        Member member1 = memberService.addMember(memberRequestDTO.toMember());
        if(member1 == null) {
            return ResponseMessage.badRequest("Member not created");
        }else {
            return ResponseMessage.created(member1, "Member created successfully");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateMember(@RequestBody Member member, @PathVariable Long id) {
        Member member1 = memberService.updateMember(member, id);
        if(member1 == null) {
            return ResponseMessage.badRequest("Member not updated");
        }else {
            return ResponseMessage.created(member1, "Member updated successfully");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMember(@PathVariable Long id) {
        Member member = memberService.getMemberById(id);
        if(member == null) {
            return ResponseMessage.notFound("Member not found");
        }else {
            memberService.deleteMember(id);
            return ResponseMessage.ok(null, "Member deleted successfully");
        }
    }

    @GetMapping("/search")
    public ResponseEntity searchMember(@RequestBody String name) {
        List<Member> members = memberService.findByMembershipNumberOrIdentityNumberOrNameOrFamilyName(name);
        if(members.isEmpty()) {
            return ResponseMessage.notFound("Member not found");
        }else {
            return ResponseMessage.ok(members, "Success");
        }
    }

    @GetMapping("/byCompetition/{competitionId}")
    public ResponseEntity<List<Member>> getMembersByCompetitionId(@PathVariable Long competitionId) {
        List<Member> members = memberService.getMembersByCompetitionId(competitionId);
        return ResponseEntity.ok(members);
    }

}
