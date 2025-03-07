package br.com.acme.springbatch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfig {

    @Bean
    public Job job(JobRepository jobRepository, Step step, Step stepCount, Step imprimeParImparStep) {
        return new JobBuilder("job", jobRepository)
                .start(step)
                .next(stepCount)
                .next(imprimeParImparStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }

}
