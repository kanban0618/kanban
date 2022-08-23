package com.kanban.controller;

import com.kanban.bean.Msg;
import com.kanban.config.VerifyCode;
import com.kanban.entity.User;
import com.kanban.service.LoginService;
import com.kanban.service.UserService;
import com.sun.mail.util.MailSSLSocketFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;


@Controller
@RestController
@RequestMapping("/login")
@Api("登录控制器")
public class LoginController {
    @Autowired
    LoginService loginService;
    @Autowired
    UserService userService;

    public static String yxyzm ;

    @PostMapping("/doLogin")
    @ApiOperation("检查登录")
    public Msg doLogin(HttpServletRequest req, @RequestBody User user){
        Msg msg = loginService.doGet(user);
        HttpSession session = req.getSession();
        session.setAttribute("LOGIN_USER",msg.getData());
        return msg;
    }


    @GetMapping("/logout")
    @ApiOperation("注销登录")
    public Msg logout(HttpServletRequest req){
        Msg msg = new Msg();
        HttpSession session = req.getSession();
        session.removeAttribute("LOGIN_USER");
        System.out.println(session+"用户已经退出");
        return msg;
    }

    @PostMapping("/register")
    @ApiOperation("注册用户")
    public Msg register(@RequestBody User user){
        return userService.insert(user);
    }

    @GetMapping("/emilMail/{receiver}")
    @ApiOperation("邮箱发送随机验证码")
    public void sendMail(HttpServletRequest request,@PathVariable String receiver) throws Exception {
        Properties prop = new Properties();
        // 开启debug调试，以便在控制台查看
        prop.setProperty("mail.debug", "true");
        // 设置邮件服务器主机名
        prop.setProperty("mail.host", "smtp.qq.com");
        // 发送服务器需要身份验证
        prop.setProperty("mail.smtp.auth", "true");
        // 发送邮件协议名称
        prop.setProperty("mail.transport.protocol", "smtp");
        // 开启SSL加密，否则会失败
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);
        // 创建session
        Session session = Session.getInstance(prop);
        // 通过session得到transport对象
        Transport ts = session.getTransport();
        // 连接邮件服务器：邮箱类型，帐号，POP3/SMTP协议授权码 163使用：smtp.163.com
        ts.connect("smtp.163.com", "kan_ban@163.com", "RNJZJVUGAXGHAJNE");//授权码需要自己弄
        // 创建邮件
        Message message = createSimpleMail(request,session,receiver);
        // 发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
//        System.out.println("code:"+request.getSession().getAttribute("code"));

    }

    /**
     * @Method: createSimpleMail
     * @Description: 创建一封只包含文本的邮件
     */
    public static MimeMessage createSimpleMail(HttpServletRequest request, Session session, @PathVariable String receiver) throws Exception {
        //  获取6为随机验证码
        String[] letters = new String[] {
                "q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m",
                "A","W","E","R","T","Y","U","I","O","P","A","S","D","F","G","H","J","K","L","Z","X","C","V","B","N","M",
                "0","1","2","3","4","5","6","7","8","9"};
        String stringBuilder ="";
        for (int i = 0; i < 6; i++) {
            stringBuilder = stringBuilder + letters[(int)Math.floor(Math.random()*letters.length)];
        }
        yxyzm = stringBuilder;
        System.out.println("-------------------"+yxyzm+"-------------------");
        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute("code",stringBuilder);
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 指明邮件的发件人（主邮箱）
        message.setFrom(new InternetAddress("kan_ban@163.com"));
        // 指明邮件的收件人，发件人和收件人如果是一样的，那就是自己给自己发
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
        // 邮件的标题
        message.setSubject("【验证主题】");
        // 邮件的文本内容
        message.setContent("欢迎您使用【看板系统】，验证码为(一分钟有效)："+stringBuilder+"，请勿回复此邮箱", "text/html;charset=UTF-8");
        // 返回创建好的邮件对象
        return message;
    }

    @GetMapping("/emil/{receiver}")
    @ApiOperation("邮箱发送")
    public void send(HttpServletRequest request,@PathVariable String receiver) throws Exception {
        Properties prop = new Properties();
        // 开启debug调试，以便在控制台查看
        prop.setProperty("mail.debug", "true");
        // 设置邮件服务器主机名
        prop.setProperty("mail.host", "smtp.qq.com");
        // 发送服务器需要身份验证
        prop.setProperty("mail.smtp.auth", "true");
        // 发送邮件协议名称
        prop.setProperty("mail.transport.protocol", "smtp");
        // 开启SSL加密，否则会失败
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);
        // 创建session
        Session session = Session.getInstance(prop);
        // 通过session得到transport对象
        Transport ts = session.getTransport();
        // 连接邮件服务器：邮箱类型，帐号，POP3/SMTP协议授权码 163使用：smtp.163.com
        ts.connect("smtp.163.com", "kan_ban@163.com", "RNJZJVUGAXGHAJNE");//授权码需要自己弄
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 指明邮件的发件人（主邮箱）
        message.setFrom(new InternetAddress("kan_ban@163.com"));
        System.out.println(receiver);
        // 指明邮件的收件人，发件人和收件人如果是一样的，那就是自己给自己发
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(receiver));
        System.out.println(receiver);
        // 邮件的标题
        message.setSubject("【验证主题】");
        // 邮件的文本内容
        message.setContent("欢迎您使用【看板系统】，请勿回复此邮箱"+"，验证码为(一分钟有效):", "text/html;charset=UTF-8");
        // 发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }

    /* 获取验证码图片*/
    @GetMapping("/getVerifyCode")
    @ApiOperation("获取验证码图片")
    public void getVerificationCode(HttpServletResponse response, HttpServletRequest request) {
        try {
            int width=200;
            int height=69;
            BufferedImage verifyImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            //生成对应宽高的初始图片
            String randomText = VerifyCode.drawRandomText(width,height,verifyImg);
            //单独的一个类方法，出于代码复用考虑，进行了封装。
            //功能是生成验证码字符并加上噪点，干扰线，返回值为验证码字符
            request.getSession().setAttribute("verifyCode", randomText);
            response.setContentType("image/png");//必须设置响应内容类型为图片，否则前台不识别
            OutputStream os = response.getOutputStream(); //获取文件输出流
            ImageIO.write(verifyImg,"png",os);//输出图片流
            os.flush();
            os.close();//关闭流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* 获取验证码图片*/
    @GetMapping("/getVerIf/{yzm}")
    @ApiOperation("获取验证码结果")
    public Msg getVerIf(@PathVariable String yzm) {
        Msg msg = new Msg();
        System.out.println(VerifyCode.getYzm());
        if (yzm.equals(VerifyCode.getYzm())){
            msg.setData(true);
            msg.setMessage("验证码输入正确");
            msg.setResult(true);
        }else {
            msg.setData(false);
            msg.setMessage("验证码输入错误");
            msg.setResult(false);
        }
        return msg;
    }

    /* 获取邮箱验证码*/
    @GetMapping("/emilyxyzm/{yzm}")
    @ApiOperation("获取邮箱验证码结果")
    public Msg emilyxyzm(@PathVariable String yzm) {
        Msg msg = new Msg();
        if (yxyzm.equals(yzm)){
            msg.setData(true);
            msg.setMessage("验证码输入正确");
            msg.setResult(true);
        }else {
            msg.setData(false);
            msg.setMessage("验证码输入错误");
            msg.setResult(false);
        }
        return msg;
    }

    @GetMapping("/findusername/{username}")
    @ApiOperation("判断用户是否存在")
    public Msg findusername(@PathVariable String username){
        return loginService.findusername(username);
    }
}
