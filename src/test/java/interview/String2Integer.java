package interview;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * Created by QuantGroup on 2018/4/16.
 */
@Slf4j
public class String2Integer {

    @Test
    public void test(){

    }

    private Integer string2Integer(String str){
        if(StringUtils.isBlank(str)){
            return Integer.valueOf(0);
        }
        return 0;

    }
}
