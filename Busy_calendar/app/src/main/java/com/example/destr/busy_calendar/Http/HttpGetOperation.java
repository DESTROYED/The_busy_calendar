package com.example.destr.busy_calendar.Http;

import com.example.destr.busy_calendar.Threads.Operation;
import com.example.destr.busy_calendar.Threads.ProgressCallback;

public class HttpGetOperation implements Operation<HttpRequest,Void,String> {

    @Override
    public String operationDoing(HttpRequest httpRequest, ProgressCallback<Void> progressCallback) throws Exception {
        HttpClient httpClient = new HttpClient();
        return httpClient.get(httpRequest.getUrl(), httpRequest.getHeaders());
    }
}
