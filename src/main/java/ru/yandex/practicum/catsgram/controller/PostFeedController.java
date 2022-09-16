package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/feed/friends")
public class PostFeedController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private final ObjectMapper objectMapper;
    private final PostService postService;

    @Autowired
    public PostFeedController(ObjectMapper objectMapper, PostService postService) {
        this.objectMapper = objectMapper;
        this.postService = postService;
    }

    @PostMapping
    public List<Post> findPosts(@RequestBody String json) {
        try {
            String paramsFromJson = objectMapper.readValue(json, String.class);
            FriendsParams friendsParams = objectMapper.readValue(paramsFromJson, FriendsParams.class);
            return postService.findAllByUserEmailCollection(friendsParams.size, friendsParams.sort, 0,
                    friendsParams.friends);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Invalid json format");
        }

    }

    @Value
    private static class FriendsParams {
        String sort;
        Integer size;
        List<String> friends;
    }

}
