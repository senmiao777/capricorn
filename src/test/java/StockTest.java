import com.alibaba.fastjson.JSON;
import com.frank.entity.mysql.Benefit;
import com.frank.entity.mysql.IncomeStatement;
import com.frank.entity.mysql.Stock;
import com.frank.entity.mysql.User;
import com.frank.model.Result;
import com.frank.repository.mysql.BenefitRepository;
import com.frank.repository.mysql.IncomeStatementRepository;
import com.frank.service.IDbService;
import com.frank.service.IUserService;
import com.frank.util.GenerateUtil;
import com.frank.util.HttpUtil;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

//import org.springframework.boot.test.SpringApplicationConfiguration;

/**
 * Created by Administrator on 2017/4/25 0025.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@ComponentScan(basePackages = "com.frank")
@SpringBootTest(classes = StockTest.class)
//@Rollback(false)
@Slf4j
public class StockTest {

    @Autowired
    private BenefitRepository benefitRepository;

    @Autowired
    private IncomeStatementRepository incomeStatementRepository;

    @Autowired
    private IDbService dbService;

    @Autowired
    private IUserService userService;

//    @Autowired
//    @Qualifier(value="testTaskPoolExecutor")
//    private Executor testTaskPoolExecutor;

/*	@Autowired
    private BenefitService benefitService;*/


    private static final String ACCESS_TOKEN = "feca4dcb1241d2564ff37534a9f509705a5154664ff39364849639145ab9f1b3";

    private static CloseableHttpClient httpClient = HttpUtil.getClient();

    private final AtomicLong num = new AtomicLong(0L);
    volatile int count = 0;

    @Test
    public void testSave(){

        User byPhone = userService.findByPhone(13286230000L);
        //userService.update(byPhone);
        byPhone.setAge(66);

    }

    @Test
    public void testTransaction() {
        User user =User.generateUser();
        user.setUserName("事务测试");
        Stock stock = new Stock();
        stock.setCode("test00");
        stock.setArea("江苏");
       // dbService.saveUserAndStockWithException(user,stock);
        dbService.saveUserAndStock(user,stock);

    }

    @Test
    public void testVolatile() {


        Executor executor = Executors.newScheduledThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    count++;
                    try {
                        java.lang.Thread.sleep(RandomUtils.nextInt(5, 15));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        log.info("count={}", count);

    }

    @Test
    public void sql2Entity() {
        long l = num.incrementAndGet();

        List<String> attrList = Lists.newArrayList("id bigint(20) NOT NULL AUTO_INCREMENT",
                "area varchar(40)",
                "occupation varchar(40)",
                "requirement varchar(256) ",
                "content varchar(1024) ",
                "user_id varchar(30) ",
                "user_name varchar(64) ",
                "url_token varchar(100) ",
                "head_image varchar(256) ",
                "gender tinyint(1) default 1",
                "register_gender tinyint(1) default 1",
                "register_at bigint(20) ",
                "headline varchar(256) ");

        String sql = GenerateUtil.sqlList2Entity(attrList);
        log.info("sqlList2Entity sql = {}",sql.toString());


    }

    private String getAttributeType(String type) {
        if (type.contains("varchar")) {
            return "private String ";
        } else if (type.contains("bigint")) {
            return "private Long ";
        } else if (type.contains("int")) {
            return "private Integer ";
        } else {
            return "private String ";
        }
    }

    /**
     * 拼接实体类
     *
     * @param s
     * @param list
     * @return
     */
    private StringBuilder get2CurrentString(StringBuilder s, List<String> list) {
        s.append("/**").append(list.get(2)).append("*/").append("@Column(name = \"").append(list.get(0))
                .append("\")").append("private ").append(getType(list.get(1))).append(" ").append(list.get(0)).append(";\n\n");
        return s;
    }


    @Async("testTaskPoolExecutor")
    public void count(int k) {
        log.info("threadID={},before k={}", java.lang.Thread.currentThread().getId(), k);
        k = k++;
        log.info("threadID={},after k={}", java.lang.Thread.currentThread().getId(), k);
    }

    @Test
    public void t() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String s = "2018-09-09";
        LocalDate date = LocalDate.parse(s, formatter);
        String s1 = date.toString();
        String s2 = StringUtils.replaceAll(s, "-", "");
        log.info("1111111111111:={}", s2);
    }

    @Test
    @Transactional
    public void test() throws Exception {


        String field = "secID,publishDate,endDate,endDateRep,partyID,ticker,secShortName,exchangeCD,actPubtime," +
                "mergedFlag,reportType,fiscalPeriod,accoutingStandards,currencyCD,tRevenue,revenue,intIncome," +
                "intExp,premEarned,commisIncome,commisExp,TCogs,COGS,premRefund,NCompensPayout,reserInsurContr," +
                "policyDivPayt,reinsurExp,bizTaxSurchg,sellExp,adminExp,finanExp,assetsImpairLoss,fValueChgGain," +
                "investIncome,AJInvestIncome,forexGain,operateProfit,NoperateIncome," +
                "NoperateExp,NCADisploss,TProfit,incomeTax,NIncome,NIncomeAttrP," +
                "minorityGain,basicEPS,dilutedEPS,othComprIncome,TComprIncome,comprIncAttrP,comprIncAttrMS";

        //  String url = "https://api.wmcloud.com/data/v1/api/fundamental/getFdmt.json?ticker=600276&secID=&beginDate=20060101&beginYear=2006&endYear=2017&endDate=20171111&publishDateBegin=20070101&publishDateEnd=20171111&reportType=A&" + field;

        String perfix = "https://api.wmcloud.com/data/v1";
        String stockCode = "600276";
        //  String url2 = "/api/fundamental/getFdmtISAllLatest.json?ticker="+stockCode+"&secID=&beginDate=20150101&endDate=20161231&year=2016&reportType=&field="+field;
        String url2 = "/api/fundamental/getFdmtIS.json?ticker=" + stockCode + "&secID=&beginDate=20150101&endDate=20161231&reportType=&field=" + field;
        url2 = url2 + "&publishDateBegin=" + 20150101;
        url2 = url2 + "&beginDateRep=" + 20150101;
        url2 = url2 + "&beginYear=" + 2015;


        HttpGet httpGet = new HttpGet(perfix + url2);
        //在header里加入 Bearer {token}，添加认证的token，并执行get请求获取json数据
        httpGet.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        httpGet.addHeader("Connection", "keep-alive");

        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);
        //System.out.println(body);
        Result result = JSON.parseObject(body, Result.class);

        log.info("result={}", result);
        for (IncomeStatement stock :
                result.getIncomeStatementList()) {

            incomeStatementRepository.save(stock);
        }


    }

    /**
     * 读取txt1，处理后输出到txt2
     *
     * @throws Exception
     */
    @Test
    public void tes2t() throws Exception {
        StringBuilder builder = new StringBuilder("");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\getFdmtIS.txt")),
                    "UTF-8"));
            String lineTxt = null;
            while ((lineTxt = br.readLine()) != null) {
                List<String> list = Splitter.on("\t").limit(3).splitToList(lineTxt);
                // 实体类
                //builder = getCurrentString(builder, list);
                //建表语句
                //  builder =   getTableString(builder, list);
                //param
                builder = getParamString(builder, list);

            }
            br.close();
        } catch (Exception e) {
            System.err.println("read errors :" + e);
        }
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("D:/结果3.txt")),
                    "UTF-8"));
            bw.write(builder.toString());
            bw.close();
        } catch (Exception e) {
            System.err.println("write errors :" + e);
        }
        log.info("result-----------------------------");
    }

    /**
     * 拼接参数
     *
     * @param s
     * @param list
     * @return
     */
    private StringBuilder getParamString(StringBuilder s, List<String> list) {
        s.append(list.get(0)).append(",");
        return s;
    }

    /**
     * 拼接建表语句
     *
     * @param s
     * @param list
     * @return
     */
    private StringBuilder getTableString(StringBuilder s, List<String> list) {


        s.append(list.get(0)).append(" ").append(getTabelType(list.get(1))).append(" ")
                .append("COMMENT '").append(list.get(2)).append("',\n");
        return s;
    }

    private String getTabelType(String type) {
        if ("String".equals(type)) {
            return "varchar(32) default \"\"";
        } else if ("Double".equals(type)) {
            return "decimal(16,4) default 0";
        } else if ("Int32".equals(type)) {
            return "int default 0";
        } else if ("Date".equals(type)) {
            return "int(8)";
        } else {
            return "varchar(32) default \"\"";
        }
    }

    /**
     * 拼接实体类
     *
     * @param s
     * @param list
     * @return
     */
    private StringBuilder getCurrentString(StringBuilder s, List<String> list) {
        s.append("/**").append(list.get(2)).append("*/").append("@Column(name = \"").append(list.get(0))
                .append("\")").append("private ").append(getType(list.get(1))).append(" ").append(list.get(0)).append(";\n\n");
        return s;
    }

    /**
     * Double -> BigDecimal
     * Int32 -> Integer
     *
     * @param type
     * @return
     */
    private String getType(String type) {
        if ("Double".equals(type)) {
            return "BigDecimal";
        } else if ("Int32".equals(type)) {
            return "Integer";
        } else {
            return type;
        }
    }


    @Test
    @Transactional
//	@Rollback(false)
    public void test2() throws Exception {

        try {
            log.info("11111111111111111111111111111");
            Benefit b = new Benefit();
            b.setSecId("test2");
            Benefit save = benefitRepository.save(b);
            log.info("2222222222222222221。save={}", save);
        } catch (Exception e) {
            log.error("----------e={}", e);
        }

    }

}
