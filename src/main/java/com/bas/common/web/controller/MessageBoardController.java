package com.bas.common.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.bas.common.constant.NavigationConstant;
import com.bas.common.constant.PageTitleContant;
import com.bas.common.service.MessageBoardService;
import com.bas.common.util.CurrentUserDataUtil;
import com.bas.common.util.DateUtils;
import com.bas.common.web.controller.form.MessageBoardVO;

/**
 * 
 * @author tanveer
 * 
 */

@Controller
@Scope("request")
public class MessageBoardController {
	
	@Autowired
	@Qualifier("MessageBoardServiceImpl")
	private MessageBoardService messageBoardService;
	
	
	
	@RequestMapping(value = "/uploadMessageBoard", method = RequestMethod.POST)
    public String uploadMessagePost(@ModelAttribute("messageBoardVO") MessageBoardVO messageBoardVO, Model model,HttpSession session) {
		messageBoardVO.setDomessage(DateUtils.getCurrentTimestatmp());
		messageBoardVO.setFrom(CurrentUserDataUtil.getCurrentLoggedUser(session));
        messageBoardService.uploadMessageBoard(messageBoardVO);
        model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.MESSAGE_BOARD_TITLE);
        model.addAttribute("messageBoardVO", messageBoardVO);
        model.addAttribute("ApplicationMessage","Your Message have been post successfully.");
        return messageBoardInbox(model);
        
    }
	
	@RequestMapping(value = "/uploadMessageBoard", method = RequestMethod.GET)
	public String showUploadMessageBoard(Model model) {
		MessageBoardVO messageBoardVO = new MessageBoardVO();
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.MESSAGE_BOARD_TITLE);
		model.addAttribute("messageBoardVO", messageBoardVO);
		return NavigationConstant.ADMIN_PREFIX_PAGE
                + NavigationConstant.POST_MESSAGE_BOARD_PAGE;
	}
	
	@RequestMapping(value = "/messageBoardInbox", method = RequestMethod.GET)
	public String messageBoardInbox(Model model) {
		model.addAttribute(PageTitleContant.PAGE_TITLE, PageTitleContant.MESSAGE_BOARD_TITLE);
		 List<MessageBoardVO> messageBoardList=messageBoardService.findAllMessageBoard();
			model.addAttribute("messageBoardList", messageBoardList);
		return NavigationConstant.ADMIN_PREFIX_PAGE
				+ NavigationConstant.MESSAGE_BOARD_INBOX_PAGE;
	}

	@RequestMapping(value = "/messageBoard", method = RequestMethod.GET)
	public String showMessageBoard(Model model) {
		MessageBoardVO messageBoardVO = messageBoardService.findLatestMessage();
		model.addAttribute("messageBoardVO", messageBoardVO);
		return NavigationConstant.EMPLOYEE_PREFIX_PAGE
				+ NavigationConstant.MESSAGE_BOARD;
	}
	
	@RequestMapping(value = "/nextMessageBoard", method = RequestMethod.GET)
	public String nextMessageBoard(Model model,@RequestParam("mid") int mid) {
		MessageBoardVO messageBoardVO = messageBoardService.findMessageById(mid);
		model.addAttribute("messageBoardVO", messageBoardVO);
		return NavigationConstant.EMPLOYEE_PREFIX_PAGE
				+ NavigationConstant.MESSAGE_BOARD;
	}
	
	@RequestMapping(value = "/previousMessageBoard", method = RequestMethod.GET)
	public String previousMessageBoard(Model model,@RequestParam("mid") int mid) {
		MessageBoardVO messageBoardVO = messageBoardService.findMessageById(mid);
		
		model.addAttribute("messageBoardVO", messageBoardVO);
		
		return NavigationConstant.EMPLOYEE_PREFIX_PAGE
				+ NavigationConstant.MESSAGE_BOARD;
	}
	
	

	@InitBinder
    public void initBinder(WebDataBinder binder) {
             // to actually be able to convert Multipart instance to byte[]
             // we have to register a custom editor
             binder.registerCustomEditor(byte[].class,
                                new ByteArrayMultipartFileEditor());
             // now Spring knows how to handle multipart object and convert them
    }
	
	@RequestMapping(value = "/findImageByMessageId", method = RequestMethod.GET)
	public void findMobilePhotoById(@RequestParam("mid") int mid,
			HttpServletResponse response) throws IOException {
		response.setContentType("image/jpg");
		byte[] photo = messageBoardService.findImageByMessageId(mid);
		if (photo != null) {
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(photo);
			outputStream.flush();
			outputStream.close();
		}
	}
	
	@RequestMapping(value = "/ ByMsgId", method = RequestMethod.GET)
	public String viewMsgByMsgId(@RequestParam("mid") int mid, Model model){
		MessageBoardVO messageBoardVO = messageBoardService.findMessageById(mid);
		
		model.addAttribute("messageBoardVO", messageBoardVO);
		return NavigationConstant.ADMIN_PREFIX_PAGE + NavigationConstant.VIEW_MESSAGE_BOARD;
	}


}
