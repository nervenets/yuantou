package com.nervenets.general.service.impl;

import com.nervenets.general.service.MailService;
import com.nervenets.general.utils.JodaUtils;
import com.nervenets.general.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@Service
public class MailServiceImpl implements MailService {
    @Resource
    private JavaMailSender mailSender;
    @Autowired
    private Environment environment;

    @Async
    public void exceptionEmailSend(String fromName, String title, String content, Exception ex) throws IOException {
        File errorFile = new File(JodaUtils.getTimestamp() + "-err.log");
        if (!errorFile.exists()) errorFile.createNewFile();
        PrintWriter printWriter = new PrintWriter(errorFile);
        ex.printStackTrace(printWriter);
        printWriter.flush();
        printWriter.close();

        StringBuilder params = new StringBuilder();
        params.append(JodaUtils.timeLongToString(JodaUtils.getTimestamp() * 1000L, "MM-dd HH:mm:ss")).append("<br>");
        params.append(content);

        StackTraceElement[] stackTrace = ex.getStackTrace();
        for (StackTraceElement element : stackTrace) {
            params.append(element.toString()).append("<br>");
        }
        if (null != ex.getCause()) {
            for (StackTraceElement element : ex.getCause().getStackTrace()) {
                params.append(element.toString()).append("<br>");
            }
        }

        sendExceptions(fromName, title, params.toString(), errorFile.getAbsolutePath());
        errorFile.delete();
    }

    @Async
    public void sendExceptions(String fromName, String title, String exceptions, String filePath) {
        String[] tos = {};
        String value = environment.getProperty("app.notify.email");

        if (!StringUtils.isBlank(value)) {
            tos = value.split(",");
            sendEmail(fromName, tos, String.format("%s系统异常：%s", title, JodaUtils.timeLongToString()), exceptions, filePath);
        }
    }

    @Async
    public void sendEmail(String fromName, String[] tos, String title, String content, String filePath) {
        MimeMessage mimeMessage;
        try {
            mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(new InternetAddress(fromName + " <" + environment.getProperty("spring.mail.username") + ">"));
            helper.setTo(tos);
            helper.setSubject(title);
            helper.setText(content, true);
            if (null != filePath) {
                //注意项目路径问题，自动补用项目路径
                FileSystemResource file = new FileSystemResource(new File(filePath));
                //加入邮件
                helper.addAttachment(filePath, file);
            }

            mailSender.send(mimeMessage);
            System.out.println(Arrays.toString(tos) + "邮件发送成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
