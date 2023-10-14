package ru.otus.spring.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
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
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.AuthorMongo;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookMongo;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.CommentMongo;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.domain.GenreMongo;
import ru.otus.spring.repository.AuthorJpaRepository;
import ru.otus.spring.repository.BookJpaRepository;
import ru.otus.spring.repository.CommentJpaRepository;
import ru.otus.spring.repository.GenreJpaRepository;
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
  private AuthorJpaRepository authorJpaRepository;

  @Autowired
  private GenreJpaRepository genreJpaRepository;

  @Autowired
  private BookJpaRepository bookJpaRepository;

  @Autowired
  private CommentJpaRepository commentJpaRepository;

  @Autowired
  private Converter service;

  @Bean
  public Flow splitFlow(Flow flowAuthor, Flow flowGenre, TaskExecutor taskExecutor) {
    return new FlowBuilder<SimpleFlow>("splitFlow")
        .split(taskExecutor)
        .add(flowAuthor, flowGenre)
        .build();
  }

  @Bean
  public Flow flowAuthor(Step transformAuthorsStep) {
    return new FlowBuilder<SimpleFlow>("flowAuthor")
        .start(transformAuthorsStep)
        .build();
  }

  @Bean
  public Flow flowGenre(Step transformGenresStep) {
    return new FlowBuilder<SimpleFlow>("flowGenre")
        .start(transformGenresStep)
        .build();
  }

  @Bean
  public TaskExecutor taskExecutor() {
    return new SimpleAsyncTaskExecutor("spring_batch");
  }

  @Bean
  public RepositoryItemReader<Author> authorReader() {
    return new RepositoryItemReaderBuilder<Author>()
        .repository(authorJpaRepository)
        .name("authorsReader")
        .sorts(Map.of())
        .methodName("findAll")
        .arguments(new ArrayList<>())
        .build();
  }

  @Bean
  public ItemProcessor<Author, AuthorMongo> authorProcessor(Converter service) {
    return service::convertAuthor;
  }

  @Bean
  public MongoItemWriter<AuthorMongo> authorWriter() {
    return new MongoItemWriterBuilder<AuthorMongo>()
        .template(mongoTemplate)
        .collection("authors")
        .build();
  }

  @Bean
  public RepositoryItemReader<Genre> genreReader() {
    return new RepositoryItemReaderBuilder<Genre>()
        .repository(genreJpaRepository)
        .name("genresReader")
        .sorts(Map.of())
        .methodName("findAll")
        .arguments(new ArrayList<>())
        .build();
  }

  @Bean
  public ItemProcessor<Genre, GenreMongo> genreProcessor(Converter service) {
    return service::convertGenre;
  }

  @Bean
  public MongoItemWriter<GenreMongo> genreWriter() {
    return new MongoItemWriterBuilder<GenreMongo>()
        .template(mongoTemplate)
        .collection("genres")
        .build();
  }

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
  public Job importMongo(Flow splitFlow, Step transformBooksStep, Step transformCommentsStep) {
    return new JobBuilder(IMPORT_BOOK_JOB_NAME, jobRepository)
        .incrementer(new RunIdIncrementer())
        .start(splitFlow)
        .next(transformBooksStep)
        .next(transformCommentsStep)
        .build()
        .build();
  }

  @Bean
  public Step transformAuthorsStep(ItemReader<Author> authorReader, MongoItemWriter<AuthorMongo> authorWriter,
                                   ItemProcessor<Author, AuthorMongo> authorProcessor) {
    return new StepBuilder("transformAuthorsStep", jobRepository)
        .<Author, AuthorMongo>chunk(CHUNK_SIZE, platformTransactionManager)
        .reader(authorReader)
        .processor(authorProcessor)
        .writer(authorWriter)
        .build();
  }

  @Bean
  public Step transformGenresStep(ItemReader<Genre> genreReader, MongoItemWriter<GenreMongo> genreWriter,
                                  ItemProcessor<Genre, GenreMongo> genreProcessor) {
    return new StepBuilder("transformGenresStep", jobRepository)
        .<Genre, GenreMongo>chunk(CHUNK_SIZE, platformTransactionManager)
        .reader(genreReader)
        .processor(genreProcessor)
        .writer(genreWriter)
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
