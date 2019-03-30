package com.project.babylon.model;

import com.project.babylon.comments.CommentModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostApiInterface {
    @GET("posts")
    Call<List<PostModel>> getpostData();
    @GET("comments")
    Call<List<CommentModel>> getCommentData();
}
