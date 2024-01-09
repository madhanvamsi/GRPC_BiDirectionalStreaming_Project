package org.Tayana.BiDirectional_Streaming_Project2.client;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import com.Tayana.Proto.Category;
import com.Tayana.Proto.EcommerceServiceGrpc;
import com.Tayana.Proto.Message;
import com.Tayana.Proto.OrderRequest;
import com.Tayana.Proto.OrderResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class MainClient
{
	public static void main(String[] args)
	{
		ManagedChannel channel=ManagedChannelBuilder.
				.forAddress("localhost", 8083)
				.usePlaintext()
				.build();
		CountDownLatch latch=new CountDownLatch(1);
		
		EcommerceServiceGrpc.EcommerceServiceStub stub=EcommerceServiceGrpc.newStub(channel);
		
		StreamObserver<OrderRequest> observer=stub.placeOrder(
				new StreamObserver<OrderResponse>() 
		{
			
			@Override
			public void onNext(OrderResponse orderResponse) 
			{
				
				System.out.println("response from the server :"+orderResponse.getOrderStatus());	
				System.out.println(orderResponse.getMessList());
			//	for (Message message : orderResponse.getMessList())
//				{
//                    System.out.println("Message: " + message.getMess());
//                }
			}
			
			@Override
			public void onError(Throwable t) 
			{
				t.printStackTrace();
				latch.countDown();
			}
			
			@Override
			public void onCompleted()
			{
				System.out.println("all the data sended to server ");
				latch.countDown();
			}
		});
		
		
		OrderRequest orderRequest=OrderRequest.newBuilder()
				.setCategory(Category.BOOKS)
				.setName("virat")
				.setEmail("virat@gmail.com")
				.setPhone(9381430729L)
				.build();
		
		OrderRequest orderRequest1=OrderRequest.newBuilder()
				.setCategory(Category.ELECTRONICS)
				.setName("nikhil")
				.setEmail("nikhil@gmail.com")
				.setPhone(93814352L)
				.build();
		
		OrderRequest orderRequest2=OrderRequest.newBuilder()
				.setCategory(Category.BOOKS)
				.setName("dhoni")
				.setEmail("dhoni@gmail.com")
				.setPhone(930729L)
				.build();
		
		List<OrderRequest>  order=Arrays.asList(orderRequest,orderRequest1,orderRequest2);
		for(OrderRequest request:order)
		{
			observer.onNext(request);
		}
		observer.onCompleted();
		
		try
		{
		    latch.await();
		    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
		} 
		catch (InterruptedException | RuntimeException e) 
		{
		    e.printStackTrace();
		}
	}
	

}
