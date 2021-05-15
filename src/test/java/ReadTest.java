import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class ReadTest {

    @Test
    public void testRead() {
        final int END = -1;
        final int ZERO = 0;
        int BUFFER_SIZE = 16;
        int num = 10;
        String path = "A:/test.txt";
        try {
            FileInputStream inputStream = new FileInputStream(path);
            FileChannel inChannel = inputStream.getChannel();

            ByteBuffer bytebuf = ByteBuffer.allocate(BUFFER_SIZE);
            CharsetDecoder decoder = Charset.defaultCharset().newDecoder();
            if (num < BUFFER_SIZE) {
                inChannel.read(bytebuf);
                bytebuf.flip();
                //对bytebuf进行解码，避免乱码
                CharBuffer decode = decoder.decode(bytebuf);
                //  log.info("decode.toString()={}",decode.toString());
                System.out.println(decode.toString().substring(ZERO, num));
                //清空缓冲区，再次放入数据
                bytebuf.clear();
            } else {
                while ((inChannel.read(bytebuf)) != END && num > ZERO) {
                    bytebuf.flip();
                    CharBuffer decode = decoder.decode(bytebuf);
                    bytebuf.clear();
                    // 最后一次读取，缓冲区字符超过了需要的个数
                    if (num < BUFFER_SIZE) {
                        System.out.println(decode.toString().substring(ZERO, num));
                    } else {
                        System.out.println(decode.toString());
                    }
                    num -= BUFFER_SIZE;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("文件未找到");
        } catch (IOException ie) {
            System.out.println("文件读取异常,error=" + ie.getStackTrace());
        }
    }



}
