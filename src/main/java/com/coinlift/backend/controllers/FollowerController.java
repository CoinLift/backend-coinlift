package com.coinlift.backend.controllers;

import com.coinlift.backend.dtos.users.FollowerResponseDto;
import com.coinlift.backend.dtos.users.UserMainInfoDto;
import com.coinlift.backend.services.followers.FollowerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin("*")
@Tag(name = "Follower Controller", description = "APIs related to managing followers and following users")
public class FollowerController {

    private final FollowerService followerService;

    public FollowerController(FollowerService followerService) {
        this.followerService = followerService;
    }

    /**
     * Endpoint to follow a user by the current user.
     *
     * @param userId The unique ID of the user to follow.
     * @return A response entity with a success message upon successful follow.
     */
    @Operation(summary = "Follow a user by the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully followed")
    })
    @PostMapping("/{username}/follow")
    public ResponseEntity<String> followUser(@PathVariable String username) {
        followerService.followUser(username);
        return ResponseEntity.ok("User successfully followed.");
    }

    /**
     * Endpoint to unfollow a user by the current user.
     *
     * @param followingId The unique ID of the user to unfollow.
     * @return A response entity with a success message upon successful unfollow.
     */
    @Operation(summary = "Unfollow a user by the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully unfollowed")
    })
    @PostMapping("/{username}/unfollow")
    public ResponseEntity<String> unfollowUser(@PathVariable String username) {
        followerService.unfollowUser(username);
        return ResponseEntity.ok("User successfully unfollowed.");
    }

    /**
     * Endpoint to fetch the main information of a user by its ID.
     *
     * @param userId The unique ID of the user to fetch main information.
     * @return A response entity containing the user's main information.
     */
    @Operation(summary = "Fetch the main information of a user by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User main information fetched successfully", content = @Content(schema = @Schema(implementation = UserMainInfoDto.class)))
    })
    @GetMapping("/main/{userId}")
    public ResponseEntity<UserMainInfoDto> getUserMainInfo(@PathVariable("userId") UUID userId) {
        return ResponseEntity.ok(followerService.getUserMainInfo(userId));
    }

    /**
     * Endpoint to fetch the list of followers for a user.
     *
     * @param userId The unique ID of the user to fetch the followers list.
     * @return A response entity containing the list of followers for the user.
     */
    @Operation(summary = "Fetch the list of followers for a user")
    @ApiResponse(
            responseCode = "200",
            description = "Followers fetched successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = FollowerResponseDto.class)))
    )
    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<FollowerResponseDto>> getFollowers(@PathVariable UUID userId) {
        List<FollowerResponseDto> followers = followerService.getFollowers(userId);
        return ResponseEntity.ok(followers);
    }

    /**
     * Endpoint to fetch the list of users that a user is following.
     *
     * @param userId The unique ID of the user to fetch the following list.
     * @return A response entity containing the list of users that the user is following.
     */
    @Operation(summary = "Fetch the list of users that a user is following")
    @ApiResponse(
            responseCode = "200",
            description = "Following fetched successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = FollowerResponseDto.class)))
    )
    @GetMapping("/{userId}/following")
    public ResponseEntity<List<FollowerResponseDto>> getFollowing(@PathVariable UUID userId) {
        List<FollowerResponseDto> following = followerService.getFollowing(userId);
        return ResponseEntity.ok(following);
    }

    /**
     * Endpoint to get the count of followers for a user.
     *
     * @param userId The unique ID of the user to fetch the follower count.
     * @return A response entity containing the count of followers for the user.
     */
    @Operation(summary = "Get the count of followers for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Follower count fetched successfully", content = @Content(schema = @Schema(implementation = Integer.class)))
    })
    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<Integer> getFollowerCount(@PathVariable UUID userId) {
        int followerCount = followerService.getFollowerCount(userId);
        return ResponseEntity.ok(followerCount);
    }

    /**
     * Endpoint to get the count of users that a user is following.
     *
     * @param userId The unique ID of the user to fetch the following count.
     * @return A response entity containing the count of users that the user is following.
     */
    @Operation(summary = "Get the count of users that a user is following")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Following count fetched successfully", content = @Content(schema = @Schema(implementation = Integer.class)))
    })
    @GetMapping("/{userId}/following/count")
    public ResponseEntity<Integer> getFollowingCount(@PathVariable UUID userId) {
        int followingCount = followerService.getFollowingCount(userId);
        return ResponseEntity.ok(followingCount);
    }

}
