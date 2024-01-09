package org.Tayana.BiDirectional_Streaming_Project2.server;

import java.io.IOException;
import com.Tayana.Proto.EcommerceServiceGrpc.EcommerceServiceImplBase;
import com.Tayana.Proto.Message;
import com.Tayana.Proto.OrderRequest;
import com.Tayana.Proto.OrderResponse;
import com.Tayana.Proto.Status;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

public class MainServer 
{
	public static void main(String[] args) throws IOException, InterruptedException {
		Server server = ServerBuilder.forPort(8083).addService(new ECommerceServiceImpl()).build();
		server.start();
		System.out.println("server started");
		server.awaitTermination();
	}
}

class ECommerceServiceImpl extends EcommerceServiceImplBase 
{

	@Override
	public StreamObserver<OrderRequest> placeOrder(StreamObserver<OrderResponse> responseObserver)
	{
		return new StreamObserver<OrderRequest>() 
		{
			//below method min and max values are included
			public int getRandomNumber(int min, int max)
			{
				return (int) ((Math.random() * (max - min)) + min);
			}

			@Override
			public void onNext(OrderRequest orderRequest)
			{
				int rand = getRandomNumber(0, 3);
				if (rand == 3) {

					Message message = Message
							.newBuilder()
							.setMess("order delivered successfully ")
							.build();
					Message message1 = Message
							.newBuilder()
							.setMess("Thanks you  ")
							.build();
					OrderResponse orderResponse = OrderResponse
							.newBuilder()
							.setOrderStatus(Status.DELEVIRED)
							.addMess(message)
							.addMess(message1)
							.build();
					responseObserver.onNext(orderResponse);
					System.out.println(orderRequest.toString());
				} 
				else if (rand == 2)
				{
					Message message = Message
							.newBuilder()
							.setMess("order is  OUT_FOR_delivered  ")
							.build();
					Message message1 = Message
							.newBuilder()
							.setMess("Thanks you  ")
							.build();
					OrderResponse orderResponse = OrderResponse
							.newBuilder()
							.setOrderStatus(Status.OUT_FOR_DELIVERY)
							.addMess(message)
							.addMess(message1)
							.build();
					responseObserver.onNext(orderResponse);
					
					System.out.println("client side : "+orderRequest.toString());

				}

				else if (rand == 1) 
				{
					Message message1 = Message
							.newBuilder()
							.setMess("Thanks you  ")
							.build();
					Message message = Message 
							.newBuilder()
							.setMess("PAYMENT_RECEIVED successfully ")
							.build();
					OrderResponse orderResponse = OrderResponse
							.newBuilder()
							.setOrderStatus(Status.PAYMENT_RECEIVED)
							.addMess(message)
							.addMess(message1)
							.build();
					responseObserver.onNext(orderResponse);
					System.out.println(orderRequest.toString());

				} 
				else if (rand == 0)
				{
					Message message1 = Message
							.newBuilder()
							.setMess("Thanks you  ")
							.build();

					Message message = Message 
							.newBuilder()
							.setMess("SHIPPED SUCCESSFULLY ")
							.build();
					OrderResponse orderResponse = OrderResponse
							.newBuilder()
							.setOrderStatus(Status.SHIPPED)
							.addMess(message)
							.addMess(message1)
							.build();
					responseObserver.onNext(orderResponse);
					System.out.println(orderRequest.toString());

				}

			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onCompleted() {
				System.out.println("server recived all the requests from the client");
				responseObserver.onCompleted();
			}
		};
	}

}