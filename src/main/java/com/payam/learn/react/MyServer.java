package com.payam.learn.react;

import io.vertx.core.AbstractVerticle;

import io.vertx.core.Future;
public class MyServer extends AbstractVerticle {


    @Override
    public void start(Future<Void> fut){
        vertx.createHttpServer()
                .requestHandler(r ->{
                    r.response().end("Hello Payam!!!");

                }).listen(8080,result ->{
                    if(result.succeeded()){
                        fut.complete();
                    }else {
                        fut.fail(result.cause());
                    }

        });


    }
}
