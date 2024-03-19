package com.onlineshop.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: item.proto")
public final class itemGrpc {

  private itemGrpc() {}

  public static final String SERVICE_NAME = "item";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.onlineshop.grpc.Item.GetItemsRequest,
      com.onlineshop.grpc.Item.GetItemsResponse> getGetAllItemsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAllItems",
      requestType = com.onlineshop.grpc.Item.GetItemsRequest.class,
      responseType = com.onlineshop.grpc.Item.GetItemsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.onlineshop.grpc.Item.GetItemsRequest,
      com.onlineshop.grpc.Item.GetItemsResponse> getGetAllItemsMethod() {
    io.grpc.MethodDescriptor<com.onlineshop.grpc.Item.GetItemsRequest, com.onlineshop.grpc.Item.GetItemsResponse> getGetAllItemsMethod;
    if ((getGetAllItemsMethod = itemGrpc.getGetAllItemsMethod) == null) {
      synchronized (itemGrpc.class) {
        if ((getGetAllItemsMethod = itemGrpc.getGetAllItemsMethod) == null) {
          itemGrpc.getGetAllItemsMethod = getGetAllItemsMethod = 
              io.grpc.MethodDescriptor.<com.onlineshop.grpc.Item.GetItemsRequest, com.onlineshop.grpc.Item.GetItemsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "item", "getAllItems"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.onlineshop.grpc.Item.GetItemsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.onlineshop.grpc.Item.GetItemsResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new itemMethodDescriptorSupplier("getAllItems"))
                  .build();
          }
        }
     }
     return getGetAllItemsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.onlineshop.grpc.Item.AddItemRequest,
      com.onlineshop.grpc.Item.AddItemResponse> getAddItemMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "addItem",
      requestType = com.onlineshop.grpc.Item.AddItemRequest.class,
      responseType = com.onlineshop.grpc.Item.AddItemResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.onlineshop.grpc.Item.AddItemRequest,
      com.onlineshop.grpc.Item.AddItemResponse> getAddItemMethod() {
    io.grpc.MethodDescriptor<com.onlineshop.grpc.Item.AddItemRequest, com.onlineshop.grpc.Item.AddItemResponse> getAddItemMethod;
    if ((getAddItemMethod = itemGrpc.getAddItemMethod) == null) {
      synchronized (itemGrpc.class) {
        if ((getAddItemMethod = itemGrpc.getAddItemMethod) == null) {
          itemGrpc.getAddItemMethod = getAddItemMethod = 
              io.grpc.MethodDescriptor.<com.onlineshop.grpc.Item.AddItemRequest, com.onlineshop.grpc.Item.AddItemResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "item", "addItem"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.onlineshop.grpc.Item.AddItemRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.onlineshop.grpc.Item.AddItemResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new itemMethodDescriptorSupplier("addItem"))
                  .build();
          }
        }
     }
     return getAddItemMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.onlineshop.grpc.Item.DeleteItemRequest,
      com.onlineshop.grpc.Item.DeleteItemResponse> getRemoveItemMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "removeItem",
      requestType = com.onlineshop.grpc.Item.DeleteItemRequest.class,
      responseType = com.onlineshop.grpc.Item.DeleteItemResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.onlineshop.grpc.Item.DeleteItemRequest,
      com.onlineshop.grpc.Item.DeleteItemResponse> getRemoveItemMethod() {
    io.grpc.MethodDescriptor<com.onlineshop.grpc.Item.DeleteItemRequest, com.onlineshop.grpc.Item.DeleteItemResponse> getRemoveItemMethod;
    if ((getRemoveItemMethod = itemGrpc.getRemoveItemMethod) == null) {
      synchronized (itemGrpc.class) {
        if ((getRemoveItemMethod = itemGrpc.getRemoveItemMethod) == null) {
          itemGrpc.getRemoveItemMethod = getRemoveItemMethod = 
              io.grpc.MethodDescriptor.<com.onlineshop.grpc.Item.DeleteItemRequest, com.onlineshop.grpc.Item.DeleteItemResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "item", "removeItem"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.onlineshop.grpc.Item.DeleteItemRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.onlineshop.grpc.Item.DeleteItemResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new itemMethodDescriptorSupplier("removeItem"))
                  .build();
          }
        }
     }
     return getRemoveItemMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static itemStub newStub(io.grpc.Channel channel) {
    return new itemStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static itemBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new itemBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static itemFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new itemFutureStub(channel);
  }

  /**
   */
  public static abstract class itemImplBase implements io.grpc.BindableService {

    /**
     */
    public void getAllItems(com.onlineshop.grpc.Item.GetItemsRequest request,
        io.grpc.stub.StreamObserver<com.onlineshop.grpc.Item.GetItemsResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAllItemsMethod(), responseObserver);
    }

