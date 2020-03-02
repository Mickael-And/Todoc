package com.cleanup.todoc.utils;

import androidx.test.espresso.IdlingResource;

public class ElapsedTimeIdlingResource implements IdlingResource {

    private long startTime;
    private long waitingTime;
    private ResourceCallback resourceCallback;

    public ElapsedTimeIdlingResource(long waitingTime) {
        this.startTime = System.currentTimeMillis();
        this.waitingTime = waitingTime;
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        long elapsed = System.currentTimeMillis() - this.startTime;
        boolean idle = (elapsed >= this.waitingTime);
        if (idle) {
            this.resourceCallback.onTransitionToIdle();
        }
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }
}
