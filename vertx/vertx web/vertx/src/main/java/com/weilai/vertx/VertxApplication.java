package com.weilai.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class VertxApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(VertxApplication.class);

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        // 创建 HTTP 服务器
        HttpServer server = vertx.createHttpServer();

        // 创建路由器实例
        Router router = Router.router(vertx);

        // 添加路由处理器
        Route route = router.get("/api/health-check");
        route.handler(context -> {
            try {
                // 打印请求信息用于调试
                LOGGER.debug("Handling request for path: {}", context.request().path());
                context.response()
                        .setStatusCode(200)
                        .putHeader("content-type", "application/json;charset=utf-8")
                        .putHeader("cache-control", "no-cache")
                        .end(new JsonObject()
                                .put("code", 0)
                                .put("time", System.currentTimeMillis())
                                .toBuffer());
            } catch (Exception e) {
                LOGGER.error("Error while handling health check request", e);
                context.fail(e);
            }
        });

        // 注册路由处理器
        server.requestHandler(router::accept);

        // 启动 HTTP 服务器
        int port = 8090; // 可以从配置或者环境变量读取端口号
        server.listen(port, ar -> {
            if (ar.succeeded()) {
                LOGGER.info("HTTP server started on port " + port);
            } else {
                LOGGER.error("Failed to start HTTP server", ar.cause());
            }
        });
    }
}