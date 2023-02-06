package grpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.util.concurrent.TimeUnit;

//@Import(GreeterGrpc.GreeterImplBase.class)
@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ClientApplication.class, args);

		String user = "jeong youn";
		int age = 27;

		String target = "localhost:50051";
		//ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
		ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 50051)
				.usePlaintext().build();
		try {
			GreeterClient client = new GreeterClient(channel);
			client.greet(user, age);
		} finally {
			channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
		}
	}

}
