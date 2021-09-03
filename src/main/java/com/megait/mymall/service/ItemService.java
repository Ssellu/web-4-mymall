package com.megait.mymall.service;

import com.megait.mymall.domain.Album;
import com.megait.mymall.domain.Book;
import com.megait.mymall.repository.AlbumRepository;
import com.megait.mymall.repository.BookRepository;
import com.megait.mymall.repository.CategoryRepository;
import com.megait.mymall.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private final BookRepository bookRepository;
    private final AlbumRepository albumRepository;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    @PostConstruct
    public void createItems() throws IOException {
        // classpath/csv/book.CSV
        ClassPathResource resource2 = new ClassPathResource("csv/album.CSV");

        // 문자열을 모두 받아 List 에 담음. (구분 : 줄바꿈)
        List<String> stringList2 =
                Files.readAllLines(resource2.getFile().toPath(), StandardCharsets.UTF_8);

        List<Album> albumList = new ArrayList<>();
        for(String s : stringList2){
            String[] split = s.split("\\|"); //  '\\|' : 정규식에서의 '|'
            Album album = Album.builder()
                    .name(split[0]) // 상품명
                    .imageUrl(split[1]) // 이미지 경로
                    .price(Integer.parseInt(split[2])) // 가격
                    .stackQuantity((int)(Math.random() * 10)) // 수량 (stock) - 0 ~ 9 랜덤
                    .category(categoryRepository.findById((long)(Math.random() * 3) + 5).orElseThrow()) // 카테고리 8 ~ 11 랜덤
                    .build();
            albumList.add(album);
            log.info("Album : {}", album.toString());
        }
        albumRepository.saveAll(albumList);
    }

//    @PostConstruct
    public void createItems2() throws IOException {

        // classpath/csv/book.CSV
        ClassPathResource resource1 = new ClassPathResource("csv/book.CSV");

        // 문자열을 모두 받아 List 에 담음. (구분 : 줄바꿈)
        List<String> stringList =
                Files.readAllLines(resource1.getFile().toPath(), StandardCharsets.UTF_8);

        List<Book> bookList = stringList.stream().map(s -> {
            String[] split = s.split("\\|"); //  '\\|' : 정규식에서의 '|'

            return Book.builder()
                    .name(split[0]) // 상품명
                    .imageUrl(split[1]) // 이미지 경로
                    .price(Integer.parseInt(split[2])) // 가격
                    .stackQuantity((int) (Math.random() * 10)) // 수량 (stock) - 0 ~ 9 랜덤
                    .category(categoryRepository.findById((long)(Math.random() * 4) + 8).orElseThrow()) // 카테고리 8 ~ 11 랜덤
                    .build();

        }).collect(Collectors.toList());

        bookRepository.saveAll(bookList);
    }
}
