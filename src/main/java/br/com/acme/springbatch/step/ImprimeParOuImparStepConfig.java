package br.com.acme.springbatch.step;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ImprimeParOuImparStepConfig {

    int count = 0;

    @Bean
    public Step step(JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            Tasklet ImprimeOlaTasklet) {
        return new StepBuilder("step", jobRepository)
                .tasklet(ImprimeOlaTasklet, transactionManager)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step stepCount(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("stepCount", jobRepository)
                .tasklet((StepContribution contribution, ChunkContext chunkContext) -> {
                    System.out.println(count++);
                    if (this.count > 5) {
                        return RepeatStatus.FINISHED;
                    }
                    return RepeatStatus.CONTINUABLE;
                }, transactionManager)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step imprimeParImparStep(
            JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("imprimeParImparStep", jobRepository)
                .<Integer, String>chunk(1, transactionManager)
                .reader(contaAteDezReader())
                .processor(parOuImparProcessor())
                .writer(imprimeWriter())
                .build();
    }

    public IteratorItemReader<Integer> contaAteDezReader() {
        List<Integer> contaAteDez = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        return new IteratorItemReader<Integer>(contaAteDez.iterator());
    }

    public FunctionItemProcessor<Integer, String> parOuImparProcessor() {
        return new FunctionItemProcessor<>(item -> item % 2 == 0 ? String.format("O número %s é par", item)
                : String.format("O número %s é impar", item));
    }

    public ItemWriter<String> imprimeWriter() {
        return itens -> itens.forEach(System.out::println);
    }

}
