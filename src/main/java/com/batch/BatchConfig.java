package com.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public Job job( JobBuilderFactory jobBuilderFactory,
                    StepBuilderFactory stepBuilderFactory,
                    ItemReader<User> itemReader,
                    ItemProcessor<User,User> itemProcessor,
                    ItemWriter<User> itemWriter){
        Step s = stepBuilderFactory.get("step1").<User,User>chunk(100)
                .reader(itemReader).processor(itemProcessor).writer(itemWriter).build();
        return jobBuilderFactory.get("job1").start(s).build();
    }

    @Bean
    public FlatFileItemReader<User> itemReader(@Value("${input}")Resource resource){
        FlatFileItemReader<User> f = new FlatFileItemReader<>();
        f.setResource(resource);
        f.setName("userdata");
        f.setLinesToSkip(1);
        f.setLineMapper(linemappe());
        return f;
    }

    @Bean
    public LineMapper<User> linemappe() {
        DefaultLineMapper<User> d = new DefaultLineMapper<>();
        DelimitedLineTokenizer token = new DelimitedLineTokenizer();
        token.setDelimiter(",");
        token.setStrict(false);
        token.setNames(new String[]{"id","name","salary","dep"});
        d.setLineTokenizer(token);
        BeanWrapperFieldSetMapper b = new BeanWrapperFieldSetMapper<>();
        b.setTargetType(User.class);
        d.setFieldSetMapper(b);
        return d;
    }
}
