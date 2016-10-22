package com.example.destr.busy_calendar.Http;

import com.example.destr.busy_calendar.Threads.Operation;
import com.example.destr.busy_calendar.Threads.ProgressCallback;

public class HttpPostOperation implements Operation<HttpRequest,Void,String> {

    @Override
    public String operationDoing(HttpRequest httpRequest, ProgressCallback<Void> progressCallback) throws Exception {
        HttpClient httpClient = new HttpClient();
        if(httpRequest!=null){
            return httpClient.post(httpRequest.getUrl(),httpRequest.getHeaders(),httpRequest.getBody());
        }
        else{
            throw new IllegalArgumentException("Http Request is null");
        }
    }
}
