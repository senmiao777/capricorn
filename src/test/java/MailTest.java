import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/24 0024 下午 9:08
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@ComponentScan(basePackages = "com.frank")
@SpringBootTest(classes = MailTest.class)
@Rollback(false)
@Slf4j
public class MailTest {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 发送带附件的邮件
     * 使用Thymeleaf模板
     */
    @Test
    public void testSendMultiMailWithThymeleaf() {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;


        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("13240411778@163.com");
            mimeMessageHelper.setTo("1042680038@qq.com");
            mimeMessageHelper.setSubject("这是标题");
            mimeMessageHelper.setText("this is text 这是内容");
            FileSystemResource fileSystemResource = new FileSystemResource("C:\\Users\\Administrator\\Desktop\\阿里巴巴Java开发手册.pdf");
            mimeMessageHelper.addAttachment("abd.pdf", fileSystemResource);
            Context context = new Context();
            context.setVariable("message", "模板中的内容");
            String emailText = templateEngine.process("mailTemplate.html", context);
            /**
             * 第二个参数是，是否是html格式
             */
            mimeMessageHelper.setText(emailText, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送带附件的邮件
     */
    @Test
    public void testSendMultiMail() {
        log.info("111");
        /**
         * MimeMessage 本身的API比较笨重，MimeMessageHelper的API比较好使
         */
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("13240411778@163.com");
            mimeMessageHelper.setTo("1042680038@qq.com");
            mimeMessageHelper.setSubject("这是标题");
            mimeMessageHelper.setText("this is text 这是内容");
            FileSystemResource fileSystemResource = new FileSystemResource("C:\\Users\\Administrator\\Desktop\\阿里巴巴Java开发手册.pdf");
            mimeMessageHelper.addAttachment("abd.pdf", fileSystemResource);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }


        log.info("222");
    }

    @Test
    public void testSendMail() {
        log.info("111");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("13240411778@163.com");
        simpleMailMessage.setTo("1042680038@qq.com");
        // simpleMailMessage.setTo("13240411778@163.com");
        simpleMailMessage.setSubject("setSubject");
        simpleMailMessage.setText("this is text");
        mailSender.send(simpleMailMessage);
        log.info("222");
    }

}
