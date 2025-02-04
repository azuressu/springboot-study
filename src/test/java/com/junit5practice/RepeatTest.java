package com.junit5practice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class RepeatTest {

    // 테스트의 for문과 유사
    @RepeatedTest(value = 5, name = "반복 테스트 {currentRepetition} / {totalRepetitions}")
    void repeatTest(RepetitionInfo info) {
        System.out.println("테스트 반복 : " + info.getCurrentRepetition() + " / " + info.getTotalRepetitions());
    }

    @DisplayName("파라미터 값을 활용한 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9}) // 전달해주고 싶은 parameter 값 전달
    void parameterTest(int num) {
        System.out.println("5 * num = " + 5 * num);
    }

}
