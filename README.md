Proto file :
This file is Used to serialize and deserialize the data in GRPC .
This file should be defined with an extension of (.proto)
Inside this file we can define Service and the message definitions what we want .
all the classes and Interfaces are auto generate here by using an plugin called protobuf plugin in pom.xml file 


GRPC :
why we use this ??
By using this we can do multiplexing (streaming) easily because it supports HTTP/2 .
And also it is fast,efficient,and it provides better performance when compared to others .
Here Stream of Request and Responses should be undergone with the same channel so that's why the traffic between channels reduces due to this it gives more efficiency then automatically it's performance is also get's high. 


