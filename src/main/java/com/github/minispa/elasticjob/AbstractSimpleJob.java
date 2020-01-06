package com.github.minispa.elasticjob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.github.minispa.MDCTraceHelper;
import org.slf4j.MDC;

import java.util.UUID;

public abstract class AbstractSimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            MDC.put(MDCTraceHelper.TraceMark, MDCTraceHelper.newTraceMark());
            run(shardingContext);
        } finally {
            MDC.clear();
        }
    }

    protected abstract void run(ShardingContext shardingContext);

}
