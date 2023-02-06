package grpc.client;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.grpc.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


public class GreeterClient{

    @GrpcClient("test")
    private final GreeterGrpc.GreeterBlockingStub greeterBlockingStub;
    public GreeterClient(Channel channel){
        greeterBlockingStub = GreeterGrpc.newBlockingStub(channel);
    }

    public void greet(String name, int age){
        System.out.println("Will try to greet " + name + "(" + age + ")" + "...");

        final GreeterOuterClass.Hello.Request request = GreeterOuterClass.Hello.Request.newBuilder()
                .setAge(age)
                .setName(name)
                .build();

        GreeterOuterClass.Hello.Response response;

        try {
            response = greeterBlockingStub.hello(request);
        } catch (StatusRuntimeException e) {
            System.out.println("RPC failed " + e.getStatus());
            return;
//            throw new RuntimeException(e);
        }

        System.out.println("Greeter Server: " + response.getStr());
    }


//    public static void main(String[] args) throws InterruptedException{
//
//    }

}
