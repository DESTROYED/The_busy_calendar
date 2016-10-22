package com.example.destr.busy_calendar.Threads;

public interface OnResultCallback<Result,Progress> extends ProgressCallback{
    void onSuccess(Result result);
    void onError(Exception e);
}
