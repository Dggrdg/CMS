package cms.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cms.service.SendMailService;

@Controller
@RequestMapping("/cms")
public class SendMailController {

	@Autowired
	SendMailService sendMailService;

	@PostMapping("/sendMail")
	public void sendMail() throws IOException {
		sendMailService.sendMail();
	}

}
