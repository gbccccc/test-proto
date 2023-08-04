package example.protobufimp.service;

import com.google.protobuf.*;
import example.protobuf.service.SearchService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SearchServiceChannelLocal implements RpcChannel {
    private SearchService service;

    @Override
    public void callMethod(Descriptors.MethodDescriptor methodDescriptor, RpcController rpcController, Message message, Message message1, RpcCallback<Message> rpcCallback) {
        service.callMethod(methodDescriptor, rpcController, message, rpcCallback);
    }
}
