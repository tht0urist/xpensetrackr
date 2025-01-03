package io.obouh.backend.xpensetrackr.transaction.client.gocardless;

import io.obouh.backend.xpensetrackr.transaction.client.gocardless.config.GoCardlessFeignClientConfig;
import io.obouh.backend.xpensetrackr.transaction.client.gocardless.response.TransactionsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
@FeignClient(name = "goCardless", configuration = GoCardlessFeignClientConfig.class, url = "${goCardless.url}")
public interface GoCardlessClient {

    @GetMapping
    TransactionsResponse findTransactions();

}
