/**
 * 
 */
package com.bas.common.service;

import java.util.List;

import com.bas.common.web.controller.form.MessageBoardVO;



/**
 * @author Tanveer
 *
 */
public interface MessageBoardService {
	public int findTotalMessages();
	public MessageBoardVO findMessageById(int mid);
	public List<MessageBoardVO> findAllMessageBoard();
	public MessageBoardVO findLatestMessage();
	public String uploadMessageBoard(MessageBoardVO messageBoardVO);
	public byte[] findImageByMessageId(int mid);

}
