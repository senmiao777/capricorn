import io.sentry.Sentry;
import io.sentry.SentryClient;
import io.sentry.SentryClientFactory;
import io.sentry.context.Context;
import io.sentry.event.BreadcrumbBuilder;
import io.sentry.event.UserBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * @author frank
 * @version 1.0
 * @date 2018/2/4 0021 下午 4:18
 */

@Slf4j
public class ClassTest {

    static {
        log.info("看到这句话说明类加载了，并且static代码块执行了");
    }

    @Test
    public void testSentry() {

        /*
         It is recommended that you use the DSN detection system, which
         will check the environment variable "SENTRY_DSN", the Java
         System Property "sentry.dsn", or the "sentry.properties" file
         in your classpath. This makes it easier to provide and adjust
         your DSN without needing to change your code. See the configuration
         page for more information.
         */
        Sentry.init();

        // You can also manually provide the DSN to the ``init`` method.
        String dsn = "https://sentry.io/whatever-7k";
        Sentry.init(dsn);

        /*
         It is possible to go around the static ``Sentry`` API, which means
         you are responsible for making the SentryClient instance available
         to your code.
         */
        SentryClient sentry = SentryClientFactory.sentryClient();


        logWithStaticAPI();
        logWithInstanceAPI(sentry);

    }

    /**
     * An example method that throws an exception.
     */
    void unsafeMethod() {
        throw new UnsupportedOperationException("You shouldn't call this!");
    }

    /**
     * Examples using the (recommended) static API.
     */
    private void logWithStaticAPI() {
        // Note that all fields set on the context are optional. Context data is copied onto
        // all future events in the current context (until the context is cleared).

        // Record a breadcrumb in the current context. By default the last 100 breadcrumbs are kept.
        Sentry.getContext().recordBreadcrumb(
                new BreadcrumbBuilder().setMessage("User made an action").build()
        );

        // Set the user in the current context.
        Sentry.getContext().setUser(
                new UserBuilder().setEmail("13240411778@163.com").build()
        );

        // Add extra data to future events in this context.
        Sentry.getContext().addExtra("extra", "thing");

        // Add an additional tag to future events in this context.
        Sentry.getContext().addTag("tagName", "tagValue");

        /*
         This sends a simple event to Sentry using the statically stored instance
         that was created in the ``main`` method.
         */
        Sentry.capture("This is a test");

        try {
            unsafeMethod();
        } catch (Exception e) {
            // This sends an exception event to Sentry using the statically stored instance
            // that was created in the ``main`` method.
            Sentry.capture(e);
        }
    }

    /**
     * Examples that use the SentryClient instance directly.
     */
    private void logWithInstanceAPI(SentryClient sentry) {
        // Retrieve the current context.
        Context context = sentry.getContext();

        // Record a breadcrumb in the current context. By default the last 100 breadcrumbs are kept.
        context.recordBreadcrumb(new BreadcrumbBuilder().setMessage("User made an action").build());

        // Set the user in the current context.
        context.setUser(new UserBuilder().setEmail("hello@sentry.io").build());

        // This sends a simple event to Sentry.
        sentry.sendMessage("This is a test");

        try {
            unsafeMethod();
        } catch (Exception e) {
            // This sends an exception event to Sentry.
            sentry.sendException(e);
        }
    }

    @Test
    public void testClazzCode() {
        int a = 100;
        int b = 110;
        int c = 120;
        int d = (a + b) * c;
        log.info("a={},b={},c={},d={}", a, b, c, d);

        for (int i = 0; i < 10; i++) {
            testReturn(i);
        }
    }

    private void testReturn(int i) {
        if (i == 3) {
            return;
        } else {
            log.info("testReturn i = {}", i);
        }
    }


