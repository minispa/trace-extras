package com.github.minispa.dubbo.filter;

import com.github.minispa.MDCTraceHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.MDC;

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
            traceMark = MDC.get(MDCTraceHelper.TraceMark);
            log.info("isConsumerSide traceMark: {}", traceMark);
            if (traceMark == null || traceMark.length() == 0) {
                traceMark = MDCTraceHelper.newTraceMark();
            }
            context.getAttachments().putIfAbsent(MDCTraceHelper.TraceMark, traceMark);
        }
        if (context.isProviderSide()) {
            traceMark = context.getAttachments().getOrDefault(MDCTraceHelper.TraceMark, MDCTraceHelper.newTraceMark());
            log.info("isProviderSide traceMark: {}", traceMark);
            MDC.put(MDCTraceHelper.TraceMark, traceMark);
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
                MDC.clear();
            }
        }
        return result;
    }
}
