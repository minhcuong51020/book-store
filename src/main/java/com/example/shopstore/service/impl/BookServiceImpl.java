package com.example.shopstore.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.shopstore.convert.ConvertBookToBookDTO;
import com.example.shopstore.dto.BookDTO;
import com.example.shopstore.entity.Author;
import com.example.shopstore.entity.Book;
import com.example.shopstore.entity.Category;
import com.example.shopstore.entity.Publisher;
import com.example.shopstore.repository.AuthorRepository;
import com.example.shopstore.repository.BookRepository;
import com.example.shopstore.repository.CategoryRepository;
import com.example.shopstore.repository.PublisherRepository;
import com.example.shopstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private Cloudinary cloudinary;


    @Override
    public BookDTO addBook(BookDTO bookDTO) {
        MultipartFile multipartFile = bookDTO.getFile();
        String thumbnail = "";
        if(!multipartFile.isEmpty()) {
            try {
                Map r = this.cloudinary.uploader().upload(bookDTO.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                thumbnail = (String) r.get("secure_url");
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        Optional<Category> category = categoryRepository.findById(bookDTO.getCategoryId());
        Optional<Publisher> publisher = publisherRepository.findById(bookDTO.getPublisherId());
        Optional<Author> author = authorRepository.findById(bookDTO.getAuthorId());
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setIsbn(bookDTO.getIsbn());
        book.setCategory(category.get());
        book.setAuthor(author.get());
        book.setPublisher(publisher.get());
        book.setDescription(bookDTO.getDescription());
        book.setPrice(bookDTO.getPrice());
        book.setDiscount(bookDTO.getDiscount());
        book.setThumbnail(thumbnail);
        Optional<Book> optionalBook = Optional.ofNullable(bookRepository.save(book));
        if(optionalBook.isPresent()) {
            bookDTO.setId(book.getId());
            bookDTO.setIsActive(bookDTO.getIsActive());
            bookDTO.setThumbnail(book.getThumbnail());
            bookDTO.setCreatedAt(book.getCreatedAt());
            bookDTO.setUpdatedAt(book.getUpdatedAt());
            return bookDTO;
        }
        return null;
    }

    @Override
    public BookDTO updateBook(BookDTO bookDTO) {
        MultipartFile multipartFile = bookDTO.getFile();
        String thumbnail = "";
        if(!multipartFile.isEmpty()) {
            try {
                Map r = this.cloudinary.uploader().upload(bookDTO.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                thumbnail = (String) r.get("secure_url");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Optional<Category> category = categoryRepository.findById(bookDTO.getCategoryId());
        Optional<Publisher> publisher = publisherRepository.findById(bookDTO.getPublisherId());
        Optional<Author> author = authorRepository.findById(bookDTO.getAuthorId());
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setIsbn(bookDTO.getIsbn());
        book.setTitle(bookDTO.getTitle());
        book.setCategory(category.get());
        book.setPublisher(publisher.get());
        book.setAuthor(author.get());
        book.setDescription(bookDTO.getDescription());
        book.setPrice(bookDTO.getPrice());
        book.setIsActive(bookDTO.getIsActive());
        book.setDiscount(bookDTO.getDiscount());
        if(thumbnail.length() != 0) {
            book.setThumbnail(thumbnail);
        } else {
            book.setThumbnail(bookDTO.getThumbnail());
        }
        Optional<Book> optionalBook = Optional.ofNullable(bookRepository.save(book));
        if(optionalBook.isPresent()) {
            bookDTO.setThumbnail(book.getThumbnail());
            bookDTO.setUpdatedAt(book.getUpdatedAt());
            return bookDTO;
        }
        return null;
    }

    @Override
    public void deleteBook(int id) {

    }

    @Override
    public BookDTO getBookById(int id) {
        Optional<Book> optional = bookRepository.findById(id);
        if (!optional.isPresent()) {
            return null;
        }
        Book book = optional.get();
        BookDTO bookDTO = ConvertBookToBookDTO.convertBookToBookDTO(book);
        return bookDTO;
    }

    @Override
    public List<BookDTO> getAllBook() {
        List<Book> books = bookRepository.findAll();
        List<BookDTO> bookDTOS = new ArrayList<>();
        for (Book book : books) {
            BookDTO bookDTO = ConvertBookToBookDTO.convertBookToBookDTO(book);
            bookDTOS.add(bookDTO);
        }
        return bookDTOS;
    }

    @Override
    public List<BookDTO> getAllBookByIsActive(int isActive) {
        List<Book> books = bookRepository.findAllByIsActiveEquals(isActive);
        List<BookDTO> bookDTOS = new ArrayList<>();
        for (Book book : books) {
            BookDTO bookDTO = ConvertBookToBookDTO.convertBookToBookDTO(book);
            bookDTOS.add(bookDTO);
        }
        return bookDTOS;
    }

    @Override
    public boolean existBookById(int id) {
        return bookRepository.existsById(id);
    }

    @Override
    public List<BookDTO> findTopBookByDiscount(double discount, int isActive) {
        List<Book> books = bookRepository.findTop4ByDiscountGreaterThanAndIsActiveEqualsOrderByIdDesc(discount, isActive);
        List<BookDTO> bookDTOS = new ArrayList<>();
        for (Book book : books) {
            BookDTO bookDTO = ConvertBookToBookDTO.convertBookToBookDTO(book);
            bookDTOS.add(bookDTO);
        }
        return bookDTOS;
    }

    @Override
    public List<BookDTO> findAllByDiscountGreaterThan(double discount) {
        List<Book> books = bookRepository.findAllByDiscountGreaterThanOrderByIdDesc(discount);
        List<BookDTO> bookDTOS = new ArrayList<>();
        for (Book book : books) {
            BookDTO bookDTO = ConvertBookToBookDTO.convertBookToBookDTO(book);
            bookDTOS.add(bookDTO);
        }
        return bookDTOS;
    }

    @Override
    public List<BookDTO> findTopBookRandom(int isActive) {
        bookRepository.findAllByIsActiveEquals(isActive);
        List<Book> books = bookRepository.findAllByIsActiveEquals(isActive);
        int max = books.size() - 1;
        int min = 1;
        Set<Integer>set = new HashSet<>();
        while (set.size() != 4) {
            Random random = new Random();
            int ids = random.nextInt(max) + min;
            set.add(ids);
        }
        Iterator<Integer> iterator = set.iterator();
        List<Book> booksTop = new ArrayList<>();
        while (iterator.hasNext()) {
            booksTop.add(books.get(iterator.next()));
        }
        List<BookDTO> bookDTOS = new ArrayList<>();
        for (Book book : booksTop) {
            BookDTO bookDTO = ConvertBookToBookDTO.convertBookToBookDTO(book);
            bookDTOS.add(bookDTO);
        }
        return bookDTOS;
    }

    @Override
    public List<BookDTO> findTopBookNew(int isActive) {
        List<Book> books = bookRepository.findTop4ByIsActiveEqualsOrderByCreatedAtDesc(isActive);
        System.out.println(books.size());
        List<BookDTO> bookDTOS = new ArrayList<>();
        for (Book book : books) {
            BookDTO bookDTO = ConvertBookToBookDTO.convertBookToBookDTO(book);
            bookDTOS.add(bookDTO);
        }
        return bookDTOS;
    }

    @Override
    public Page<BookDTO> findAllBookByCategoryAndIsActive(String slugCategory, int isActive, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Book> page = bookRepository.findAllByCategorySlugAndIsActive(slugCategory, isActive, pageable);
        Page<BookDTO> pageDTO = page.map(book-> ConvertBookToBookDTO.convertBookToBookDTO(book));
        return pageDTO;
    }

    @Override
    public BookDTO getBookByIdAndIsActive(int id, int isActive) {
        Optional<Book> book = Optional.ofNullable(bookRepository.getBookByIdAndIsActive(id, isActive));
        if(!book.isPresent()) {
            return null;
        }
        BookDTO bookDTO = ConvertBookToBookDTO.convertBookToBookDTO(book.get());
        return bookDTO;
    }
}
