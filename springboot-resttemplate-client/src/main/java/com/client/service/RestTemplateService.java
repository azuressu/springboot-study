package com.client.service;

import com.client.dto.ItemDto;
import com.client.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RestTemplateService {
    
    private final RestTemplate restTemplate;
    
    public RestTemplateService(RestTemplateBuilder builder) {
        // RestTemplate 생성
        this.restTemplate = builder.build();
    }

    public ItemDto getCallObject(String query) {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                // 서버 입장의 서버에 보낼 준비
                .fromUriString("http://localhost:7070")
                // 서버 입장의 서버에서 받아줄 url
                .path("/api/server/get-call-obj")
                // 검색어를 보냄 (query param)
                .queryParam("query", query)
                .encode()
                .build()
                .toUri();
        log.info("uri = " + uri);

        ResponseEntity<ItemDto> responseEntity = restTemplate.getForEntity(uri, ItemDto.class);

        log.info("statusCode = " + responseEntity.getStatusCode());

        return responseEntity.getBody();
    }

    public List<ItemDto> getCallList() {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:7070")
                .path("/api/server/get-call-list")
                .encode()
                .build()
                .toUri();
        log.info("uri = " + uri);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        log.info("statusCode = " + responseEntity.getStatusCode());
        log.info("Body = " + responseEntity.getBody());

        return fromJSONtoItems(responseEntity.getBody());
    }

    public ItemDto postCall(String query) {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:7070")
                // PathVariable
                .path("/api/server/post-call/{query}")
                .encode()
                .build()
                .expand(query)
                .toUri();
        log.info("uri = " + uri);

        User user = new User("Robbie", "1234");

        // Body에 내용을 담아서 전달(Body에 user 객체를 넣음)
        ResponseEntity<ItemDto> responseEntity = restTemplate.postForEntity(uri, user, ItemDto.class);

        log.info("statusCode = " + responseEntity.getStatusCode());

        return responseEntity.getBody();
    }

    public List<ItemDto> exchangeCall(String token) {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:7070")
                .path("/api/server/exchange-call")
                .encode()
                .build()
                .toUri();
        log.info("uri = " + uri);

        // RequestBody에 담을 user의 정보
        User user = new User("Robbie", "1234");

        // RequestEntity 객체로 만들어서 전달
        RequestEntity<User> requestEntity = RequestEntity
                .post(uri)
                .header("X-Authorization", token)
                .body(user);

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        return fromJSONtoItems(responseEntity.getBody());
    }

    public List<ItemDto> fromJSONtoItems(String responseEntity) {
        JSONObject jsonObject = new JSONObject(responseEntity);
        JSONArray items = jsonObject.getJSONArray("items");
        List<ItemDto> itemDtoList = new ArrayList<>();

        for (Object item : items) {
            ItemDto itemDto = new ItemDto((JSONObject) item);
            itemDtoList.add(itemDto);
        }

        return itemDtoList;
    }
}
