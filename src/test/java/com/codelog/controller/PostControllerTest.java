package com.codelog.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

@WebMvcTest
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Test
    @DisplayName("/posts 요청시 Hello World를 출력한다.")
    void test() throws Exception {
        Map<String, String> content = new HashMap<>();
        content.put("title", "제목입니다.");
        content.put("content", "내용입니다.");
        String json = gson.toJson(content);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                                .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"title\": \"제목입니다.\", \"content\": \"내용입니다.\"}")
                                .content(json)
                )

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello World"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("/posts 요청시 title값은 필수다.")
    void test2() throws Exception {
        Map<String, String> content = new HashMap<>();
        content.put("title", null);
        content.put("content", "내용입니다.");
        String json = gson.toJson(content);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                                .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"title\": null, \"content\": \"내용입니다.\"}")
                                .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.title").value("타이틀을 입력해주세요."))
                .andDo(MockMvcResultHandlers.print());

    }
}
