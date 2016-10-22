package com.example.destr.busy_calendar.Threads;

public interface Operation <Params,Progress,Result>{
    Result operationDoing(Params params,ProgressCallback<Progress> progressCallback) throws Exception;
}
