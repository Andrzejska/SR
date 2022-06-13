package messages;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.45.1)",
    comments = "Source: messages.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class MessagesServiceGrpc {

  private MessagesServiceGrpc() {}

  public static final String SERVICE_NAME = "calculator.MessagesService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<messages.ListenRequest,
      messages.Message> getListenToMessagesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "listenToMessages",
      requestType = messages.ListenRequest.class,
      responseType = messages.Message.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<messages.ListenRequest,
      messages.Message> getListenToMessagesMethod() {
    io.grpc.MethodDescriptor<messages.ListenRequest, messages.Message> getListenToMessagesMethod;
    if ((getListenToMessagesMethod = MessagesServiceGrpc.getListenToMessagesMethod) == null) {
      synchronized (MessagesServiceGrpc.class) {
        if ((getListenToMessagesMethod = MessagesServiceGrpc.getListenToMessagesMethod) == null) {
          MessagesServiceGrpc.getListenToMessagesMethod = getListenToMessagesMethod =
              io.grpc.MethodDescriptor.<messages.ListenRequest, messages.Message>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "listenToMessages"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  messages.ListenRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  messages.Message.getDefaultInstance()))
              .setSchemaDescriptor(new MessagesServiceMethodDescriptorSupplier("listenToMessages"))
              .build();
        }
      }
    }
    return getListenToMessagesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MessagesServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MessagesServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MessagesServiceStub>() {
        @java.lang.Override
        public MessagesServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MessagesServiceStub(channel, callOptions);
        }
      };
    return MessagesServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MessagesServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MessagesServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MessagesServiceBlockingStub>() {
        @java.lang.Override
        public MessagesServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MessagesServiceBlockingStub(channel, callOptions);
        }
      };
    return MessagesServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MessagesServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MessagesServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MessagesServiceFutureStub>() {
        @java.lang.Override
        public MessagesServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MessagesServiceFutureStub(channel, callOptions);
        }
      };
    return MessagesServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class MessagesServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void listenToMessages(messages.ListenRequest request,
        io.grpc.stub.StreamObserver<messages.Message> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListenToMessagesMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getListenToMessagesMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                messages.ListenRequest,
                messages.Message>(
                  this, METHODID_LISTEN_TO_MESSAGES)))
          .build();
    }
  }

  /**
   */
  public static final class MessagesServiceStub extends io.grpc.stub.AbstractAsyncStub<MessagesServiceStub> {
    private MessagesServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MessagesServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MessagesServiceStub(channel, callOptions);
    }

    /**
     */
    public void listenToMessages(messages.ListenRequest request,
        io.grpc.stub.StreamObserver<messages.Message> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getListenToMessagesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class MessagesServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<MessagesServiceBlockingStub> {
    private MessagesServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MessagesServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MessagesServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<messages.Message> listenToMessages(
        messages.ListenRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getListenToMessagesMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class MessagesServiceFutureStub extends io.grpc.stub.AbstractFutureStub<MessagesServiceFutureStub> {
    private MessagesServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MessagesServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MessagesServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_LISTEN_TO_MESSAGES = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MessagesServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MessagesServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LISTEN_TO_MESSAGES:
          serviceImpl.listenToMessages((messages.ListenRequest) request,
              (io.grpc.stub.StreamObserver<messages.Message>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class MessagesServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MessagesServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return messages.MessagesProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MessagesService");
    }
  }

  private static final class MessagesServiceFileDescriptorSupplier
      extends MessagesServiceBaseDescriptorSupplier {
    MessagesServiceFileDescriptorSupplier() {}
  }

  private static final class MessagesServiceMethodDescriptorSupplier
      extends MessagesServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    MessagesServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (MessagesServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MessagesServiceFileDescriptorSupplier())
              .addMethod(getListenToMessagesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
