package grpc.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

//@Import(GreeterGrpc.GreeterImplBase.class)
@SpringBootApplication
public class ClientApplication {


	/*private static GreeterClient staticGreeterClient;	//Application먼저 뜨고, static인 greeterclient는 아직 생성되지 않는다..?

	private GreeterClient greeterClient;

	@PostConstruct
	private void initClient(){
		staticGreeterClient= this.greeterClient;
	}*/

	@Autowired
	private static GreeterClient greeterClient;
	public ClientApplication(GreeterClient greeterClient) {
		this.greeterClient = greeterClient;
	}

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ClientApplication.class, args);

		String user = "jeong youn";
		int age = 27;
		greeterClient.greet(user, age);

		//String target = "localhost:50051";
		//ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
		//ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
		//                .usePlaintext().build();

//		try {
//			//GreeterClient client = new GreeterClient(channel);
//			greeterClient.greet(user, age);
//		} finally {
//			channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
//		}
	}

}
