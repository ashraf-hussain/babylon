package com.project.babylon.landing.presenter;

import com.project.babylon.comments.model.CommentModel;
import com.project.babylon.common.SetupRetrofit;
import com.project.babylon.landing.view.LandingView;
import com.project.babylon.landing.model.ApiInterface;
import com.project.babylon.post.model.PostModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LandingImp implements LandingPresenter {


    public LandingImp(LandingView landingView) {
        this.landingView = landingView;
    }

    private LandingView landingView;

    @Override
    public void loadCommentData() {

        SetupRetrofit setupRetrofit = new SetupRetrofit();
        Retrofit retrofit = setupRetrofit.getRetrofit();

        final ApiInterface postApiInterface = retrofit.create(ApiInterface.class);
        postApiInterface.getCommentData().enqueue(new Callback<List<CommentModel>>() {

            @Override
            public void onResponse(Call<List<CommentModel>> call, Response<List<CommentModel>> response) {
                if (response.code() == 200) {
                    landingView.showCommentsData(response.body());
                    landingView.onSuccess();
                }
            }

            @Override
            public void onFailure(Call<List<CommentModel>> call, Throwable t) {
                landingView.onError();
            }
        });

    }

    @Override
    public void loadPostData() {

        SetupRetrofit setupRetrofit = new SetupRetrofit();
        Retrofit retrofit = setupRetrofit.getRetrofit();

        final ApiInterface postApiInterface = retrofit.create(ApiInterface.class);
        postApiInterface.getPostData().enqueue(new Callback<List<PostModel>>() {

            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                if (response.code() == 200) {
                    landingView.showPostData(response.body());
                    landingView.onSuccess();
                }
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                landingView.onError();
            }
        });

    }
}
