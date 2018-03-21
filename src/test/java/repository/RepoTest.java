package repository;

import com.frank.entity.mysql.BlindDateComment;
import com.frank.repository.mysql.BlindDateCommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/21 0021 下午 4:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@ComponentScan(basePackages = "com.frank")
@SpringBootTest(classes = RepoTest.class)
@Rollback(false)
@Slf4j
public class RepoTest {

    @Autowired
    private BlindDateCommentRepository commentRepository;

    @Test
    public void testString() {

        String area = "";
        String edu = "";
        String age = "";
        String occupation = "";
        String requirement = "";


        List<BlindDateComment> areas = commentRepository.findByAreaNot("area");
        log.info("areas.size()={}", areas.size());
        for (BlindDateComment c : areas) {
            area = modify(c.getArea());
            edu = modify(c.getEducation());
            age = modify(c.getAge());
            occupation = modify(c.getOccupation());
            requirement = modify(c.getRequirement());


            c.setAge(age);
            c.setArea(area);
            c.setOccupation(occupation);
            c.setEducation(edu);
            c.setRequirement(requirement);
            commentRepository.save(c);
        }

    }

    private String modify(String str) {
        if (str.contains(":")) {
            str = str.substring(str.indexOf(":") + 1);
        }
        return str.trim();

    }


}
