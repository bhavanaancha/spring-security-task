package com.epam.gatewayserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mock.Strictness;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

import com.epam.gatewayserver.filter.AuthenticationGateWayFilter;
import com.epam.gatewayserver.filter.RouteValidator;
import com.epam.gatewayserver.proxy.WebFluxAuthenticationProxy;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class AuthenticationGateWayFilterTest {

    @InjectMocks
    private AuthenticationGateWayFilter filter;

    @Mock
    private RouteValidator routeValidator;

    @Mock
    private WebFluxAuthenticationProxy authenticationProxy;

    @Mock
    private ServerWebExchange exchange;

    @Mock
    private ServerHttpRequest request;

    @Mock
    private ServerHttpResponse response;

    @Mock
    private GatewayFilterChain chain;

    private static final String VALID_TOKEN = "valid-token";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(exchange.getRequest()).thenReturn(request);
        when(exchange.getResponse()).thenReturn(response);
    }

    @Test
    void testApplyWithValidToken() {
        when(routeValidator.isSecured.test(request)).thenReturn(true);
        when(request.getHeaders()).thenReturn(HttpHeaders.EMPTY);
        when(authenticationProxy.validateToken(anyString()))
            .thenReturn(Mono.just("Token is Valid"));

        when(chain.filter(any())).thenReturn(Mono.empty());

        filter.apply(new AuthenticationGateWayFilter.Config())
            .filter(exchange, chain)
            .as(StepVerifier::create)
            .verifyComplete();

        verify(routeValidator).isSecured.test(request);
        verify(authenticationProxy).validateToken(anyString());
        verify(chain).filter(any());
        verifyNoMoreInteractions(routeValidator, authenticationProxy, chain);
    }

    @Test
    void testApplyWithMissingAuthorizationHeader() {
        when(routeValidator.isSecured.test(request)).thenReturn(true);
        when(request.getHeaders()).thenReturn(new HttpHeaders());

        Mono<Void> responseMono = Mono.empty();
        when(response.setStatusCode(HttpStatus.UNAUTHORIZED)).then(invocation -> {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return Mono.empty();
        });
        when(response.setComplete()).then(invocation -> {
            response.setComplete().block(); // Block to complete the Mono<Void>
            return Mono.empty();
        });
        when(chain.filter(any())).thenReturn(responseMono);

        Mono<Void> result = filter.apply(new AuthenticationGateWayFilter.Config())
            .filter(exchange, chain);

        StepVerifier.create(result).verifyComplete();

        verify(routeValidator).isSecured.test(request);
        verify(response).setStatusCode(HttpStatus.UNAUTHORIZED);
        verify(response).setComplete();
        verify(chain).filter(any());
        verifyNoMoreInteractions(routeValidator, authenticationProxy, chain);
    }

    @Test
    void testApplyWithInvalidToken() {
        when(routeValidator.isSecured.test(request)).thenReturn(true);
        when(request.getHeaders()).thenReturn(HttpHeaders.EMPTY);
        when(authenticationProxy.validateToken(anyString()))
            .thenReturn(Mono.just("Token is Invalid"));

        Mono<Void> responseMono = Mono.empty();
//        when(response.setStatusCode(HttpStatus.UNAUTHORIZED)).thenReturn(response);
//        when(response.setComplete()).thenReturn(response);
        when(response.setStatusCode(HttpStatus.UNAUTHORIZED)).then(invocation -> {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return Mono.empty();
        });
        when(response.setComplete()).then(invocation -> {
            response.setComplete().block(); // Block to complete the Mono<Void>
            return Mono.empty();
        });
        when(chain.filter(any())).thenReturn(responseMono);

        Mono<Void> result = filter.apply(new AuthenticationGateWayFilter.Config())
            .filter(exchange, chain);

        StepVerifier.create(result).verifyComplete();

        verify(routeValidator).isSecured.test(request);
        verify(response).setStatusCode(HttpStatus.UNAUTHORIZED);
        verify(response).setComplete();
        verify(chain).filter(any());
        verifyNoMoreInteractions(routeValidator, authenticationProxy, chain);
    }

    @Test
    void testApplyWithNonSecuredRoute() {
        when(routeValidator.isSecured.test(request)).thenReturn(false);

        Mono<Void> responseMono = Mono.empty();
        when(chain.filter(any())).thenReturn(responseMono);

        Mono<Void> result = filter.apply(new AuthenticationGateWayFilter.Config())
            .filter(exchange, chain);

        StepVerifier.create(result).verifyComplete();

        verify(routeValidator).isSecured.test(request);
        verifyNoMoreInteractions(routeValidator, authenticationProxy, chain);
    }
}
