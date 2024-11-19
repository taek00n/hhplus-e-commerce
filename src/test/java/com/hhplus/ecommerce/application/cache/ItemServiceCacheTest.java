package com.hhplus.ecommerce.application.cache;

import com.hhplus.ecommerce.application.ItemService;
import com.hhplus.ecommerce.domain.Item;
import com.hhplus.ecommerce.domain.repository.ItemRepository;
import com.hhplus.ecommerce.domain.repository.OrderDetailRepository;
import com.hhplus.ecommerce.domain.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@EnableCaching
public class ItemServiceCacheTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @MockBean
    private ItemRepository itemRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        cacheManager.getCache("allItems").clear();
        cacheManager.getCache("topItems").clear();
    }

    @Test
    @DisplayName("인기상품조회에서 2번째 조회할때 캐쉬를 사용하는지 확인")
    void getTopItemWithCache() {
        //given
        LocalDateTime endDateTime = LocalDate.now().atStartOfDay();
        LocalDateTime startDateTime = endDateTime.minusDays(3);
        Item item1 = new Item("청바지1", 10000, 4);
        Item item2 = new Item("청바지2", 20000, 5);
        Item item3 = new Item("청바지3", 30000, 6);
        List<Item> topItemList = new ArrayList<>();
        topItemList.add(item1);
        topItemList.add(item2);
        topItemList.add(item3);
        when(itemRepository.findTopItems(startDateTime, endDateTime)).thenReturn(topItemList);

        //when1
        List<Item> getTopItemsFirstTime = itemService.getTopItems();
        //then1
        assertThat(getTopItemsFirstTime).isNotNull();
        assertThat(getTopItemsFirstTime).isEqualTo(topItemList);
        verify(itemRepository, times(1)).findTopItems(startDateTime, endDateTime);

        //when2
        List<Item> getTopItemsSecondTime = itemService.getTopItems();
        assertThat(getTopItemsSecondTime).isNotNull();
//        assertThat(getTopItemsSecondTime).isEqualTo(topItemList);
        verify(itemRepository, times(1)).findTopItems(startDateTime, endDateTime);
    }

    @Test
    @DisplayName("인기상품조회에서 하루가 지나서 조회했을때 캐시를 조회해서 가져오는지 확인")
    void getTopItemWithCacheAfter1Day() {
        //given
        LocalDateTime endDateTime = LocalDate.now().atStartOfDay();
        LocalDateTime startDateTime = endDateTime.minusDays(3);
        Item item1 = new Item("청바지1", 10000, 4);
        Item item2 = new Item("청바지2", 20000, 5);
        Item item3 = new Item("청바지3", 30000, 6);
        List<Item> topItemList = new ArrayList<>();
        topItemList.add(item1);
        topItemList.add(item2);
        topItemList.add(item3);
        when(itemRepository.findTopItems(startDateTime, endDateTime)).thenReturn(topItemList);

        //when1
        List<Item> getTopItemsFirstTime = itemService.getTopItems();
        //then1
        assertThat(getTopItemsFirstTime).isNotNull();
        assertThat(getTopItemsFirstTime).isEqualTo(topItemList);
        verify(itemRepository, times(1)).findTopItems(startDateTime, endDateTime);

        Clock clock = Clock.fixed(Instant.parse("2024-11-09T00:00:00.00Z"), ZoneId.systemDefault());
        clock = Clock.offset(clock, Duration.ofDays(1));
        LocalDateTime now = LocalDateTime.now(clock);
        System.out.println("하루 경과 : " + now);

        //when2
        List<Item> getTopItemsSecondTime = itemService.getTopItems();
        assertThat(getTopItemsSecondTime).isNotNull();
        verify(itemRepository, times(1)).findTopItems(startDateTime, endDateTime);
    }

    @Test
    @DisplayName("전체상품조회에서 2번째 조회할때 캐쉬를 사용하는지 확인")
    void getAllItemWithCache() {
        //given
        LocalDateTime endDateTime = LocalDate.now().atStartOfDay();
        LocalDateTime startDateTime = endDateTime.minusDays(3);
        Item item1 = new Item("청바지1", 10000, 4);
        Item item2 = new Item("청바지2", 20000, 5);
        Item item3 = new Item("청바지3", 30000, 6);
        List<Item> topItemList = new ArrayList<>();
        topItemList.add(item1);
        topItemList.add(item2);
        topItemList.add(item3);
        when(itemRepository.findAll()).thenReturn(topItemList);

        //when1
        List<Item> getAllItemsFirstTime = itemService.getItemAll();
        //then1
        assertThat(getAllItemsFirstTime).isNotNull();
        assertThat(getAllItemsFirstTime).isEqualTo(topItemList);
        verify(itemRepository, times(1)).findAll();

        //when2
        List<Item> getAllItemsSecondTime = itemService.getTopItems();
        assertThat(getAllItemsSecondTime).isNotNull();
        verify(itemRepository, times(1)).findAll();
    }

}
