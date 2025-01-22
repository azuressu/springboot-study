package com.memo.service;

import com.memo.dto.MemoRequestDto;
import com.memo.dto.MemoResponseDto;
import com.memo.entity.Memo;
import com.memo.repository.MemoRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;

    public MemoResponseDto createMemo(MemoRequestDto requestDto) {
        // RequestDto -> Entity
        Memo memo = new Memo(requestDto);

        // Save Memo Into Repo
        Memo saveMemo = memoRepository.save(memo);

        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(saveMemo);

        return memoResponseDto;
    }

    public List<MemoResponseDto> getMemos() {
        // Search Memos In Repo
        return memoRepository.findAll().stream().map(MemoResponseDto::new).toList();
    }

    @Transactional
    public Long updateMemo(Long id, MemoRequestDto requestDto) {
        // Search Memo In Repo
        Memo memo = findMemo(id);

        // Update Memo
        memo.update(requestDto);

        return id;
    }

    public Long deleteMemo(Long id) {
        // Search Memo In Repo
        Memo memo = findMemo(id);

        // Delete Memo
        memoRepository.delete(memo);

        return id;
    }

    private Memo findMemo(Long id) {
        return memoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다."));

    }
}