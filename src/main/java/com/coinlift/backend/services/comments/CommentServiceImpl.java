package com.coinlift.backend.services.comments;

import com.coinlift.backend.dtos.comments.CommentRequestDto;
import com.coinlift.backend.dtos.comments.CommentResponseDto;
import com.coinlift.backend.entities.Comment;
import com.coinlift.backend.entities.Post;
import com.coinlift.backend.entities.notification.EventType;
import com.coinlift.backend.entities.user.MyUserDetails;
import com.coinlift.backend.entities.user.User;
import com.coinlift.backend.exceptions.DeniedAccessException;
import com.coinlift.backend.exceptions.ResourceNotFoundException;
import com.coinlift.backend.mappers.CommentMapper;
import com.coinlift.backend.repositories.CommentRepository;
import com.coinlift.backend.repositories.PostRepository;
import com.coinlift.backend.repositories.UserRepository;
import com.coinlift.backend.services.followers.FollowerService;
import com.coinlift.backend.services.notifications.NotificationService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private final UserRepository userRepository;

    private final FollowerService followerService;

    private final NotificationService notificationService;

    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper, UserRepository userRepository, FollowerService followerService, NotificationService notificationService, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userRepository = userRepository;
        this.followerService = followerService;
        this.notificationService = notificationService;
        this.postRepository = postRepository;
    }

    /**
     * Retrieve a paginated list of comments for a specific post.
     *
     * @param postId The unique ID of the post to fetch comments for.
     * @param page   The page number for pagination (default: 0).
     * @param size   The number of comments per page (default: 10).
     * @return A list of {@link CommentResponseDto} representing the comments for the post.
     */
    @Override
    public List<CommentResponseDto> getComments(UUID postId, int page, int size) {
        UUID userId = getUserIdOrNull();
        Pageable commentPage = PageRequest.of(page, size);

        List<Comment> comments = commentRepository.findAllWithoutReplies(postId, commentPage);

        return comments.stream()
                .map(comment -> new CommentResponseDto(
                        comment.getId(),
                        comment.getContent(),
                        Duration.between(comment.getCreatedAt(), LocalDateTime.now()).getSeconds(),
                        isCreator(userId, comment),
                        isRepliesExists(comment),
                        followerService.getUserMainInfo(comment.getUser().getId())
                )).toList();
    }

    /**
     * Retrieve a paginated list of replies for a specific comment.
     *
     * @param commentId The unique ID of the comment to fetch replies for.
     * @param page      The page number for pagination (default: 0).
     * @param size      The number of replies per page (default: 10).
     * @return A list of {@link CommentResponseDto} representing the replies to the comment.
     */
    @Override
    public List<CommentResponseDto> getReplies(UUID commentId, int page, int size) {
        UUID userId = getUserIdOrNull();

        List<Comment> paginatedComments = commentRepository.findByParentCommentId(commentId, PageRequest.of(page, size));

        return paginatedComments.stream()
                .map(reply -> new CommentResponseDto(
                        reply.getId(),
                        reply.getContent(),
                        Duration.between(reply.getCreatedAt(), LocalDateTime.now()).getSeconds(),
                        isCreator(userId, reply),
                        isRepliesExists(reply),
                        followerService.getUserMainInfo(reply.getUser().getId())
                ))
                .toList();
    }

    /**
     * Create a new reply for a specific comment.
     *
     * @param commentRequestDto The {@link CommentRequestDto} containing the reply details.
     * @param commentId         The unique ID of the comment to reply to.
     * @return The ID of the created reply.
     * @throws ResourceNotFoundException if the parent comment with the specified ID is not found.
     */
    @Override
    public UUID createReply(CommentRequestDto commentRequestDto, UUID commentId) {
        UUID userId = getUserId();
        Comment parrentComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("comment with id " + commentId + " not found!"));

        Comment comment = commentMapper.toCommentEntity(commentRequestDto, parrentComment.getPost().getId());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));

        comment.setParentComment(parrentComment);
        comment.setUser(user);

        notificationService.notifyUser(user.getUsername(), parrentComment.getUser().getId(), EventType.REPLY);

        return commentRepository.save(comment).getId();
    }


    /**
     * Creates a new comment for a specific post.
     *
     * @param commentRequestDto The `CommentRequestDto` containing the details of the new comment.
     * @param postId            The ID of the post for which the comment is created.
     * @return The ID of the newly created comment.
     * @throws ResourceNotFoundException if the user is not found when trying to create the comment.
     */
    @Override
    public UUID createComment(CommentRequestDto commentRequestDto, UUID postId) {
        UUID userId = getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post not found"));
        Comment comment = commentMapper.toCommentEntity(commentRequestDto, postId);

        comment.setUser(user);

        notificationService.notifyUser(user.getUsername(), post.getUser().getId(), EventType.COMMENT);

        return commentRepository.save(comment).getId();
    }

    /**
     * Updates a comment for a specific post.
     *
     * @param commentRequestDto The `CommentRequestDto` containing the updated comment details.
     * @param postId            The ID of the post to which the comment belongs.
     * @param commentId         The ID of the comment to update.
     * @return The `CommentResponseDto` representing the updated comment.
     * @throws DeniedAccessException if the user is not the creator of the comment and not authorized to update it.
     */
    @Override
    public CommentResponseDto updateComment(CommentRequestDto commentRequestDto, UUID postId, UUID commentId) {
        UUID userId = getUserId();
        Comment comment = getComment(postId, commentId);

        checkAccess(userId, comment);

        comment.setContent(commentRequestDto.content());

        commentRepository.save(comment);

        return new CommentResponseDto(
                commentId,
                commentRequestDto.content(),
                Duration.between(comment.getCreatedAt(), LocalDateTime.now()).getSeconds(),
                true,
                isRepliesExists(comment),
                followerService.getUserMainInfo(comment.getUser().getId())
        );
    }

    /**
     * Deletes a comment for a specific post.
     *
     * @param postId    The ID of the post to which the comment belongs.
     * @param commentId The ID of the comment to delete.
     * @throws DeniedAccessException if the user is not the creator of the comment and not authorized to delete it.
     */
    @Override
    public void deleteComment(UUID postId, UUID commentId) {
        UUID userId = getUserId();
        Comment comment = getComment(postId, commentId);

        checkAccess(userId, comment);

        commentRepository.deleteById(comment.getId());
    }

    private UUID getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new DeniedAccessException("You can't do it before authenticate!");
        }
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        return userDetails.user().getId();
    }

    private UUID getUserIdOrNull() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        return userDetails.user().getId();
    }

    private Comment getComment(UUID postId, UUID commentId) {
        return commentRepository.findByPostIdAndCommentId(postId, commentId)
                .orElseThrow(() -> new ResourceNotFoundException("comment with id " + commentId + " not found!"));
    }

    private void checkAccess(UUID userId, Comment comment) {
        if (!userId.equals(comment.getUser().getId())) {
            throw new DeniedAccessException("You don't have access, because you're not creator of this comment!");
        }
    }

    private boolean isCreator(UUID userId, Comment comment) {
        if (userId == null) {
            return false;
        }
        return userId.equals(comment.getUser().getId());
    }

    private boolean isRepliesExists(Comment comment) {
        return !comment.getReplies().isEmpty();
    }
}
