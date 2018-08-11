/*package com.bas.soap.email.endpoint;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.bas.soap.service.email.EMailSenderServiceImpl;
import com.email.soap.provider.EMailSender;
import com.email.soap.provider.SoapEmailMessage;


@Component("SoapEmailProvider")
@WebService(name = "EMailSender", endpointInterface = "com.email.soap.provider.EMailSender", portName = "EMailSenderPort",serviceName="EMailSenderService",targetNamespace="http://www.soap.email.com/provider/",wsdlLocation="/bas-email.wsdl")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class SoapEmailProvider implements EMailSender {
	
	@Autowired
	@Qualifier("EMailSenderServiceImpl")
	private EMailSenderServiceImpl eMailSenderServiceImpl;

	@Override
	public String sendEMail(SoapEmailMessage psendEMail) {
		eMailSenderServiceImpl.sendMail(psendEMail.getSender(),psendEMail.getReceiver().get(0), psendEMail.getSubject(),psendEMail.getMessage());
		System.out.println("Hola = "+psendEMail.getSender());
		return "doneisdone!";
	}

	
}
*/