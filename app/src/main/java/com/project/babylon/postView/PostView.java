package com.project.babylon.postView;

import com.project.babylon.model.PostModel;

import java.util.List;

public interface PostView {
    void showPostData(List<PostModel> postModelList);
    void onSuccess();
    void onError();
}