    @Test
    public void code() {
        String rawString = "漱壕   4 1\n" +
                " \u0004 \u001D\t \u0007 \u001E\b \u001F\u0007  \n" +
                " ! \"\u000B # $\u0007 %\n" +
                " & '\u0001 \u0003log\u0001 \u0012Lorg/slf4j/Logger;\u0001 \u0006<init>\u0001 \u0003()V\u0001 \u0004Code\u0001 \u000FLineNumberTable\u0001 \u0012LocalVariableTable\u0001 \u0004this\u0001 \u000BLClassTest;\u0001 \n" +
                "testClazzCode\u0001 \u0001a\u0001 \u0001I\u0001 \u0001b\u0001 \u0001c\u0001 \u0001d\u0001 \u0019RuntimeVisibleAnnotations\u0001 \u0010Lorg/junit/Test;\u0001 \b<clinit>\u0001 \n" +
                "SourceFile\u0001 \u000EClassTest.java\f \u000B \f\f \t \n" +
                "\u0001 \u0013a={},b={},c={},d={}\u0001 \u0010java/lang/Object\u0007 (\f ) *\u0007 +\f , -\u0001 \tClassTest\u0007 .\f / 0\u0001 \u0011java/lang/Integer\u0001 \u0007valueOf\u0001 \u0016(I)Ljava/lang/Integer;\u0001 \u0010org/slf4j/Logger\u0001 \u0004info\u0001 ((Ljava/lang/String;[Ljava/lang/Object;)V\u0001 \u0017org/slf4j/LoggerFactory\u0001 \tgetLogger\u0001 %(Ljava/lang/Class;)Lorg/slf4j/Logger; ! \u0007 \u0004   \u0001 \u001A \t \n" +
                "   \u0003 \u0001 \u000B \f \u0001 \n" +
                "   / \u0001 \u0001   \u0005*?\u0001?  \u0002 \u000E   \u0006 \u0001   \u000B \u000F   \f \u0001   \u0005 \u0010 \u0011   \u0001 \u0012 \f \u0002 \n" +
                "   ?\u0006 \u0005   <\u0010d<\u0010n=\u0010x>\u001B\u001C`\u001Dh6\u0004?\u0002\u0012\u0003\u0007?\u0004Y\u0003\u001B?\u0005SY\u0004\u001C?\u0005SY\u0005\u001D?\u0005SY\u0006\u0015\u0004?\u0005S?\u0006\u0003 ?  \u0002 \u000E   \u001A \u0006   \u000E \u0003 \u000F \u0006 \u0010 \t \u0011 \u0010 \u0012 ; \u0013 \u000F   4 \u0005   < \u0010 \u0011   \u0003 9 \u0013 \u0014 \u0001 \u0006 6 \u0015 \u0014 \u0002 \t 3 \u0016 \u0014 \u0003 \u0010 , \u0017 \u0014 \u0004 \u0018   \u0006 \u0001 \u0019   \b \u001A \f \u0001 \n" +
                "   ! \u0001     \t\u0012\u0007?\b?\u0002?  \u0001 \u000E   \u0006 \u0001   \n" +
                " \u0001 \u001B   \u0002 \u001C";


        try {

            final byte[] gb2312s = rawString.getBytes("GB2312");
//            String s = new String(rawString.getBytes("GB2312"), "ISO-8859-1");
//            log.info("s={}", s);

            String[] encodelist = {"ISO-8859-1", "GB2312", "UTF-8", "GBK", "Big5", "UTF-16LE", "Shift_JIS", "EUC-JP"};

            for (int i = 0; i < encodelist.length; i++) {
                try {
                    String s = new String(gb2312s, encodelist[i]);
                    log.info("encode={},s={}", encodelist[i], s);
                } catch (Exception e) {
                } finally {
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEncode() {
        String str = "漱壕";
        final String encodeType = getEncodeType(str);
        log.info("encodeType={}", encodeType);
    }

    private String getEncodeType(String str) {
        String[] encodelist = {"ISO-8859-1", "GB2312", "UTF-8", "GBK", "Big5", "UTF-16LE", "Shift_JIS", "EUC-JP"};
        for (int i = 0; i < encodelist.length; i++) {
            try {
                if (str.equals(new String(str.getBytes(encodelist[i]), encodelist[i]))) {
                    return encodelist[i];
                }
            } catch (Exception e) {
            } finally {
            }
        }
        return "UNKNOWN";
    }

}
