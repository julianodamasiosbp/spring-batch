package br.com.acme.springbatch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ImprimeOlaTaskletConfig implements Tasklet {

    @Override
    @Nullable
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("##############################");
        System.out.println("Job executado com sucesso!");
        System.out.println("##############################");
        return RepeatStatus.FINISHED;
    }

}
