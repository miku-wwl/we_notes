namespace java com.weilai.thrift

struct HelloMessage {
    1: required string message,
}

struct HelloResponse {
    1: required string message,
}

service HelloService {

    HelloResponse sayHello(1: HelloMessage request);
}