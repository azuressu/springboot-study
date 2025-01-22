package com.memo.controller;

import com.memo.dto.MemoRequestDto;
import com.memo.dto.MemoResponseDto;
import com.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>();

    /**
     * 메모 생성 API
     * @param memoRequestDto : username과 contents를 담은 requestDto
     * @return : 생성한 memo에 대한 resposneDto 반환
     */
    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto memoRequestDto) {
        // RequestDto -> Entity
        Memo memo = new Memo(memoRequestDto);

        // Search Memo's MAX ID
        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
        memo.setId(maxId);

        // Save DB
        memoList.put(memo.getId(), memo);

        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        // Return
        return memoResponseDto;
    }

    /**
     * 전체 메모 API
     * @return : 메모 responseDto 리스트 반환
     */
    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {
        // Map To List
        List<MemoResponseDto> memoResponseDtos =
                memoList.values().stream().map(MemoResponseDto::new).toList();

        // Return
        return memoResponseDtos;
    }

    /**
     * 메모 수정 API 
     * @param id : 수정할 메모의 ID
     * @param memoRequestDto : 수정할 메모의 username, contents
     * @return : 수정한 메모의 ID 반환
     */
    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto memoRequestDto) {
        // Search Memo In List
        if (memoList.containsKey(id)) {
            // Get Memo
            Memo memo = memoList.get(id);
            // Memo Update
            memo.update(memoRequestDto);
            // Return
            return memo.getId();
        } else {
            throw new IllegalStateException("선택한 메모는 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        // Search Memo In List
        if(memoList.containsKey(id)) {
            // Memo Delete
            memoList.remove(id);
            // Return
            return id;
        } else {
            throw new IllegalStateException("선택한 메모는 존재하지 않습니다.");
        }
    }
}
