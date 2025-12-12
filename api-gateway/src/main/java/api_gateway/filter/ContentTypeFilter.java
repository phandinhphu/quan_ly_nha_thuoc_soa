package api_gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ContentTypeFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // Ensure Content-Type is set for JSON responses
            if (exchange.getResponse().getHeaders().getContentType() == null) {
                exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                log.debug("Set Content-Type to application/json for path: {}", 
                    exchange.getRequest().getPath());
            }
        }));
    }

    @Override
    public int getOrder() {
        return -2; // Run before ResponseLoggingFilter
    }
}
