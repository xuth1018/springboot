package com.company.project.web.mail;

import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.core.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Api("邮件")
@Path("/mail")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Component
public class MailController {

    @Resource
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String username;

    @ApiOperation("发送简单邮件")
    @Path("/sendSimpleMail")
    @POST
    public Result sendSimpleMail(){
        try {
            //需开启pop/smtp协议
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom(username);
            mail.setTo("2676457872@qq.com");
            mail.setSentDate(new Date());
            mail.setSubject("感恩");
            mail.setText("今天天气很好");
            javaMailSender.send(mail);
        }catch (Exception e){
            throw new ServiceException("邮件发送失败");
        }
        return ResultGenerator.genSuccessResult();
    }
}
