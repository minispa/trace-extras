package com.github.minispa.elasticjob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import static com.github.minispa.MDCTraceHelper.*;

public abstract class AbstractSimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        try {
            setNew();
            run(shardingContext);
        } finally {
            clear();
        }
    }

    protected abstract void run(ShardingContext shardingContext);

}
