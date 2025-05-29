namespace java com.weilai.thrift

struct HelloMessage {
    1: required string message,
}

service HelloService {
    oneway void sayHello(1: HelloMessage request);
}