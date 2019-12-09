package com.synpore.streamBatchTask;

import com.xueqiu.streaming.batch.task.core.PooledResourceStrategy;
import com.xueqiu.streaming.batch.task.core.SimpleBatchTask;
import com.xueqiu.streaming.batch.task.core.SimpleTaskConfig;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class StreamBatchTaskDemon {
    public static void main(String[] args) {
        SimpleTaskConfig<User> simpleTaskConfig = SimpleTaskConfig.<User>builder()
                .taskName("getResourceTest") //taskName
                .size(1000)     //batch size
                .threadNum(2)
                .strategy(PooledResourceStrategy.CREATE_DESTROY)
                .indexInfo(r -> r.getId())  //
                .identifier(r -> String.valueOf(r.getId()))   //data unique identifier
                .pullData((index, size) ->getData()) //pull data procedure
                .jobContent((e) -> {
                    //doing job
                    try {
                        Thread.sleep(1000 * 10);
                        System.out.println(e.getUserName());
                    } catch (InterruptedException e1) {
                    }
                    return true;
                })
                .build();
        SimpleBatchTask.buildTask(simpleTaskConfig).beginTask();
    }


    static List<User> getData(){
        List<User> data=new ArrayList<>();
            data.add(User.builder().id(1l).userName("tom").age(19).build());
            data.add(User.builder().id(2l).userName("tim").age(20).build());
            data.add(User.builder().id(3l).userName("lucy").age(18).build());
        return data;
    }
}

@Builder
@Data
class User{
    //primary key
    private Long id;
    private String userName;
    private int age;
}
