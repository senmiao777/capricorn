package com.frank.controller;

import com.frank.entity.mysql.IncomeStatement;
import com.frank.entity.mysql.Stock;
import com.frank.model.JsonResult;
import com.frank.repository.mysql.IncomeStatementRepository;
import com.frank.repository.mysql.StockRepository;
import com.google.common.util.concurrent.RateLimiter;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 基本增删改查操作
 * 缺少权限验证
 * 以及各种校验
 */
@Slf4j
@RestController
@RequestMapping(value = "/t/stock")
public class StockController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IncomeStatementRepository incomeStatementRepository;

    @Autowired
    private StockRepository stockRepository;

    /**
     * 每秒发十个令牌
     */
    RateLimiter rateLimiter = RateLimiter.create(10);

    @ApiOperation(value = "跳转到Echart页面")
    @RequestMapping("/hello")
    public String helloHtml() {
        //  map.put("hello","hello");
        return "/firstEchart.html";
    }


    @ApiOperation(value = "根据股票代码查询该股票合并利润表信息", notes = "这是展示在详情里的信息。根据传入的股票代码查询该股票的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "stockCode", value = "股票代码，如600672", required = true, dataType = "String")
    })
    @RequestMapping(value = "/incomeStatement", method = RequestMethod.GET)
    public JsonResult incomeStatement(@RequestParam String stockCode) {
        List<IncomeStatement> incomeStatementList = incomeStatementRepository.findByStockCode(stockCode);

        return JsonResult.buildSuccessResult(incomeStatementList);

    }

    /**
     * 根据股票代码查询该股票基本信息
     * rateLimiter限制访问速度
     *
     * @param stockCode
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public JsonResult info(@RequestParam String stockCode) {

        log.info("stockCode={},等待时间={}", stockCode,rateLimiter.acquire());
        Stock stock = stockRepository.findByStockCode(stockCode);
        return JsonResult.buildSuccessResult(stock);
    }

    /**
     * 根据股票代码查询该股票基本信息
     * rateLimiter限制访问速度
     *
     * @param stockCode
     * @return
     */
    @RequestMapping(value = "/info2", method = RequestMethod.GET)
    public JsonResult info2(@RequestParam String stockCode) {

        log.info("stockCode={},等待时间={}", stockCode,rateLimiter.acquire());
        if(!rateLimiter.tryAcquire(200L, TimeUnit.MICROSECONDS)){
            log.info("去别的页面转转吧/抛异常，统一处理都行");
            return  JsonResult.buildErrorResult("去别的页面转转吧");
        }
        Stock stock = stockRepository.findByStockCode(stockCode);
        return JsonResult.buildSuccessResult(stock);
    }

    /**
     * 根据股票代码查询该股票基本信息
     *
     * @param stockCode
     * @return
     */
    @RequestMapping(value = "/base", method = RequestMethod.GET)
    public JsonResult base(@RequestParam String stockCode) {
        log.info("-----------------stockCode={}----------------{}", stockCode);
        Stock stock3 = stockRepository.findOne(1L);
        log.info("stock={}", stock3);
        Stock stock = stockRepository.findByStockCode(stockCode);
        log.info("stock={}", stock);
        Stock stock2 = stockRepository.findByStockCode(stockCode);
        log.info("stock={}", stock2);
        Stock stock6 = stockRepository.findByStockCode(stockCode);
        log.info("stock={}", stock6);


        Stock stock4 = stockRepository.findOne(1L);
        log.info("stock={}", stock4);
        Stock stock7 = stockRepository.findOne(1L);
        log.info("stock={}", stock7);
        if (stock == null) {
            log.info("base stock is null.input stock code ={}", stockCode);
            return JsonResult.buildSuccessResult("根据该股票代码未查到数据，请确认后输入", stock);
        }

        return JsonResult.buildSuccessResult(stock);

    }

    private void doSomething(String code) {
        stockRepository.findByStockCode(code);
    }


    /**
     * 根据id删除用户
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/l/base", method = RequestMethod.GET)
    public JsonResult baseList(@RequestParam String area) {
        List<Stock> stockList = stockRepository.findByArea(area);
        if (CollectionUtils.isEmpty(stockList)) {
            log.info("base stockList is null.input area ={}", area);
            return JsonResult.buildSuccessResult("根据该地区未查到数据，请确认后输入", area);
        }

        return JsonResult.buildSuccessResult(stockList);

    }


    @RequestMapping(value = "/insertStock", method = RequestMethod.GET)
    public JsonResult insertStock(@RequestParam String code) {
        Stock s = new Stock();
        s.setCode(code);
        Stock save = stockRepository.save(s);


        return JsonResult.buildSuccessResult(save);

    }


    /**
     * 服务器段作为rest客户端， 向其他服务器发起请求
     * @param age
     * @return

     @RequestMapping(value = "/client/{age}", method = RequestMethod.GET)
     public ResponseEntity<User> UserR(
     @PathVariable int age){
     //UriComponentsBuilder uriBuilder) {
     String url = "http://127.0.0.1:8080/user/User/s/"+age;
     Map<String,String> paramMap = Maps.newHashMap();
     paramMap.put("name", "testName");
     ResponseEntity<User> responseEntity = restTemplate.getForEntity(url, User.class , paramMap);
     HttpStatus statusCode = responseEntity.getStatusCode();
     // 如果和第三方服务器约定了返回响应码，需要做验证
     if(!HttpStatus.OK.equals(responseEntity.getStatusCode())){
     log.error("预期返回值异常，返回值:{}",responseEntity.getStatusCode());
     }

     User User = responseEntity.getBody();
     HttpHeaders header = new HttpHeaders();

     log.info("age:{},User:{}", age, User);
     //
     // header.setLocation(locationUri);

     //header.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON_UTF8));


     //在请求头设置返回的资源类型是 xml ,而服务器返回的数据类型是json

     header.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON_UTF8));
     header.setContentType(MediaType.APPLICATION_JSON_UTF8);

     return new ResponseEntity(User, header, HttpStatus.ACCEPTED);
     }*/
}
