package com.payam.learn.react;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;


public class CounterServer extends AbstractVerticle {



    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        int processorCounts = Runtime.getRuntime().availableProcessors();
        DeploymentOptions options = new DeploymentOptions().setInstances(processorCounts);
        vertx.deployVerticle( CounterServer.class.getName(),options);

    }

    @Override
    public void start(Future<Void> fut) {
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.patch("/").method(HttpMethod.POST).handler(routingContext -> {
            long number = Long.parseLong(routingContext.getBody().toString().trim());
            SingletonCounter.INSTANCE.value.add(number);
            routingContext.response().setStatusCode(200).end();
        });
        router.patch("/count").method(HttpMethod.GET).handler(routingContext -> {
            routingContext.response().setStatusCode(200).end(String.valueOf(SingletonCounter.INSTANCE.value.sum()));
        });
        server.requestHandler(router).listen(80);
    }
}
