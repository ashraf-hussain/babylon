package com.project.babylon.landing.model;

import com.project.babylon.comments.model.CommentModel;
import com.project.babylon.post.model.PostModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("posts")
    Call<List<PostModel>> getPostData();

    @GET("comments")
    Call<List<CommentModel>> getCommentData();
}
