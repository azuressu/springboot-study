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
}
