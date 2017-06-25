package android.example.com.bakingapp.connection;

import android.support.test.espresso.IdlingResource;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by felipe on 25/06/17.
 */


public class MyExecutor extends ThreadPoolExecutor implements IdlingResource {

    private boolean idleStateChanged = true;
    private boolean mIsIdle = true;
    private ResourceCallback mResourceCallback;

    public MyExecutor() {
        super(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
    }

    public ResourceCallback getResourceCallback() {
        return mResourceCallback;
    }

    public void setIdleState(boolean idle) {
        idleStateChanged = (mIsIdle == idle);
        mIsIdle = idle;
    }

    @Override
    public String getName() {
        return "MyExecutor";
    }

    @Override
    public boolean isIdleNow() {

        if (mIsIdle && mResourceCallback != null) {
            mResourceCallback.onTransitionToIdle();
        }

        return mIsIdle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mResourceCallback = callback;
    }
}


