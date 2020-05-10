package com.payam.learn.react;

import io.vertx.core.AbstractVerticle;

import io.vertx.core.Future;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class CounterServer extends AbstractVerticle {

    protected LongAdder counter = new LongAdder();

    @Override
    public void start(Future<Void> fut) {
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        router.patch("/").method(HttpMethod.POST).handler(routingContext -> {
            long number = Long.parseLong(routingContext.getBody().toString().trim());
            counter.add(number);
            routingContext.response().setStatusCode(200).end();
        });

        router.patch("/count").method(HttpMethod.GET).handler(routingContext -> {
            routingContext.response().end(String.valueOf(counter.sum()));
        });
        server.requestHandler(router).listen(80);
    }
}
