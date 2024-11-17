package com.example.library_api;
import static org.junit.jupiter.api.Assertions.*;
import com.example.library_api.entities.Author;
import com.example.library_api.entities.Book;
import com.example.library_api.entities.Reader;
import com.example.library_api.entities.TransactionBook;
import com.example.library_api.repositories.AuthorRepository;
import com.example.library_api.repositories.BookRepository;
import com.example.library_api.repositories.ReaderRepository;
import com.example.library_api.repositories.TransactionBookRepository;
import com.example.library_api.services.AuthorService;
import com.example.library_api.services.BookService;
import com.example.library_api.services.ReaderService;
import com.example.library_api.services.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.example.library_api.exceptions.NotFoundException;
@SpringBootTest
class LibraryApiApplicationTests {
@Autowired
	private AuthorService authorService;
@Autowired
private BookRepository bookRepository;
@Autowired
private BookService bookService;
@Autowired
private AuthorRepository authorRepository;
	@Test
	void contextLoads() {
	}
	@Test
	void getAllAuthors(){

		List<Author> authors = authorService.getAllAuthors();
		assertNotNull(authors);
	}

	@Test
	@Transactional
		// Откат изменений после теста
	void addAuthor() {
		Book book =Book.builder()
				.name("TestBook")
				.yearCreate(1992).
				build();
		bookRepository.save(book);
		Set<Book>books=new HashSet<>();
		books.add(book);
				Author author = Author.builder()
				.name("Иван")
				.surname("Иванов")
				.dateBirth(LocalDate.of(1990, 1, 1))
				.books(books)
				.build();

		// Сохраняем автора
		Author savedAuthor = authorService.addAuthor(author);


		// Проверяем, что автор был сохранен и имеет ID
		assertNotNull(savedAuthor.getId());
		assertEquals(author.getName(), savedAuthor.getName());
		assertEquals(author.getSurname(), savedAuthor.getSurname());
		assertEquals(author.getDateBirth(), savedAuthor.getDateBirth());
		assertEquals(author.getBooks(), savedAuthor.getBooks());
	}
	@Test
	void testGetMostPopularAuthor() {
	transactionBookRepository.deleteAll();
        bookRepository.deleteAll();
        authorRepository.deleteAll();

	Author author1 = new Author();
        author1.setName("Author One");
        authorRepository.save(author1);

	Author author2 = new Author();
        author2.setName("Author Two");
        authorRepository.save(author2);

	Book book1 = new Book();
        book1.setName("Book One");
        book1.setYearCreate(2020);
        book1.setAuthors(Collections.singletonList(author1));
        bookRepository.save(book1);

	Book book2 = new Book();
        book2.setName("Book Two");
        book2.setYearCreate(2021);
        book2.setAuthors(Collections.singletonList(author2));
        bookRepository.save(book2);

	TransactionBook transaction1 = new TransactionBook();
        transaction1.setBook(book1);
        transaction1.setDateTime(LocalDateTime.now().minusDays(1)); // Yesterday
        transactionBookRepository.save(transaction1);

	TransactionBook transaction2 = new TransactionBook();
        transaction2.setBook(book2);
        transaction2.setDateTime(LocalDateTime.now().minusDays(2)); // Two days ago
        transactionBookRepository.save(transaction2);

	TransactionBook transaction3 = new TransactionBook();
        transaction3.setBook(book1);
        transaction3.setDateTime(LocalDateTime.now().minusDays(3)); // Three days ago
        transactionBookRepository.save(transaction3);

		LocalDate startDate = LocalDate.now().minusDays(5);
		LocalDate endDate = LocalDate.now();

		Author mostPopularAuthor = transactionService.getMostPopularAuthor(startDate, endDate);

		assertNotNull(mostPopularAuthor);
		assertEquals("Author One", mostPopularAuthor.getName());
	}
	@Test
	void getAllBooks(){
		List<Book>books=bookService.getAllBooks();
		assertNotNull(books);
	}
	@Test
	void addBook(){
		Book book= Book.builder()
				.name("Test book")
				.yearCreate(1780)
				.build();
		bookRepository.deleteAll();
		Book initBook = bookService.addBook(book);
		Assertions.assertNotNull(initBook);
		List<Book> finalBook = bookRepository.findAll();
		Assertions.assertNotNull(finalBook);
		Assertions.assertEquals(initBook.getId(), finalBook.get(0).getId());
		Assertions.assertEquals(initBook.getName(), finalBook.get(0).getName());
		Assertions.assertEquals(initBook.getYearCreate(), finalBook.get(0).getYearCreate());
	}
	@Autowired
	private ReaderService readerService;
	@Autowired
	private ReaderRepository readerRepository;
@Autowired
private TransactionBookRepository transactionBookRepository;
	@Test
	void getPopularReader(){
		Reader reader1 = new Reader();
		reader1.setName("Reader 1");
		readerRepository.save(reader1);

		Reader reader2 = new Reader();
		reader2.setName("Reader 2");
		readerRepository.save(reader2);
		//create transaction for 1 reader
		TransactionBook transactionBook1 = new TransactionBook();
transactionBook1.setReader(reader1);
transactionBookRepository.save(transactionBook1);
TransactionBook transactionBook2 = new TransactionBook();
transactionBook2.setReader(reader1);
transactionBookRepository.save(transactionBook2);
// Создаем транзакцию для reader2
		TransactionBook transaction3 = new TransactionBook();
		transaction3.setReader(reader2);
		transactionBookRepository.save(transaction3);
		Reader mostPopularReader=readerService.getMostPopularReader();
		// Проверим, что полученный читатель это тот, у кого больше транзакций
		assertNotNull(mostPopularReader);
		assertEquals("Reader 1", mostPopularReader.getName());
	}

