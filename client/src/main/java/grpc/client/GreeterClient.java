package grpc.client;

import greet.GreeterGrpc;
import greet.GreeterOuterClass;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
@Service
public class GreeterClient{

    /*@Autowired private final GreeterGrpc.GreeterBlockingStub greeterBlockingStub; //bean으로 존재하지 않는다.

    private final ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 50051)
            .usePlaintext().build();

    public GreeterClient(GreeterGrpc.GreeterBlockingStub greeterBlockingStub) {
        this.greeterBlockingStub = GreeterGrpc.newBlockingStub(channel); //계속 stub을 생성해야할 필요는 없지 않나..?
     }*/

    /*private GreeterGrpc.GreeterBlockingStub greeterBlockingStub;
    public GreeterClient(Channel channel){
        greeterBlockingStub = GreeterGrpc.newBlockingStub(channel);
    }*/

    private static final int PORT = 50051;
    public static final String HOST = "localhost";

    private final GreeterGrpc.GreeterBlockingStub greeterBlockingStub = GreeterGrpc.newBlockingStub(
            ManagedChannelBuilder.forAddress(HOST, PORT)
                    .usePlaintext()
                    .build()
    );


    public void greet(String name, int age){
        System.out.println("Will try to greet " + name + "(" + age + ")" + "...");

        final GreeterOuterClass.Hello.Request request = GreeterOuterClass.Hello.Request.newBuilder()
                .setAge(age)
                .setName(name)
                .build();

        /**클라이언트가 받는 response**/
        GreeterOuterClass.Hello.Response response;

        try {
            /**서버에 보낼 request, 서버에서 받을 response.
            클라이언트: request -> 서버: response**/
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
