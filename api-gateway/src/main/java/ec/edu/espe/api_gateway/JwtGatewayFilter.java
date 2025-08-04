package ec.edu.espe.api_gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtGatewayFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().value();

        // Permitir rutas de autenticación sin validación
        if (isAuthPath(path)) {
            return chain.filter(exchange);
        }

        // Extraer token del header Authorization
        String token = extractToken(exchange.getRequest());

        if (token == null || !jwtUtils.validateJwtToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Agregar información del usuario a los headers para los microservicios
        String username = jwtUtils.getUserNameFromJwtToken(token);
        List<String> roles = jwtUtils.getRolesFromJwtToken(token);

        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header("X-User-Name", username)
                .header("X-User-Roles", String.join(",", roles))
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    private String extractToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean isAuthPath(String path) {
        return path.startsWith("/api/v1/api/auth/") ||
                path.equals("/actuator/health") ||
                path.startsWith("/actuator/");
    }

    @Override
    public int getOrder() {
        return -100; // Ejecutar antes que otros filtros
    }
}