	@Test
	void getReadersSortedByUnreturnedBooksTest() {
		// Создаем читателей
		Reader reader1 = new Reader();
		reader1.setName("Reader 1");
		reader1.setSurname("Surname 1");
		readerRepository.save(reader1);

		Reader reader2 = new Reader();
		reader2.setName("Reader 2");
		reader2.setSurname("Surname 2");
		readerRepository.save(reader2);

		// Создаем транзакции для reader1 (не возвращенные книги)
		TransactionBook t1 = new TransactionBook();
		t1.setReader(reader1);
		t1.setTypeOperation("issue"); // Не возвращена
		transactionBookRepository.save(t1);

		TransactionBook t2 = new TransactionBook();
		t2.setReader(reader2);
		t2.setTypeOperation("issue"); // Не возвращена
		transactionBookRepository.save(t2);

		// Создаем транзакцию для reader2 (возвращенная книга)
		TransactionBook t3 = new TransactionBook();
		t3.setReader(reader2);
		t3.setTypeOperation("return"); // Возвращена
		transactionBookRepository.save(t3);

		// Вызовем метод для получения читателей, отсортированных по не возвращенным книгам
		List<Reader> sortedReaders = readerService.getReadersSortedByUnreturnedBooks();

		// Проверим, что читатель с наибольшим количеством не возвращенных книг в начале списка
		assertNotNull(sortedReaders);
		assertEquals(2, sortedReaders.size());  // Два читателя
		assertEquals("Reader 1", sortedReaders.get(0).getName());  // Reader 1 должен быть первым
		assertEquals("Reader 2", sortedReaders.get(1).getName());  // Reader 2 должен быть вторым, так как у него меньше не возвращенных книг
	}
	@Autowired
	private TransactionService transactionService;
	@BeforeEach
	void setUp() {
		// Создание и сохранение читателя
		Reader reader = new Reader();
		reader.setName("John Doe");
		reader.setSurname("Test");
		reader.setPhoneNumber(123456789);
		reader.setDateOfBirth(LocalDate.of(1990, 1, 1));
		readerRepository.save(reader);

		// Создание и сохранение книги
		Book book = new Book();
		book.setName("Test Book");
		book.setYearCreate(2020);
		bookRepository.save(book);
	}
	@Test
	void testPerformTransactionIssue() {
		// Получаем читателя и книгу из базы данных
		Reader reader = readerRepository.findAll().get(0);
		Book book = bookRepository.findAll().get(0);

		// Выполнение транзакции "issue"
		TransactionBook transaction = transactionService.performTransaction(reader.getId(), book.getId(), "issue");

		// Проверка, что транзакция была выполнена
		assertNotNull(transaction);
		assertEquals("issue", transaction.getTypeOperation());
		assertNotNull(transaction.getDateTime());
		assertEquals(reader.getId(), transaction.getReader().getId());
		assertEquals(book.getId(), transaction.getBook().getId());
	}

	@Test
	void testPerformTransactionReturn() {
		Reader reader = readerRepository.findAll().get(0);
		Book book = bookRepository.findAll().get(0);

		// Выполнение транзакции "return"
		TransactionBook transaction = transactionService.performTransaction(reader.getId(), book.getId(), "return");

		// Проверка, что транзакция была выполнена
		assertNotNull(transaction);
		assertEquals("return", transaction.getTypeOperation());
		assertNotNull(transaction.getDateTime());
		assertEquals(reader.getId(), transaction.getReader().getId());
		assertEquals(book.getId(), transaction.getBook().getId());
	}

	@Test
	void testPerformTransactionReserve() {
		Reader reader = readerRepository.findAll().get(0);
		Book book = bookRepository.findAll().get(0);

		// Выполнение транзакции "reserve"
		TransactionBook transaction = transactionService.performTransaction(reader.getId(), book.getId(), "reserve");

		// Проверка, что транзакция была выполнена
		assertNotNull(transaction);
		assertEquals("reserve", transaction.getTypeOperation());
		assertNotNull(transaction.getDateTime());
		assertEquals(reader.getId(), transaction.getReader().getId());
		assertEquals(book.getId(), transaction.getBook().getId());
	}

	@Test
	void testPerformTransactionWithInvalidOperation() {
		Reader reader = readerRepository.findAll().get(0);
		Book book = bookRepository.findAll().get(0);

		// Ожидаем исключение при неправильной операции
		assertThrows(IllegalArgumentException.class, () -> {
			transactionService.performTransaction(reader.getId(), book.getId(), "invalidOperation");
		});
	}

	@Test
	void testPerformTransactionWithNotFoundReader() {
		Long invalidReaderId = 999L;  // Не существующий ID
		Long bookId = 1L;             // ID книги

		// Ожидаем исключение при несуществующем читателе
		assertThrows(NotFoundException.class, () -> {
			transactionService.performTransaction(invalidReaderId, bookId, "issue");
		});
	}

	@Test
	void testPerformTransactionWithNotFoundBook() {
		Long readerId = 1L;      // ID существующего читателя
		Long invalidBookId = 999L;  // Не существующий ID книги

		// Ожидаем исключение при несуществующей книге
		assertThrows(NotFoundException.class, () -> {
			transactionService.performTransaction(readerId, invalidBookId, "issue");
		});
	}

	}

