package com.dangdang.ddframe.job.event.type;

import com.dangdang.ddframe.job.context.ExecutionType;
import com.dangdang.ddframe.job.event.JobEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

/**
 * 作业状态痕迹事件.
 *
 * @author caohao
 */
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public final class JobStatusTraceEvent implements JobEvent {

    /**
     * 主键
     */
    private String id = UUID.randomUUID().toString();
    /**
     * 作业名称
     */
    private final String jobName;
    /**
     * 原作业任务ID
     */
    @Setter
    private String originalTaskId = "";
    /**
     * 作业任务ID
     * 来自 {@link com.dangdang.ddframe.job.executor.ShardingContexts#taskId}
     */
    private final String taskId;
    /**
     * 执行作业服务器的名字
     * Elastic-Job-Lite，作业节点的 IP 地址
     * Elastic-Job-Cloud，Mesos 执行机主键
     */
    private final String slaveId;
    /**
     * 任务来源
     */
    private final Source source;
    /**
     * 任务执行类型
     */
    private final ExecutionType executionType;
    /**
     * 作业分片项
     * 多个分片项以逗号分隔
     */
    private final String shardingItems;
    /**
     * 任务执行状态
     */
    private final State state;
    /**
     * 相关信息
     */
    private final String message;
    /**
     * 记录创建时间
     */
    private Date creationTime = new Date();

    /**
     * 任务执行状态
     */
    public enum State {
        /**
         * 开始中
         */
        TASK_STAGING,
        /**
         * 运行中
         */
        TASK_RUNNING,
        /**
         * 完成（正常）
         */
        TASK_FINISHED,
        /**
         * 完成（异常）
         */
        TASK_ERROR,

        // Elastic-Job-Cloud 如下独有
        TASK_KILLED,
        TASK_LOST,
        TASK_FAILED,
        TASK_DROPPED,
        TASK_GONE,
        TASK_GONE_BY_OPERATOR,
        TASK_UNREACHABLE,
        TASK_UNKNOWN
    }

    /**
     * 任务来源
     */
    public enum Source {
        /**
         * Elastic-Job-Cloud 调度器
         */
        CLOUD_SCHEDULER,
        /**
         * Elastic-Job-Cloud 执行器
         */
        CLOUD_EXECUTOR,
        /**
         * Elastic-Job-Lite 执行器
         */
        LITE_EXECUTOR
    }
}
