package ru.otus.spring.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookMongo;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.CommentMongo;
import ru.otus.spring.repository.BookJpaRepository;
import ru.otus.spring.repository.CommentJpaRepository;
import ru.otus.spring.service.Converter;

import java.util.ArrayList;
import java.util.Map;

@Configuration
public class JobConfig {
  public static final String IMPORT_BOOK_JOB_NAME = "importBookJob";

  private static final int CHUNK_SIZE = 5;

  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private PlatformTransactionManager platformTransactionManager;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private BookJpaRepository bookJpaRepository;

  @Autowired
  private CommentJpaRepository commentJpaRepository;

  @Autowired
  private Converter service;

  @Bean
  public RepositoryItemReader<Book> bookReader() {
    return new RepositoryItemReaderBuilder<Book>()
        .repository(bookJpaRepository)
        .name("booksReader")
        .sorts(Map.of())
        .methodName("findAll")
        .arguments(new ArrayList<>())
        .build();
  }

  @Bean
  public ItemProcessor<Book, BookMongo> bookProcessor(Converter service) {
    return service::convertBook;
  }

  @Bean
  public MongoItemWriter<BookMongo> bookWriter() {
    return new MongoItemWriterBuilder<BookMongo>()
        .template(mongoTemplate)
        .collection("books")
        .build();
  }

  @Bean
  public RepositoryItemReader<Comment> commentReader() {
    return new RepositoryItemReaderBuilder<Comment>()
        .repository(commentJpaRepository)
        .name("commentsReader")
        .sorts(Map.of())
        .methodName("findAll")
        .arguments(new ArrayList<>())
        .build();
  }

  @Bean
  public ItemProcessor<Comment, CommentMongo> commentProcessor(Converter service) {
    return service::convertComment;
  }

  @Bean
  public MongoItemWriter<CommentMongo> commentWriter() {
    return new MongoItemWriterBuilder<CommentMongo>()
        .template(mongoTemplate)
        .collection("comments")
        .build();
  }

  @Bean
  public Job importMongo(Step transformBooksStep, Step transformCommentsStep) {
    return new JobBuilder(IMPORT_BOOK_JOB_NAME, jobRepository)
        .incrementer(new RunIdIncrementer())
        .start(transformBooksStep)
        .next(transformCommentsStep)
        .build();
  }

  @Bean
  public Step transformBooksStep(ItemReader<Book> bookReader, MongoItemWriter<BookMongo> bookWriter,
                                 ItemProcessor<Book, BookMongo> bookProcessor) {
    return new StepBuilder("transformBooksStep", jobRepository)
        .<Book, BookMongo>chunk(CHUNK_SIZE, platformTransactionManager)
        .reader(bookReader)
        .processor(bookProcessor)
        .writer(bookWriter)
        .build();
  }

  @Bean
  public Step transformCommentsStep(ItemReader<Comment> commentReader, MongoItemWriter<CommentMongo> commentWriter,
                                    ItemProcessor<Comment, CommentMongo> commentProcessor) {
    return new StepBuilder("transformCommentsStep", jobRepository)
        .<Comment, CommentMongo>chunk(CHUNK_SIZE, platformTransactionManager)
        .reader(commentReader)
        .processor(commentProcessor)
        .writer(commentWriter)
        .build();
  }
}