    /**
     */
    public void addItem(com.onlineshop.grpc.Item.AddItemRequest request,
        io.grpc.stub.StreamObserver<com.onlineshop.grpc.Item.AddItemResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getAddItemMethod(), responseObserver);
    }

    /**
     */
    public void removeItem(com.onlineshop.grpc.Item.DeleteItemRequest request,
        io.grpc.stub.StreamObserver<com.onlineshop.grpc.Item.DeleteItemResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRemoveItemMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetAllItemsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.onlineshop.grpc.Item.GetItemsRequest,
                com.onlineshop.grpc.Item.GetItemsResponse>(
                  this, METHODID_GET_ALL_ITEMS)))
          .addMethod(
            getAddItemMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.onlineshop.grpc.Item.AddItemRequest,
                com.onlineshop.grpc.Item.AddItemResponse>(
                  this, METHODID_ADD_ITEM)))
          .addMethod(
            getRemoveItemMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.onlineshop.grpc.Item.DeleteItemRequest,
                com.onlineshop.grpc.Item.DeleteItemResponse>(
                  this, METHODID_REMOVE_ITEM)))
          .build();
    }
  }

  /**
   */
  public static final class itemStub extends io.grpc.stub.AbstractStub<itemStub> {
    private itemStub(io.grpc.Channel channel) {
      super(channel);
    }

    private itemStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected itemStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new itemStub(channel, callOptions);
    }

    /**
     */
    public void getAllItems(com.onlineshop.grpc.Item.GetItemsRequest request,
        io.grpc.stub.StreamObserver<com.onlineshop.grpc.Item.GetItemsResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAllItemsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void addItem(com.onlineshop.grpc.Item.AddItemRequest request,
        io.grpc.stub.StreamObserver<com.onlineshop.grpc.Item.AddItemResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAddItemMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void removeItem(com.onlineshop.grpc.Item.DeleteItemRequest request,
        io.grpc.stub.StreamObserver<com.onlineshop.grpc.Item.DeleteItemResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRemoveItemMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class itemBlockingStub extends io.grpc.stub.AbstractStub<itemBlockingStub> {
    private itemBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private itemBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected itemBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new itemBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.onlineshop.grpc.Item.GetItemsResponse getAllItems(com.onlineshop.grpc.Item.GetItemsRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetAllItemsMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.onlineshop.grpc.Item.AddItemResponse addItem(com.onlineshop.grpc.Item.AddItemRequest request) {
      return blockingUnaryCall(
          getChannel(), getAddItemMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.onlineshop.grpc.Item.DeleteItemResponse removeItem(com.onlineshop.grpc.Item.DeleteItemRequest request) {
      return blockingUnaryCall(
          getChannel(), getRemoveItemMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class itemFutureStub extends io.grpc.stub.AbstractStub<itemFutureStub> {
    private itemFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private itemFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected itemFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new itemFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.onlineshop.grpc.Item.GetItemsResponse> getAllItems(
        com.onlineshop.grpc.Item.GetItemsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAllItemsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.onlineshop.grpc.Item.AddItemResponse> addItem(
        com.onlineshop.grpc.Item.AddItemRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getAddItemMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.onlineshop.grpc.Item.DeleteItemResponse> removeItem(
        com.onlineshop.grpc.Item.DeleteItemRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRemoveItemMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_ALL_ITEMS = 0;
  private static final int METHODID_ADD_ITEM = 1;
  private static final int METHODID_REMOVE_ITEM = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final itemImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(itemImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_ALL_ITEMS:
          serviceImpl.getAllItems((com.onlineshop.grpc.Item.GetItemsRequest) request,
              (io.grpc.stub.StreamObserver<com.onlineshop.grpc.Item.GetItemsResponse>) responseObserver);
          break;
        case METHODID_ADD_ITEM:
          serviceImpl.addItem((com.onlineshop.grpc.Item.AddItemRequest) request,
              (io.grpc.stub.StreamObserver<com.onlineshop.grpc.Item.AddItemResponse>) responseObserver);
          break;
        case METHODID_REMOVE_ITEM:
          serviceImpl.removeItem((com.onlineshop.grpc.Item.DeleteItemRequest) request,
              (io.grpc.stub.StreamObserver<com.onlineshop.grpc.Item.DeleteItemResponse>) responseObserver);
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

  private static abstract class itemBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    itemBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.onlineshop.grpc.Item.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("item");
    }
  }

  private static final class itemFileDescriptorSupplier
      extends itemBaseDescriptorSupplier {
    itemFileDescriptorSupplier() {}
  }

  private static final class itemMethodDescriptorSupplier
      extends itemBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    itemMethodDescriptorSupplier(String methodName) {
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
      synchronized (itemGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new itemFileDescriptorSupplier())
              .addMethod(getGetAllItemsMethod())
              .addMethod(getAddItemMethod())
              .addMethod(getRemoveItemMethod())
              .build();
        }
      }
    }
    return result;
  }
}
