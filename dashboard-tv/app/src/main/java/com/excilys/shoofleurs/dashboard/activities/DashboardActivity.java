package com.excilys.shoofleurs.dashboard.activities;

import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.excilys.shoofleurs.dashboard.R;
import com.excilys.shoofleurs.dashboard.controllers.SlideShowController;
import com.excilys.shoofleurs.dashboard.factories.AnimatorFactory;
import com.excilys.shoofleurs.dashboard.service.SlideShowService;
import com.excilys.shoofleurs.dashboard.utils.AndroidUtils;

/**
 * This Activity represents the main view of the application.
 * It asks the server for slideshows updates via the SlideShowService and
 * display them.
 */
public class DashboardActivity extends AppCompatActivity {
    private AnimatorSet mProgressAnimatorSet1,
            mProgressAnimatorSet2,
            mProgressAnimatorSet3,
            mProgressAnimatorSet4;

    /**
     * The waiting view points
     */
    private View mTopLeftPoint, mBottomLeftPoint, mBottomRightPoint, mTopRightPoint;

    /**
     * The service for the slideshows
     */
    private SlideShowService mSlideShowService;

    /**
     * The controller of slideshows for displaying them
     */
    private SlideShowController mSlideShowController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidUtils.hideStatusBar(this);
        setContentView(R.layout.activity_main);
        startWaitingAnimation();
        mSlideShowController = SlideShowController.getInstance(this);
        mSlideShowService = SlideShowService.getInstance(this);
        mSlideShowService.checkUpdates();
    }


    /**
     * Start the waiting animation
     */
    public void startWaitingAnimation() {
        mTopLeftPoint = findViewById(R.id.progress_view_top_left_point);
        mBottomLeftPoint = findViewById(R.id.progress_view_bottom_left_point);
        mBottomRightPoint = findViewById(R.id.progress_view_bottom_right_point);
        mTopRightPoint = findViewById(R.id.progress_view_top_right_point);

        RelativeLayout progressViewLayout = (RelativeLayout) findViewById(R.id.progress_view_layout);
        mProgressAnimatorSet1 = AnimatorFactory.createSquarePointAnimatorSet(mTopLeftPoint, progressViewLayout.getLayoutParams().width, 300, 0.07f, AnimatorFactory.Position.TOP_LEFT);
        mProgressAnimatorSet2 = AnimatorFactory.createSquarePointAnimatorSet(mBottomLeftPoint, progressViewLayout.getLayoutParams().width, 300, 0.07f, AnimatorFactory.Position.BOTTOM_LEFT);
        mProgressAnimatorSet3 = AnimatorFactory.createSquarePointAnimatorSet(mBottomRightPoint, progressViewLayout.getLayoutParams().width, 300, 0.07f, AnimatorFactory.Position.BOTTOM_RIGHT);
        mProgressAnimatorSet4 = AnimatorFactory.createSquarePointAnimatorSet(mTopRightPoint, progressViewLayout.getLayoutParams().width, 300, 0.07f, AnimatorFactory.Position.TOP_RIGHT);

        AndroidUtils.setVisibility(View.VISIBLE, mBottomLeftPoint, mBottomRightPoint, mTopLeftPoint, mTopRightPoint);
        AndroidUtils.startAnimators(mProgressAnimatorSet1, mProgressAnimatorSet2, mProgressAnimatorSet3, mProgressAnimatorSet4);
    }

    /**
     * Stop the waiting animation
     */
    public void stopWaitingAnimation() {
        AndroidUtils.cancelAnimators(mProgressAnimatorSet1, mProgressAnimatorSet2, mProgressAnimatorSet3, mProgressAnimatorSet4);
        AndroidUtils.setVisibility(View.GONE, mBottomLeftPoint, mBottomRightPoint, mTopLeftPoint, mTopRightPoint);
    }

    public SlideShowController getSlideShowController() {
        return mSlideShowController;
    }
}
