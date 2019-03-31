package com.project.babylon.landing.view;

import com.project.babylon.comments.model.CommentModel;
import com.project.babylon.post.model.PostModel;

import java.util.List;

public interface LandingView {
    void showCommentsData(List<CommentModel> commentModelList);
    void showPostData(List<PostModel> postModelList);
    void onSuccess();
    void onError();
    void  checkpoint();
}
