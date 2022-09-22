package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.BookE;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import static com.edu.ulab.app.util.SetterHelper.setIfNotNull;

@Slf4j
@Service
public class BookServiceImpl implements BookService {
    BookRepository bookRepository;
    BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        BookE bookE = bookMapper.createBookEFromBookDto(bookDto);
        bookRepository.save(bookE);
        BookDto bookDtoCreated = bookMapper.bookEToBookDto(bookE);
        return bookDtoCreated;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) throws Throwable {
        Long id = bookDto.getId();
        BookE bookEForUpdate = bookRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("Book with id: " + id + " was not found"));
        bookMapper.updateBookEFromBookDto(bookDto, bookEForUpdate);
        BookDto bookDtoChanged = bookMapper.bookEToBookDto(bookEForUpdate);
        return bookDtoChanged;
    }

    @Override
    public BookDto getBookById(Long id) {
        BookE bookE = bookRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("Book with id: " + id + " was not found"));
        BookDto bookDto = bookMapper.bookEToBookDto(bookE);
        return bookDto;
    }

    @Override
    public void deleteBookById(Long id) {
        log.info("deleting book by id: {}", id);
        bookRepository.deleteById(id);
    }
}
