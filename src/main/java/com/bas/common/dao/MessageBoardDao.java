package com.bas.common.dao;

import java.util.List;

import com.bas.common.dao.entity.MessageBoardEntity;

/**
 * 
 * @author nnnnnnnnnn
 *
 */
public interface MessageBoardDao {

	public int findTotalMessages();
	public MessageBoardEntity findMessageById(int mid);
	public MessageBoardEntity findLatestMessage();
	public String saveMessageBoard(MessageBoardEntity messageBoardEntity);
	public List<MessageBoardEntity> findAllMessageBoard();
	public byte[] findImageByMessageId(int mid);

}
