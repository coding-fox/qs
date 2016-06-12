package org.brjia.qs.mail;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class Test {

	public static void main(String[] args) throws MalformedURLException, EmailException {
		

		  // create the email message
//		  ImageHtmlEmail email = new ImageHtmlEmail();
		HtmlEmail email = new HtmlEmail();
		  email.setDebug(true);
		  email.setSmtpPort(465);
		  email.setAuthenticator(new DefaultAuthenticator("yemanhuli@yeah.net", "hjw0223WYYX"));
		  email.setSSLOnConnect(true);
		  email.setHostName("smtp.yeah.net");
		  email.addTo("419422534@qq.com","野渡");
		  email.setFrom("yemanhuli@yeah.net", "别人家倩女");
		  email.setSubject("Test email with inline image");
		  
//		// load your HTML email template
//		  String htmlEmailTemplate = ".... <img src=\"http://www.apache.org/images/feather.gif\"> ....";
//		  // define you base URL to resolve relative resource locations
//		  URL url = new URL("http://www.apache.org");
//		  email.setDataSourceResolver(new DataSourceUrlResolver(url));
//		  // set the html message
//		  email.setHtmlMsg(htmlEmailTemplate);
//		  // set the alternative message
//		  email.setTextMsg("Your email client does not support HTML messages");
//		  // send the email
//		  email.send();
//		  
		// embed the image and get the content id
		  URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
		  String cid = email.embed(url, "Apache logo");
		  // set the html message
		  email.setHtmlMsg("<html>The apache logo - <img src=\"cid:"+cid+"\"></html>");
		  // set the alternative message
		  email.setTextMsg("Your email client does not support HTML messages");
		  // send the email
		  email.send();
	}
}
