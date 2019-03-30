package com.project.babylon.postPresenter;

import android.util.Log;

import com.project.babylon.common.SetupRetrofit;
import com.project.babylon.model.PostApiInterface;
import com.project.babylon.model.PostModel;
import com.project.babylon.postView.PostView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PostImp implements PostPresenter {


    public PostImp(PostView postView) {
        this.postView = postView;
    }

    private PostView postView;

    @Override
    public void loadPostData() {

        SetupRetrofit setupRetrofit = new SetupRetrofit();
        Retrofit retrofit = setupRetrofit.getRetrofit();

        final PostApiInterface postApiInterface = retrofit.create(PostApiInterface.class);
        postApiInterface.getpostData().enqueue(new Callback<List<PostModel>>() {

            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                if (response.code() == 200) {
                    postView.showPostData(response.body());
                    postView.onSuccess();
                }
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                postView.onError();
            }
        });

    }
}
