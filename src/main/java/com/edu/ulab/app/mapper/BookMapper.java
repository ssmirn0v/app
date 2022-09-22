package com.edu.ulab.app.mapper;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.BookE;
import com.edu.ulab.app.web.request.BookRequest;
import com.edu.ulab.app.web.response.BookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto bookRequestToBookDto(BookRequest bookRequest);

    BookRequest bookDtoToBookRequest(BookDto bookDto);
    
    BookE bookDtoToBookE(BookDto bookDto);

    BookDto bookEToBookDto(BookE update);

    @Mapping(ignore = true, target = "id")
    BookE createBookEFromBookDto(BookDto bookDto);

    BookResponse bookDtoToBookResponse(BookDto bookDto);

    void updateBookEFromBookDto(BookDto bookDto, @MappingTarget BookE bookE);
}
