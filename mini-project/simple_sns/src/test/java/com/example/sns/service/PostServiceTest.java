package com.example.sns.service;

import com.example.sns.exception.ErrorCode;
import com.example.sns.exception.SnsApplicationException;
import com.example.sns.fixture.PostEntityFixture;
import com.example.sns.fixture.UserEntityFixture;
import com.example.sns.model.entity.PostEntity;
import com.example.sns.model.entity.UserEntity;
import com.example.sns.repository.PostEntityRepository;
import com.example.sns.repository.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostEntityRepository postEntityRepository;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @Test
    void 포스트작성이_성공한경우() {
        String title = "title";
        String body = "body";
        String username = "username";

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));

        assertDoesNotThrow(() -> postService.create(title, body, username));
    }

    @Test
    void 포스트작성시_요청한유저가_존재하지않는경우() {
        String title = "title";
        String body = "body";
        String username = "username";

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(postEntityRepository.save(any())).thenReturn(mock(PostEntity.class));

        SnsApplicationException e = assertThrows(
                SnsApplicationException.class,
                () -> postService.create(title, body, username)
        );

        assertEquals(ErrorCode.USER_NOT_FOUND, e.getErrorCode());
    }

    @Test
    void 포스트수정이_성공한경우() {
        String title = "title";
        String body = "body";
        String username = "username";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(username, postId, 1);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));
        when(postEntityRepository.saveAndFlush(any())).thenReturn(postEntity);

        assertDoesNotThrow(() -> postService.modify(title, body, username, postId));
    }

    @Test
    void 포스트수정시_포스트가_존재하지않는_경우() {
        String title = "title";
        String body = "body";
        String username = "username";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(username, postId, 1);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.empty());

        SnsApplicationException e =
                assertThrows(SnsApplicationException.class, () -> postService.modify(title, body, username, postId));

        assertEquals(ErrorCode.POST_NOT_FOUND, e.getErrorCode());
    }

    @Test
    void 포스트수정시_권한이_없는_경우() {
        String title = "title";
        String body = "body";
        String username = "username";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(username, postId, 1);
        UserEntity writer = UserEntityFixture.get("username1", "password", 2);

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(writer));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        SnsApplicationException e =
                assertThrows(SnsApplicationException.class, () -> postService.modify(title, body, username, postId));

        assertEquals(ErrorCode.INVALID_PERMISSION, e.getErrorCode());
    }

    @Test
    void 포스트삭제가_성공한경우() {
        String username = "username";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(username, postId, 1);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        assertDoesNotThrow(() -> postService.delete(username, 1));
    }

    @Test
    void 포스트삭제시_포스트가_존재하지않는_경우() {
        String username = "username";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(username, postId, 1);
        UserEntity userEntity = postEntity.getUser();

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.empty());

        SnsApplicationException e =
                assertThrows(SnsApplicationException.class, () -> postService.delete(username, 1));

        assertEquals(ErrorCode.POST_NOT_FOUND, e.getErrorCode());
    }

    @Test
    void 포스트삭제시_권한이_없는_경우() {
        String username = "username";
        Integer postId = 1;

        PostEntity postEntity = PostEntityFixture.get(username, postId, 1);
        UserEntity writer = UserEntityFixture.get("username1", "password", 2);

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(writer));
        when(postEntityRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        SnsApplicationException e =
                assertThrows(SnsApplicationException.class, () -> postService.delete(username, 1));

        assertEquals(ErrorCode.INVALID_PERMISSION, e.getErrorCode());
    }

    @Test
    void 피드목록요청이_성공한경우() {

        Pageable pageable = mock(Pageable.class);
        when(postEntityRepository.findAll(pageable)).thenReturn(Page.empty());

        assertDoesNotThrow(() -> postService.list(pageable));
    }

    @Test
    void 내피드목록요청이_성공한경우() {

        Pageable pageable = mock(Pageable.class);
        UserEntity user = mock(UserEntity.class);

        when(userEntityRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(postEntityRepository.findAllByUser(user, pageable)).thenReturn(Page.empty());

        assertDoesNotThrow(() -> postService.my("", pageable));
    }
}
