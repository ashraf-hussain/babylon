package com.project.babylon;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.project.babylon.comments.commentDetail.CommentsDetailActivity;
import com.project.babylon.comments.view.AllCommentsActivity;
import com.project.babylon.landing.view.LandingActivity;
import com.project.babylon.post.postDetail.PostDetailActivity;
import com.project.babylon.post.view.AllPostActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<LandingActivity> activityTestRule =
            new ActivityTestRule<>(LandingActivity.class);
    @Rule
    public ActivityTestRule<AllCommentsActivity> allCommentsActivityActivityTestRule =
            new ActivityTestRule<>(AllCommentsActivity.class);

    @Rule
    public ActivityTestRule<CommentsDetailActivity> commentsDetailActivityActivityTestRule =
            new ActivityTestRule<>(CommentsDetailActivity.class);
    @Rule
    public ActivityTestRule<AllPostActivity> allPostActivityActivityTestRule =
            new ActivityTestRule<>(AllPostActivity.class);

    @Rule
    public ActivityTestRule<PostDetailActivity> postDetailActivityActivityTestRule =
            new ActivityTestRule<>(PostDetailActivity.class);


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.project.babylon", appContext.getPackageName());
    }


    @Test
    public void onCommentRecyclerViewClickedLanding(){
        onView(withId(R.id.rv_comments)).perform(click());
    }

    @Test
    public void commentViewAllActon() {
        onView(withId(R.id.ll_comment_view_all)).perform(click());
    }


    @Test
    public void postViewAllActon() {
        onView(withId(R.id.ll_post_view_all)).perform(click());
    }









}
