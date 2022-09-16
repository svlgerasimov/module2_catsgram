package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public List<Post> findAll(@RequestParam(required = false, defaultValue = "desc") String  sort,
                              @RequestParam(required = false, defaultValue = "0") Integer page,
                              @RequestParam(required = false, defaultValue = "10") Integer size) {
        if ((!"asc".equals(sort) && !"desc".equals(sort)) || page < 0 || size < 0) {
            throw new IllegalArgumentException();
        }
        List<Post> posts = postService.findAll(size, sort, size * page);
        logger.debug("Текущее количество постов: {}", posts.size());
        return posts;
    }

    @GetMapping("/post/{id}")
    public Post findById(@PathVariable Integer id) {
        return postService.findById(id);
    }

    @PostMapping(value = "/post")
    public void create(@RequestBody Post post) {
        logger.debug("Добавить пост: {}", post);
        postService.create(post);
    }
}