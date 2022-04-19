package com.frank.message;

import com.google.common.collect.Lists;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.protocol.IMAPProtocol;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.util.ObjectUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.mail.search.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ShowMail {

    public static String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private MimeMessage mimeMessage;
    /**
     * 附件下载后的存放目录
     */
    private String saveAttachPath = "";
    /**
     * 存放邮件内容的StringBuffer对象
     */
    private StringBuffer bodyText = new StringBuffer();

    /**
     * 构造函数,初始化一个MimeMessage对象
     *
     * @param mimeMessage
     */
    public ShowMail(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }

    /**
     * 获得发件人的地址和姓名
     *
     * @return
     * @throws MessagingException
     */
    public String getFrom() throws MessagingException {
        InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
        String from = address[0].getAddress();
        if (from == null) {
            from = "";
        }
        String personal = address[0].getPersonal();

        if (personal == null) {
            personal = "";
        }

        String fromAddr = null;
        if (personal != null || from != null) {
            fromAddr = personal + "<" + from + ">";
        }
        return fromAddr;
    }

    /**
     * 获得邮件的收件人，抄送，和密送的地址和姓名，根据所传递的参数的不同
     *
     * @param type "to"----收件人　"cc"---抄送人地址　"bcc"---密送人地址
     * @return
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public String getMailAddress(String type) throws MessagingException, UnsupportedEncodingException {
        if (ObjectUtils.isEmpty(type)) {
            return "";
        }

        String addType = type.toUpperCase();

        if (!addType.equals("TO") && !addType.equals("CC") && !addType.equals("BCC")) {
            return "";
        }
        InternetAddress[] address;

        if (addType.equals("TO")) {
            address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.TO);
        } else if (addType.equals("CC")) {
            address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.CC);
        } else {
            address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.BCC);
        }

        if (ObjectUtils.isEmpty(address)) {
            return "";
        }
        StringBuilder mailAddr = new StringBuilder();
        String emailAddr;
        String personal;
        for (int i = 0; i < address.length; i++) {
            emailAddr = address[i].getAddress();
            if (emailAddr == null) {
                emailAddr = "";
            } else {
                emailAddr = MimeUtility.decodeText(emailAddr);
            }
            personal = address[i].getPersonal();
            if (personal == null) {
                personal = "";
            } else {
                personal = MimeUtility.decodeText(personal);
            }
            mailAddr.append(",").append(personal).append("<").append(emailAddr).append(">");
        }
        return mailAddr.toString().substring(1);
    }

    /**
     * 获得邮件主题
     *
     * @return
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public String getSubject() throws MessagingException, UnsupportedEncodingException {
        String subject = MimeUtility.decodeText(mimeMessage.getSubject());
        if (subject == null) {
            subject = "";
        }
        return subject;
    }

    /**
     * 获得邮件发送日期
     *
     * @return
     * @throws MessagingException
     */
    public String getSentDate() throws MessagingException {
        Date sentDate = mimeMessage.getSentDate();
        SimpleDateFormat format = new SimpleDateFormat(NORM_DATETIME_PATTERN);
        return format.format(sentDate);
    }

    /**
     * 获得邮件正文内容
     *
     * @return
     */
    public String getBodyText() {
        return bodyText.toString();
    }

    /**
     * 解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件
     * 主要是根据MimeType类型的不同执行不同的操作，一步一步的解析
     * @param part
     * @throws MessagingException
     * @throws IOException
     */
    public void getMailContent(Part part) throws MessagingException, IOException {

        String contentType = part.getContentType();

        int nameIndex = contentType.indexOf("name");

        boolean conName = false;

        if (nameIndex != -1) {
            conName = true;
        }

        if (part.isMimeType("text/plain") && conName == false) {
            bodyText.append((String) part.getContent());
        } else if (part.isMimeType("text/html") && conName == false) {
            bodyText.append((String) part.getContent());
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int counts = multipart.getCount();
            for (int i = 0; i < counts; i++) {
                this.getMailContent(multipart.getBodyPart(i));
            }
        } else if (part.isMimeType("message/rfc822")) {
            this.getMailContent((Part) part.getContent());
        }
    }

    /**
     * 判断此邮件是否需要回执，如果需要回执返回"true",否则返回"false"
     *
     * @return
     * @throws MessagingException
     */
    public boolean getReplySign() throws MessagingException {

        boolean replySign = false;

        String needReply[] = mimeMessage.getHeader("Disposition-Notification-To");

        if (needReply != null) {
            replySign = true;
        }
        return replySign;
    }

    /**
     * 判断此邮件是否已读，如果未读返回false,反之返回true
     *
     * @return
     * @throws MessagingException
     */
    public boolean isNew() throws MessagingException {
        boolean isNew = false;
        Flags flags = mimeMessage.getFlags();
        Flags.Flag[] flag = flags.getSystemFlags();
        for (int i = 0; i < flag.length; i++) {
            if (flag[i] == Flags.Flag.SEEN) {
                isNew = true;
            }
        }
        return isNew;
    }

    /**
     * 判断此邮件是否包含附件
     *
     * @param part
     * @return
     * @throws MessagingException
     * @throws IOException
     */
    public boolean isContainAttach(Part part) throws MessagingException, IOException {
        boolean attachFlag = false;
        if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            BodyPart mPart;
            String conType;
            for (int i = 0; i < mp.getCount(); i++) {
                mPart = mp.getBodyPart(i);
                String disposition = mPart.getDisposition();
                if (Part.ATTACHMENT.equals(disposition) || Part.INLINE.equals(disposition)) {
                    attachFlag = true;
                } else if (mPart.isMimeType("multipart/*")) {
                    attachFlag = this.isContainAttach(mPart);
                } else {
                    conType = mPart.getContentType();
                    if (conType.toLowerCase().indexOf("application") != -1 || conType.toLowerCase().indexOf("name") != -1){
                        attachFlag = true;
                    }
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            attachFlag = isContainAttach((Part) part.getContent());
        }
        return attachFlag;
    }

    /**
     * 保存附件
     *
     * @param part
     * @throws MessagingException
     * @throws IOException
     */
    public void saveAttachMent(Part part) throws MessagingException, IOException {
        String fileName;
        if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            BodyPart mPart;
            for (int i = 0; i < mp.getCount(); i++) {
                mPart = mp.getBodyPart(i);
                String disposition = mPart.getDisposition();
                if (Part.ATTACHMENT.equals(disposition) || Part.INLINE.equals(disposition)) {
                    fileName = mPart.getFileName();
                    if (null != fileName && fileName.toLowerCase().indexOf("gb2312") != -1) {
                        fileName = MimeUtility.decodeText(fileName);
                    }
                    this.saveFile(fileName, mPart.getInputStream());
                } else if (mPart.isMimeType("multipart/*")) {
                    this.saveAttachMent(mPart);
                } else {
                    fileName = mPart.getFileName();
                    if ((fileName != null) && (fileName.toLowerCase().indexOf("GB2312") != -1)) {
                        fileName = MimeUtility.decodeText(fileName);
                        this.saveFile(fileName, mPart.getInputStream());
                    }
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            this.saveAttachMent((Part) part.getContent());
        }
    }

    /**
     * 设置附件存放路径
     *
     * @param attachPath
     */
    public void setAttachPath(String attachPath) {
        this.saveAttachPath = attachPath;
    }

    /**
     * 获得附件存放路径
     *
     * @return
     */
    public String getAttachPath() {
        return saveAttachPath;
    }

    /**
     * 真正的保存附件到指定目录里
     *
     * @param fileName
     * @param in
     * @throws IOException
     */
    private void saveFile(String fileName, InputStream in) throws IOException {
        String osName = System.getProperty("os.name");
        String storeDir = this.getAttachPath();
        if (null == osName) {
            osName = "";
        }
        if (osName.toLowerCase().indexOf("win") != -1) {
            if (ObjectUtils.isEmpty(storeDir))
                storeDir = "C:\\tmp";
        } else {
            storeDir = "/tmp";
        }
        FileOutputStream fos = new FileOutputStream(new File(storeDir + File.separator + fileName));
        IOUtils.copy(in, fos);
        IOUtils.closeQuietly(fos);
        IOUtils.closeQuietly(in);
    }

    /**
     *  获取阿里云邮箱信息
     *
     * @param host 邮件服务器
     * @param username 邮箱名
     * @param password 密码
     * @param protocol 协议
     * @return
     * @throws MessagingException
     */
    public static Message[] getALiYunMessage(String host, String username, String password, String protocol) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        Store store = session.getStore(protocol);
        store.connect(host, username, password);

        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);
        return folder.getMessages();
    }


    /**
     * 获取163邮箱信息
     *
     * @param host
     * @param username
     * @param password
     * @param protocol
     * @return
     * @throws MessagingException
     */
    public static Message[] getWEMessage(String host, String username, String password, String protocol) throws MessagingException {
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", protocol);
        Session session = Session.getDefaultInstance(props, null);
        Store store = session.getStore(protocol);
        store.connect(host, username, password);
        Folder folder = store.getFolder("INBOX");

        if (folder instanceof IMAPFolder) {
            IMAPFolder imapFolder = (IMAPFolder)folder;
            //javamail中使用id命令有校验checkOpened, 所以要去掉id方法中的checkOpened();
            imapFolder.doCommand(new IMAPFolder.ProtocolCommand() {
                public Object doCommand(IMAPProtocol p) throws com.sun.mail.iap.ProtocolException {
                    p.id("FUTONG");
                    return null;
                }
            });
        }

        if(folder != null) {
            folder.open(Folder.READ_WRITE);
        }

        //final Message[] messages = folder.getMessages();
       // SearchTerm searchTerm1 = new FromStringTerm("noreply@steampowered.com");
        // 三月30的时间戳
        long time = 1647840475000L;
        System.out.println("日期="+new Date(time));
        SearchTerm searchTerm = new AndTerm(new FromStringTerm("noreply@steampowered.com"), new SentDateTerm(ComparisonTerm.GT,new Date(time)));
        //SearchTerm searchTerm = new AndTerm(new FromStringTerm("noreply@steampowered.com"), new ReceivedDateTerm(ComparisonTerm.GE,new Date(time)));

        Message[] messages = folder.search(searchTerm);
        System.out.println("共"+ messages.length +" 封邮件");
        return messages;
    }


    /**
     * 打印邮件
     *
     * @param messageList
     * @throws IOException
     * @throws MessagingException
     */
    public static void printMailMessage(List<Message> messageList) throws IOException, MessagingException {
        System.out.println("邮件数量:" + messageList.size());
        ShowMail re;
        Message message;
        for (int i = 0, leng = messageList.size(); i < leng; i++) {
            message = messageList.get(i);
            re = new ShowMail((MimeMessage) message);
            System.out.println("邮件【" + i + "】主题:" + re.getSubject());
            System.out.println("邮件【" + i + "】发送时间:" + re.getSentDate());
            System.out.println("邮件【" + i + "】是否需要回复:" + re.getReplySign());
            System.out.println("邮件【" + i + "】是否已读:" + re.isNew());
            System.out.println("邮件【" + i + "】是否包含附件:" + re.isContainAttach( message));
            System.out.println("邮件【" + i + "】发送人地址:" + re.getFrom());
            System.out.println("邮件【" + i + "】收信人地址:" + re.getMailAddress("to"));
            System.out.println("邮件【" + i + "】抄送:" + re.getMailAddress("cc"));
            System.out.println("邮件【" + i + "】暗抄:" + re.getMailAddress("bcc"));
            System.out.println("邮件【" + i + "】发送时间:" + re.getSentDate());
            System.out.println("邮件【" + i + "】邮件ID:" + ((MimeMessage) message).getMessageID());
            re.getMailContent(message);
       //     System.out.println("邮件【" + i + "】正文内容:\r\n" + re.getBodyText());
            re.setAttachPath("D:\\Download\\mailFile\\");
            re.saveAttachMent(message);
        }
    }

    public static void main(String[] args) throws MessagingException, IOException, ParseException {
        // 阿里云登录信息
//        String host = "pop3.mxhichina.com";
//        String username = "liwei@xiaostudy.com";
//        String password = "密码";
//        String protocol = "pop3";
//        String fromMail = "XXX@163.com";
//        String startDate = "2020-2-24 23:35:40";
//        List<Message> messageList = filterMessage(getALiYunMessage(host, username, password, protocol), fromMail, startDate);

        // 163登录信息
      /*  String host = "imap.163.com";
        String username = "13240411778@163.com";
        String password = "授权码，不是密码";
        String protocol = "imaps";
        String fromMail = "noreply@github.com";*/


        // QQ 邮箱

        String startDate = "2022-3-14 23:35:40";
        String host = "pop.qq.com";
        String username = "1042680038@qq.com";
        String password = "zgfgklgmhsfnbfbe";
        String protocol = "pop3";
        String fromMail = "noreply@steampowered.com";
        //List<Message> messageList = filterMessage(getWEMessage(host, username, password, protocol), fromMail, startDate);
        List<Message> messageList = Lists.newArrayList(getWEMessage(host, username, password, protocol));

        printMailMessage(messageList);
    }
}