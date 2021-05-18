package com.shichko.deliveryservice.model.service;

import com.shichko.deliveryservice.model.entity.Order;
import com.shichko.deliveryservice.model.entity.Ordered;
import com.shichko.deliveryservice.util.RsaCipherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String email;

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(Order order) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        StringBuilder message = new StringBuilder("Order N").append(order.getId());
        message.append("<table border=\"1\"><tr><td>Name</td><td>Amount</td><td>Price</td></tr>");

        double total = 0;

        for (Ordered ordered: order.getOrdered())
        {
            total += ordered.getProduct().getPrice() * ordered.getAmount();
            message
                    .append("<tr><td>")
                    .append(ordered.getProduct().getName())
                    .append("</td><td>")
                    .append(ordered.getAmount())
                    .append("</td><td>")
                    .append(String.format("%.2f", ordered.getProduct().getPrice()))
                    .append("</td></tr>");
        }
        message
                .append("</table><br><b>")
                .append("Total: ")
                .append(total)
                .append("</b><br><a href='http://localhost:8080/#/orderState?id=")
                .append(RsaCipherUtil.encrypt(order.getId()))
                .append("'>Order state</a>");

        helper.setText(message.toString(), true);
        helper.setTo(order.getCustomer().getEmail());
        helper.setSubject("Your order is received");
        helper.setFrom(email);
        emailSender.send(mimeMessage);

    }

}
