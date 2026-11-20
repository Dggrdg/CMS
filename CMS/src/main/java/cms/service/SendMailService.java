package cms.service;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class SendMailService {

	public void sendMail() throws IOException {

		File file = new File("/Users/humingxuan/Desktop/CmsFilePath/CMS.xlsx");

		if (file.exists()) {
			try (Workbook workbook = WorkbookFactory.create(file)) {
				Sheet sheet = workbook.getSheetAt(0);
				for (Row row : sheet) {
					if (row.getRowNum() == 0) {
						continue;
					}
					Cell ipCell = row.getCell(0);
					Cell mailCell = row.getCell(1);

					sendOutlook(ipCell.getStringCellValue(), mailCell.getStringCellValue());
				}
			}
		}

	}

	public void sendOutlook(String IP, String mail) {
//		Outlook信箱
		String outLookMail = "PMAService@iisigroup.com";
//		Outlook密碼
		String outLookPassword = "Jv4xDY;T,r-@B@84r$YT";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp-mail.outlook.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(outLookMail, outLookPassword);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(outLookMail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));

			message.setSubject("停管處防火牆開通告知測試");
			message.setText("您好，IP ：" + IP + "已開通");

			Transport.send(message);

			System.out.println("信件已寄送給" + mail);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
