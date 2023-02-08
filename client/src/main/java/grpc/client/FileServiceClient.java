package grpc.client;

import file.DataChunk;
import file.DownloadFileRequest;
import file.FileServiceGrpc;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
public class FileServiceClient {

    private static final int PORT = 50053;
    public static final String HOST = "localhost";
    private final FileServiceGrpc.FileServiceBlockingStub fileServiceBlockingStub = FileServiceGrpc.newBlockingStub(
            ManagedChannelBuilder.forAddress(HOST, PORT)
                    .usePlaintext()
                    .build()
    );


    public ByteArrayOutputStream downloadFile(String fileName){
        System.out.println("Will try to download file");

        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        final CountDownLatch finishedLatch = new CountDownLatch(1);
        final AtomicBoolean completed = new AtomicBoolean(false);

        StreamObserver<DataChunk> streamObserver = new StreamObserver<DataChunk>() {
            @Override
            public void onNext(DataChunk dataChunk) {
                try {
                    bos.write(dataChunk.getData().toByteArray());
                } catch (IOException e) {
                    log.error("***error on write to byte array stream***", e);
                    onError(e);
                }
            }

            @Override
            public void onError(Throwable t) {
                log.error("***downloadFile() error***", t);
                finishedLatch.countDown();
            }

            @Override
            public void onCompleted() {
                log.info("***downloadFile() has been completed!");
                completed.compareAndSet(false, true);
                finishedLatch.countDown();
            }
        };

        try {
            DownloadFileRequest request = DownloadFileRequest.newBuilder()
                    .setFileName(fileName).build();

            fileServiceBlockingStub.downloadFile(request, streamObserver);

            finishedLatch.await(5, TimeUnit.MINUTES);

            if(!completed.get())
                throw new Exception("****The downloadFile() method did not complete****");
        } catch (Exception e) {
            log.error("***The downloadFile() method did not complete****");
        }

        return bos;
    }

}
