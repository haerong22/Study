package com.example.restcontroller.notice.controller;

import com.example.restcontroller.notice.entity.Notice;
import com.example.restcontroller.notice.exception.AlreadyDeletedException;
import com.example.restcontroller.notice.exception.DuplicateNoticeException;
import com.example.restcontroller.notice.exception.NoticeNotFoundException;
import com.example.restcontroller.notice.model.NoticeDeleteInput;
import com.example.restcontroller.notice.model.NoticeInput;
import com.example.restcontroller.notice.model.NoticeModel;
import com.example.restcontroller.notice.model.ResponseError;
import com.example.restcontroller.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class ApiNoticeController {

    private final NoticeRepository noticeRepository;

    @GetMapping("/api/notice")
    public String chapter1_6() {
        return "공지사항입니다.";
    }

    @GetMapping("/api/notice2")
    public NoticeModel chapter1_7() {

        return NoticeModel.builder()
                .id(1L)
                .title("공지사항입니다.")
                .contents("공지사항 내용 입니다.")
                .regDate(LocalDateTime.now())
                .build();
    }

    @GetMapping("/api/notice3")
    public List<NoticeModel> chapter1_8() {
        List<NoticeModel> noticeList = new ArrayList<>();

        noticeList.add(NoticeModel.builder()
                .id(1L)
                .title("공지사항입니다.")
                .contents("공지사항 내용입니다.")
                .regDate(LocalDateTime.of(2021, 1, 30, 0, 0))
                .build());

        noticeList.add(NoticeModel.builder()
                .id(2L)
                .title("두번째 공지사항입니다.")
                .contents("두번째 공지사항 내용입니다.")
                .regDate(LocalDateTime.of(2021, 1, 31, 0, 0))
                .build());

        return noticeList;
    }

    @GetMapping("/api/notice5")
    public List<NoticeModel> chapter1_9() {
        return new ArrayList<>();
    }

    @GetMapping("/api/notice/count")
    public Integer chapter1_10() {
        List<NoticeModel> noticeList = new ArrayList<>();
        noticeList.add(new NoticeModel());
        noticeList.add(new NoticeModel());
        noticeList.add(new NoticeModel());

        return noticeList.size();
    }

    @PostMapping("/api/notice")
    public NoticeModel chapter1_11(@RequestParam String title,
                                   @RequestParam String contents) {
        return NoticeModel.builder()
                .id(1L)
                .title(title)
                .contents(contents)
                .regDate(LocalDateTime.now())
                .build();
    }

    @PostMapping("/api/notice2")
    public NoticeModel chapter1_12(NoticeModel noticeModel) {
        return noticeModel
                .setId(2L)
                .setRegDate(LocalDateTime.now());
    }

    @PostMapping("/api/notice3")
    public NoticeModel chapter1_13(@RequestBody NoticeModel noticeModel) {
        return noticeModel
                .setId(3L)
                .setRegDate(LocalDateTime.now());
    }

    @PostMapping("/api/notice4")
    public Notice chapter1_14(@RequestBody NoticeInput noticeInput) {
        return noticeRepository.save(Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .regDate(LocalDateTime.now())
                .build());
    }

    @PostMapping("/api/notice5")
    public Notice chapter1_15(@RequestBody NoticeInput noticeInput) {
        return noticeRepository.save(Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .regDate(LocalDateTime.now())
                .build());
    }

    @GetMapping("/api/notice/{id}")
    public Notice chapter1_16(@PathVariable Long id) {
        return noticeRepository.findById(id).orElseGet(() -> null);
    }

    @PutMapping("/api/notice/{id}")
    public Notice chapter1_17(@PathVariable Long id, @RequestBody NoticeInput noticeInput) {
        Notice noticeEntity = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("아이디 없음"));

        noticeEntity
                .setTitle(noticeInput.getTitle())
                .setContents(noticeInput.getContents())
                .setUpdateDate(LocalDateTime.now());

        noticeRepository.save(noticeEntity);

        return noticeEntity;
    }

    @PutMapping("/api/notice2/{id}")
    public Notice chapter1_18(@PathVariable Long id, @RequestBody NoticeInput noticeInput) {
        Notice noticeEntity = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));

        noticeEntity
                .setTitle(noticeInput.getTitle())
                .setContents(noticeInput.getContents());

        noticeRepository.save(noticeEntity);

        return noticeEntity;
    }

    @PutMapping("/api/notice3/{id}")
    public Notice chapter1_19(@PathVariable Long id, @RequestBody NoticeInput noticeInput) {
        Notice noticeEntity = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));

        noticeEntity
                .setTitle(noticeInput.getTitle())
                .setContents(noticeInput.getContents())
                .setUpdateDate(LocalDateTime.now());

        noticeRepository.save(noticeEntity);

        return noticeEntity;
    }

    @PatchMapping("/api/notice/{id}/hits")
    public void chapter1_20(@PathVariable Long id) {
        Notice noticeEntity = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));
        noticeEntity.setHits(noticeEntity.getHits() + 1);
        noticeRepository.save(noticeEntity);
    }

    @DeleteMapping("/api/notice/{id}")
    public void chapter1_21(@PathVariable Long id) {
        noticeRepository.deleteById(id);
    }
    @DeleteMapping("/api/notice2/{id}")
    public void chapter1_22(@PathVariable Long id) {
        Notice noticeEntity = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));

        noticeRepository.delete(noticeEntity);
    }

    @DeleteMapping("api/notice3/{id}")
    public void chapter1_23(@PathVariable Long id) {
        Notice noticeEntity = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));
        if (noticeEntity.isDeleted()) {
            throw new AlreadyDeletedException("이미 삭제된 게시글 입니다.");
        }
        noticeEntity
                .setDeleted(true)
                .setDeleteDate(LocalDateTime.now());

        noticeRepository.save(noticeEntity);
    }

    @DeleteMapping("/api/notice4")
    public void chapter1_24(@RequestBody NoticeDeleteInput noticeDeleteInput) {

        List<Notice> noticeList = noticeRepository.findByIdIn(noticeDeleteInput.getIdList())
                .orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));

        noticeList.forEach(notice -> {
            notice
                    .setDeleted(true)
                    .setDeleteDate(LocalDateTime.now());
        });

        noticeRepository.saveAll(noticeList);
    }

    @DeleteMapping("/api/notice/all")
    public void chapter1_25() {
        noticeRepository.deleteAll();
    }

    @PostMapping("/api/notice6")
    public ResponseEntity<String> chapter1_26(@RequestBody NoticeInput noticeInput) {
        noticeRepository.save(Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .regDate(LocalDateTime.now())
                .build());

        return new ResponseEntity<>("CREATED", HttpStatus.CREATED);
    }

    @PostMapping("/api/notice7")
    public ResponseEntity<?> chapter1_27(@RequestBody @Valid NoticeInput noticeInput,
                                              BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            List<ResponseError> errors = new ArrayList<>();
            bindingResult.getFieldErrors().forEach(e -> errors.add(ResponseError.of(e)));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        noticeRepository.save(Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .regDate(LocalDateTime.now())
                .build());

        return new ResponseEntity<>("CREATED", HttpStatus.CREATED);
    }

    @PostMapping("/api/notice8")
    public ResponseEntity<?> chapter1_28(@RequestBody @Valid NoticeInput noticeInput,
                                         BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            List<ResponseError> errors = new ArrayList<>();
            bindingResult.getFieldErrors().forEach(e -> errors.add(ResponseError.of(e)));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        noticeRepository.save(Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .regDate(LocalDateTime.now())
                .build());

        return new ResponseEntity<>("CREATED", HttpStatus.CREATED);
    }

    @GetMapping("/api/notice/latest/{size}")
    public Page<Notice> chapter1_29(@PathVariable int size) {
        return noticeRepository.findAll(PageRequest.of(0, size, Sort.Direction.DESC, "regDate"));
    }

    @PostMapping("/api/notice9")
    public ResponseEntity<?> chapter1_30(@RequestBody @Valid NoticeInput noticeInput,
                                         BindingResult bindingResult) throws DuplicateNoticeException {
        if (bindingResult.hasFieldErrors()) {
            List<ResponseError> errors = new ArrayList<>();
            bindingResult.getFieldErrors().forEach(e -> errors.add(ResponseError.of(e)));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        // 중복체크

        // regDate > checkDate 이면 1분 미만
        LocalDateTime checkDate = LocalDateTime.now().minusMinutes(1);

        int noticeCount = noticeRepository.countByTitleAndContentsAndRegDateGreaterThanEqual(
                noticeInput.getTitle(),
                noticeInput.getContents(),
                checkDate
        );

        if (noticeCount > 0 ) {
            throw new DuplicateNoticeException("1분 이내에 등록된 공지사항이 존재합니다");
        }

        noticeRepository.save(Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .regDate(LocalDateTime.now())
                .build());

        return new ResponseEntity<>("CREATED", HttpStatus.CREATED);
    }
}




















