package org.tombear.spring.boot.blog.repository.es;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.tombear.spring.boot.blog.domain.es.EsBlog;

/**
 * <P>Descriptions</P>
 *
 * @author tombear on 2018-08-01 22:26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsBlogRepositoryTest {
    @Autowired
    private EsBlogRepository esBlogRepository;

    @Before
    public void initRepositoryData() {
        esBlogRepository.deleteAll(); // clear all data
        esBlogRepository.save(new EsBlog("Had I not seen the Sun",
                "I could have borne the shade",
                "But Light a newer Wilderness. My Wilderness has made."));
        esBlogRepository.save(new EsBlog("There is room in the halls of pleasure",
                "For a long and lordly train",
                "But one by one we must all file on, Through the narrow aisles of pain."));
        esBlogRepository.save(new EsBlog("When you are old",
                "When you are old and grey and full of sleep",
                "And ndding by the fire, take down this book."));
        esBlogRepository.save(new EsBlog("Json Title",
                "sumary for json",
                "{\"span\":\"f123456\",\"servity\":\"No.1\"}"));
    }

    @Test
    public void testFindDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining() {
        Pageable pageable = PageRequest.of(0, 20);

        String title = "Sun";
        String summary = "is";
        String content = "down";

        Page<EsBlog> page = esBlogRepository.findByTitleContainingOrSummaryContainingOrContentContaining(title, summary, content, pageable);
        printPage(page, 1);

        title = "the";
        summary = "the";
        content = "the";

        page = esBlogRepository.findByTitleContainingOrSummaryContainingOrContentContaining(title, summary, content, pageable);
        printPage(page, 2);


        title = "abc";
        summary = "for json";
        content = "\"span\":\"f123456\"";

        page = esBlogRepository.findByTitleContainingOrSummaryContainingOrContentContaining(title, summary, content, pageable);
        printPage(page, 3);
    }

    public void printPage(Page<EsBlog> page, int index) {
        System.out.println("----------start " + index);
        for (EsBlog esBlog : page) {
            System.out.println(esBlog.toString());
        }
        System.out.println("----------end " + index);
    }
}
