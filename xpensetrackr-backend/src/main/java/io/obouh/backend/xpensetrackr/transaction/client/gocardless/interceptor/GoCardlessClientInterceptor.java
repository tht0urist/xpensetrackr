package io.obouh.backend.xpensetrackr.transaction.client.gocardless.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class GoCardlessClientInterceptor implements RequestInterceptor {
    private final String token;
    public GoCardlessClientInterceptor(String token){
        this.token = token;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", token);
    }
}
