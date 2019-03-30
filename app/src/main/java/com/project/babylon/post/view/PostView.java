package com.project.babylon.post.view;

import com.project.babylon.post.model.PostModel;

import java.util.List;

public interface PostView {
    void showPostData(List<PostModel> postModelList);
    void onSuccess();
    void onError();
}
