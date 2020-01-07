package com.github.minispa.dubbo.filter;

import static com.github.minispa.MDCTraceHelper.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

@Slf4j
@Activate(group = {Constants.CONSUMER, Constants.PROVIDER}, order = Integer.MAX_VALUE)
public class RpcTraceFilter implements Filter {

    public RpcTraceFilter() {
        log.info("Invoke RpcTraceFilter constructor");
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext context = RpcContext.getContext();
        String traceMark;
        if (context.isConsumerSide()) {
            traceMark = getNewIfAbsent();
            log.info("isConsumerSide traceMark: {}", traceMark);
            context.getAttachments().putIfAbsent(TraceMark, traceMark);
        }
        if (context.isProviderSide()) {
            traceMark = setNewIfAbsent(context.getAttachments().get(TraceMark));
            log.info("isProviderSide traceMark: {}", traceMark);
        }
        Result result = null;
        try {
            result = invoker.invoke(invocation);
        } finally {
            if (context.isConsumerSide()) {
                log.info("isConsumerSide result:{}, invoker: {}, invocation: {}", result, invoker, invocation);
            }
            if (context.isProviderSide()) {
                log.info("isProviderSide result:{}, invoker: {}, invocation: {}", result, invoker, invocation);
                clear();
            }
        }
        return result;
    }
}
