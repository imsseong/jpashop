package me.seongim.jpabook.service;

import me.seongim.jpabook.domain.item.Book;
import me.seongim.jpabook.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 상품등록() throws Exception {
        //given
        Book book = new Book();
        book.setName("book");
        book.setAuthor("seongim");

        //when
        itemService.saveItem(book);

        //then
        assertEquals(book, itemRepository.findOne(book.getId()));
    }

}