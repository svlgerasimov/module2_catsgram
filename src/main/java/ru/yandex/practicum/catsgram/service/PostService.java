package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exceptions.PostNotFoundException;
import ru.yandex.practicum.catsgram.exceptions.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final List<Post> posts = new ArrayList<>();
    private final UserService userService;
    private int lastId = 0;

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    private int generateId() {
        return ++lastId;
    }

    public List<Post> findAll(int size, String sort, int from) {
        Comparator<Post> postComparator = Comparator.comparing(Post::getCreationDate);
        return posts.stream()
                .sorted("desc".equals(sort) ? postComparator.reversed() : postComparator)
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
    }

    public List<Post> findAllByUserEmail(int size, String sort, int from, String userEmail) {
        Comparator<Post> postComparator = Comparator.comparing(Post::getCreationDate);
        return posts.stream()
                .filter(post -> Objects.equals(userEmail, post.getAuthor()))
                .sorted("desc".equals(sort) ? postComparator.reversed() : postComparator)
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
    }

    public List<Post> findAllByUserEmailCollection(int size, String sort, int from, Collection<String> userEmails) {
        Comparator<Post> postComparator = Comparator.comparing(Post::getCreationDate);
        return posts.stream()
                .filter(post -> userEmails.contains(post.getAuthor()))
                .sorted("desc".equals(sort) ? postComparator.reversed() : postComparator)
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
    }

    public Post create(Post post) {
        String userEmail = post.getAuthor();
        if (!userService.containsUser(userEmail)) {
            throw new UserNotFoundException(String.format("User with email %s not found", userEmail));
        }
        post.setId(generateId());
        posts.add(post);
        return post;
    }

    public Post findById(Integer id) {
        return posts.stream()
                .filter(post -> Objects.equals(post.getId(), id))
                .findFirst()
                .orElseThrow(() -> new PostNotFoundException(String.format("Post with id=%d not found", id)));
    }
}





