package com.server.service;

import com.server.dto.ItemResponseDto;
import com.server.dto.UserRequestDto;
import com.server.entity.Item;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ItemService {

    private final List<Item> itemList = Arrays.asList(
            new Item("Mac", 3_888_000),
            new Item("iPad", 1_230_000),
            new Item("iPhone", 1_550_000),
            new Item("Watch", 450_000),
            new Item("AirPods", 350_000)
    );

    public Item getCallObject(String query) {
        for (Item item : itemList) {
            if(item.getTitle().equals(query)) {
                return item;
            }
        }
        return null;
    }

    public ItemResponseDto getCallList() {
        ItemResponseDto responseDto = new ItemResponseDto();
        for (Item item : itemList) {
            responseDto.setItems(item);
        }
        return responseDto;
    }

    public Item postCall(String query, UserRequestDto userRequestDto) {
        // 담긴 user의 정보가 제대로 담겨왔는지 확인
        System.out.println("userRequestDto.getUsername() = " + userRequestDto.getUsername());
        System.out.println("userRequestDto.getPassword() = " + userRequestDto.getPassword());

        return getCallObject(query);
    }

    public ItemResponseDto exchangeCall(String token, UserRequestDto requestDto) {
        System.out.println("token = " + token);
        System.out.println("requestDto.getUsername() = " + requestDto.getUsername());
        System.out.println("requestDto.getPassword() = " + requestDto.getPassword());

        return getCallList();
    }
}
