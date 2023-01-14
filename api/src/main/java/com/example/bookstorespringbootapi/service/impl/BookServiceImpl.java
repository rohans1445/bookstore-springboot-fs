package com.example.bookstorespringbootapi.service.impl;

import com.example.bookstorespringbootapi.dto.BookDTO;
import com.example.bookstorespringbootapi.dto.BookListDTO;
import com.example.bookstorespringbootapi.dto.PagedResponseDTO;
import com.example.bookstorespringbootapi.entity.ApplicationUser;
import com.example.bookstorespringbootapi.entity.Book;
import com.example.bookstorespringbootapi.entity.Review;
import com.example.bookstorespringbootapi.exception.ResourceNotFoundException;
import com.example.bookstorespringbootapi.mapper.BookMapper;
import com.example.bookstorespringbootapi.repository.BookRepository;
import com.example.bookstorespringbootapi.service.BookService;
import com.example.bookstorespringbootapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final UserService userService;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public PagedResponseDTO<BookListDTO> getPagedResponse(int page, int size){
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Book> paged = bookRepository.findAll(pageable);

        List<BookListDTO> content = paged.getContent().stream().map(book -> {
            return BookListDTO.builder()
                    .id(book.getId())
                    .bookDetail(book.getBookDetail())
                    .price(book.getPrice())
                    .reviewCount(book.getReviews().size())
                    .imgPath(book.getImgPath())
                    .author(book.getAuthor())
                    .shortDesc(book.getShortDesc())
                    .timesPurchased(book.getOwnedBy().size())
                    .avgReviews(getAvgRatingForABook(book))
                    .productPurchased(checkIfProductPurchased(book))
                    .title(book.getTitle())
                    .build();
        }).collect(Collectors.toList());

        PagedResponseDTO<BookListDTO> res = new PagedResponseDTO<>();
        res.setContent(content);
        res.setPage(page);
        res.setSize(size);
        res.setTotalElements(paged.getTotalElements());
        res.setTotalPages(paged.getTotalPages());
        res.setLast(paged.isLast());

        return res;
    }

    @Override
    public Book getBookById(int id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        bookOptional.orElseThrow(() -> new ResourceNotFoundException("Cannot find Book with id: " + id));
        return bookOptional.get();
    }

    @Override
    public Book saveBook(BookDTO bookDTO) {
        Book book = null;
        if(bookDTO.getId() != 0){
            book = getBookById(bookDTO.getId());
        } else {
            book = new Book();
        }

        book.setBookDetail(bookDTO.getBookDetail());
        book.setAuthor(bookDTO.getAuthor());
        book.setShortDesc(bookDTO.getShortDesc());
        book.setTitle(bookDTO.getTitle());
        book.setPrice(bookDTO.getPrice());
        book.setImgPath(bookDTO.getImgPath());

        return bookRepository.save(book);
    }

    @Override
    public List<Book> searchByTitle(String title) {
        return bookRepository.searchByTitle(title);
    }

    @Override
    public double getAvgRatingForABook(Book book) {
        List<Review> reviews = book.getReviews();
        int sum = 0;
        double avg = 0;
        for (Review r: reviews) {
            sum = sum + r.getRating();
        }
        avg = (double)sum/reviews.size();

        return Math.floor(Math.round(avg * 10.0) / 10.0);
    }

    private boolean checkIfProductPurchased(Book book){
        // check if request is anonymous
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken) return false;
        else {
            ApplicationUser currentUser = userService.getCurrentUser();
            return userService.itemExistsInUserInventory(book.getId(), currentUser.getId());
        }
    }

}
