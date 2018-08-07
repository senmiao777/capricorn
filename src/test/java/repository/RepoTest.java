package repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frank.entity.mysql.BlindDateComment;
import com.frank.entity.mysql.Stock;
import com.frank.repository.mysql.BlindDateCommentRepository;
import com.frank.repository.mysql.StockRepository;
import com.frank.util.ESUtil;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
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

    public static final String INDEX = "learn";
    public static final String TYPE = "blinddate";
    public static final String INDEX_STOCK = "stock";
    public static final String TYPE_STOCK = "stock_base_info";
    @Autowired
    private BlindDateCommentRepository commentRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private TransportClient client;


    @Test
    public void testAddData() {
        Long begin = 301L;
        Long end = begin + 100;
        for (Long i =begin; i < end; i++) {
            String s = addBlindDataData2ES(i);
            log.info("s={}", s);
        }

//        List<Stock> all = stockRepository.findAll();
//        for (Stock s : all) {
//            addStockDataData2ES(s);
//            log.info("data id = {}", s.getId());
//        }


    }

    private String addStockDataData2ES(Stock one) {

        String s = JSON.toJSONString(one);
        log.info("s={}", s);

        JSONObject jsonObject = JSON.parseObject(s);
        log.info("jsonObject={}", jsonObject);

        IndexResponse response = client.prepareIndex(INDEX_STOCK, TYPE_STOCK, one.getId().toString()).setSource(jsonObject).get();

        log.info("addData response status:{},id:{}", response.status().getStatus(), response.getId());
        return response.getId();
    }

    private String addBlindDataData2ES(Long id) {
        BlindDateComment one = commentRepository.findOne(id);
        if (one == null) {
            return "null";
        }

        /**
         * java.lang.IllegalArgumentException: The number of object passed must be even but was [1]
         * 因为传入的对象不是Map
         */
        String s = JSON.toJSONString(one);
        log.info("s={}", s);

        JSONObject jsonObject = JSON.parseObject(s);
        log.info("jsonObject={}", jsonObject);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(one);
            log.info("json={}", json);
            log.info("equals={}", s.equals(json));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        IndexResponse response = client.prepareIndex(INDEX, TYPE, id.toString()).setSource(jsonObject).get();

        log.info("addData response status:{},id:{}", response.status().getStatus(), response.getId());
        return response.getId();
    }

    @Test
    public void testString22() {
        boolean learn = ESUtil.createIndex("learn");
        log.info("learn={}", learn);

    }

    @Test
    public void testString2() {
        BlindDateComment one = commentRepository.findOne(31L);
        log.info("one={}", one.getRequirement());
        String modify = modify(one.getRequirement());
        log.info("modify={}", modify);
        one.setRequirement(modify);
        BlindDateComment save = commentRepository.save(one);
        log.info("save id ={}", save.getId());
    }

    @Test
    public void testString() {

        String area = "";
        String edu = "";
        String age = "";
        String occupation = "";
        String requirement = "";


        List<BlindDateComment> areas = commentRepository.findAll();
        log.info("areas.size()={}", areas.size());
        for (BlindDateComment c : areas) {
            //   area = modifyArea(c.getArea());
            //          edu = modifyEdu(c.getEducation());
            //    age = modifyAge(c.getAge());
            //  occupation = modifyoccupation(c.getOccupation());
            //     String s = modify(c.getContent());
            //requirement
            requirement = modifyrequirement(c.getRequirement());
            //    c.setAge(age);
            //   c.setArea(area);
            c.setRequirement(requirement);
            //        c.setOccupation(occupation);
            //       c.setEducation(edu);
            // c.setContent(s);
            BlindDateComment save = commentRepository.save(c);
            log.info("save id ={}", save.getId());

        }

    }

    private String modifyrequirement(String str) {
        if (str.contains("：")) {
            str = str.replaceAll("：", "");
        }
        return str;

    }


    private String modifyEdu(String edu) {
        if ("educ".equals(edu)) {
            return "未知";
        } else if (NumberUtils.isDigits(edu)) {
            return "未知";
        } else {
            return edu;
        }
    }

    private String modifyArea(String area) {
        if ("abc".equals(area)) {
            return "未知";
        } else if ("area".equals(area)) {
            return "未知";
        } else {
            return area;
        }
    }

    private String modifyoccupation(String occupation) {
        if ("occupation".equals(occupation)) {
            return "未知";
        } else {
            return occupation;
        }
    }

    private String modifyAge(String age) {
        if (StringUtils.isBlank(age)) {
            return "0";
        }

        if ("age".equals(age)) {
            return "0";
        }

        if (age.contains("+")) {
            age = age.replaceAll("'+'", "");
        }

        if (age.contains("年")) {
            age = age.substring(0, age.indexOf("年"));
        }

        if (age.contains("岁")) {
            age = age.replaceAll("岁", "");
        }
        return age;
    }

    private void delete(BlindDateComment comment) {
        List<String> strings = Splitter.on(",").splitToList(comment.getContent());
        if (strings.size() >= 4) {
            return;
        }

        List<String> strings2 = Splitter.on(" ").splitToList(comment.getContent());
        if (strings2.size() >= 4) {
            return;
        }

        comment.setArea("abc");
        BlindDateComment save = commentRepository.save(comment);
        log.info("save id ={}", save.getId());


    }

    private String modify(String str) {

        // String s = str.replaceAll("：", "");
        String s1 = str.replaceAll("</p>", "");
        String s2 = s1.replaceAll("<p>", "");
        return s2;

    }


}
