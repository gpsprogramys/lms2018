/**
 * 
 */
package com.bas.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bas.common.dao.MessageBoardDao;
import com.bas.common.dao.entity.MessageBoardEntity;
import com.bas.common.service.MessageBoardService;
import com.bas.common.web.controller.form.MessageBoardVO;

/**
 * @author Tanveer
 *
 */
@Service("MessageBoardServiceImpl")
@Transactional
public class MessageBoardServiceImpl implements MessageBoardService {
	
	@Autowired
	@Qualifier("MessageBoardDaoImpl")
	private MessageBoardDao messageBoardDao;
	
	@Override
	public byte[] findImageByMessageId(int mid) {
		return messageBoardDao.findImageByMessageId(mid);
	}
	
	@Override
    public String uploadMessageBoard(MessageBoardVO messageBoardVO){
        MessageBoardEntity messageBoardEntity = new MessageBoardEntity();
        BeanUtils.copyProperties(messageBoardVO, messageBoardEntity);
        String str = messageBoardDao.saveMessageBoard(messageBoardEntity);
        return str;
    }


	@Override
	public int findTotalMessages() {
		return messageBoardDao.findTotalMessages();
	}

	@Override
	public MessageBoardVO findMessageById(int mid) {
		MessageBoardEntity messageBoardEntity = messageBoardDao.findMessageById(mid);
		MessageBoardVO messageBoardVO = new MessageBoardVO();
		BeanUtils.copyProperties(messageBoardEntity, messageBoardVO);
		return messageBoardVO;
	}

	@Override
	public MessageBoardVO findLatestMessage() {
		MessageBoardEntity messageBoardEntity = messageBoardDao.findLatestMessage();
		MessageBoardVO messageBoardVO = new MessageBoardVO();
		BeanUtils.copyProperties(messageBoardEntity, messageBoardVO);
		return messageBoardVO;
	}


	@Override
	public List<MessageBoardVO> findAllMessageBoard() {
		List<MessageBoardEntity> messageBoardList=messageBoardDao.findAllMessageBoard();
		List<MessageBoardVO> messageBoardVOsList=new ArrayList<MessageBoardVO>();
		for(MessageBoardEntity mbe:messageBoardList){
			MessageBoardVO messageBoardVO = new MessageBoardVO();
			BeanUtils.copyProperties(mbe, messageBoardVO);
			messageBoardVOsList.add(messageBoardVO);
		}
		return messageBoardVOsList;
	}

}
