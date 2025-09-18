package com.library.controller;

import com.library.controller.vm.BorrowTransactionResponseVM;
import com.library.controller.vm.MemberResponseVM;
import com.library.dto.BorrowTransactionDto;
import com.library.dto.MemberDto;
import com.library.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/add-member")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addMember(@Validated @RequestBody MemberDto memberDto) {
        memberService.addNewMember(memberDto);
        return ResponseEntity.created(URI.create("addMember/admin")).build();
    }

    @PutMapping("/update-member")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MemberDto> updateMember(@Validated @RequestBody MemberDto memberDto) {
        return ResponseEntity.ok(memberService.updateMember(memberDto));
    }

    @DeleteMapping("/delete/member-id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/member-id/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    public ResponseEntity<MemberDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getById(id));
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    public ResponseEntity<MemberDto> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(memberService.getByEmail(email));
    }

    @GetMapping("/search/name/{name}/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    public ResponseEntity<MemberResponseVM> getByName(@PathVariable String name,@PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        return ResponseEntity.ok(memberService.getByName(name,pageNo-1,pageSize));
    }

    @GetMapping("/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    public ResponseEntity<MemberResponseVM> getAllMembers(@PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        return ResponseEntity.ok(memberService.getAllMembers(pageNo-1,pageSize));
    }

    @GetMapping("/active/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    public ResponseEntity<MemberResponseVM> getActiveMembers(@PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        return ResponseEntity.ok(memberService.getActiveMembers(pageNo-1,pageSize ));
    }

    @GetMapping("/history/member-id/{id}/page-number/{pageNo}/page-size/{pageSize}")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    public ResponseEntity<BorrowTransactionResponseVM> getBorrowingHistory(@PathVariable Long id, @PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        return ResponseEntity.ok(memberService.getBorrowingHistory(id, pageNo-1,pageSize ));
    }
}
