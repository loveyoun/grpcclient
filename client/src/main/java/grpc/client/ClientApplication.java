package grpc.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

//@Import(GreeterGrpc.GreeterImplBase.class)
@Slf4j
@SpringBootApplication
public class ClientApplication {

	public static final Path SERVER_BASE_PATH = Paths.get("src/main/resources/output/test.jpg");

	/*private static GreeterClient staticGreeterClient;	//Application먼저 뜨고, static인 greeterclient는 아직 생성되지 않는다..?

	private GreeterClient greeterClient;

	@PostConstruct
	private void initClient(){
		staticGreeterClient= this.greeterClient;
	}*/

	/**@Autowired
	private static GreeterClient greeterClient;
	public ClientApplication(GreeterClient greeterClient) {
		this.greeterClient = greeterClient;
	}**/

	@Autowired
	public static FileServiceClient fileServiceClient;
	public ClientApplication(FileServiceClient fileServiceClient) {
		this.fileServiceClient = fileServiceClient;
	}


	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ClientApplication.class, args);

		/**String user = "jeong youn";
		int age = 27;
		greeterClient.greet(user, age);**/

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
		try {
			String fileName = "C:/Users/lovek/park.jpg";
			ByteArrayOutputStream imageOS = fileServiceClient.downloadFile(fileName);
			byte[] bytes = imageOS.toByteArray();
			log.info("!!!!!!!!File has been downloaded!!!!!!!!!!!");

			File file = new File(SERVER_BASE_PATH.toUri());
			if(!file.exists()) file.createNewFile();

			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			writer.write(Arrays.toString(bytes));
			//Files.write(bytes, new File(""));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
