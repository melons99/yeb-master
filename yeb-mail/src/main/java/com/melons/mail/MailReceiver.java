package com.melons.mail;


import com.melons.server.pojo.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Component
public class MailReceiver {

    private static final  Logger LOGGER = LoggerFactory.getLogger(MailReceiver.class);

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailProperties mailProperties;
    @Autowired
    private TemplateEngine templateEngine;

    @RabbitListener(queues = "mail.welcome")
    public void handler(Employee employee){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            //发件人
            helper.setFrom(mailProperties.getUsername());
            //收件人
            helper.setTo(employee.getEmail());
//            helper.setTo("714684734@qq.com");
            //主题
            helper.setSubject("入职欢迎邮件");
            //发送日期
            helper.setSentDate(new Date());
            //邮件内容
            Context context = new Context();
            context.setVariable("name",employee.getName());
            context.setVariable("posName",employee.getPosition().getName());
            context.setVariable("departmentName",employee.getDepartment().getName());
            context.setVariable("joblevelname",employee.getJoblevel().getName());

            String mail = templateEngine.process("mail", context);
            helper.setText(mail,true);
            //发送邮件
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            LOGGER.error("邮件发送失败=======>{}",e.getMessage());
        }

    }
}
