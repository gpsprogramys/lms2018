package com.bas.common.dao.impl;

import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

import com.bas.common.dao.MessageBoardDao;
import com.bas.common.dao.entity.MessageBoardEntity;


@Repository("MessageBoardDaoImpl")
public class MessageBoardDaoImpl extends JdbcDaoSupport implements MessageBoardDao{

	@Autowired
	@Qualifier("sdatasource")
	public void setDataSourceInSuper(DataSource dataSource) {
		super.setDataSource(dataSource);
	}
	
	@Override
	public byte[] findImageByMessageId(int mid){
		String query = "select * from message_board_tbl where mid=" + mid;
		MessageBoardEntity messageBoardEntity = (MessageBoardEntity) super.getJdbcTemplate()
				.queryForObject(query,
						new BeanPropertyRowMapper(MessageBoardEntity.class));
		return messageBoardEntity.getImage();
	}
	@Override
    public String saveMessageBoard(MessageBoardEntity messageBoardEntity){
        String query = "insert into message_board_tbl(subject,message,category,fuser,image,domessage) values (?,?,?,?,?,?)";
        LobHandler lobHandler = new DefaultLobHandler();
        SqlLobValue sqlLobValue=new SqlLobValue(messageBoardEntity.getImage(),lobHandler);
        Object[] obj =new Object[]{messageBoardEntity.getSubject(),messageBoardEntity.getMessage(),messageBoardEntity.getCategory(),messageBoardEntity.getFrom(),sqlLobValue,messageBoardEntity.getDomessage()};
        int[] dataType=new int[] {Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR,Types.VARCHAR,Types.BLOB, Types.TIMESTAMP};
        super.getJdbcTemplate().update(query,obj,dataType);
        
        return "Message sent successfully !";
        
    }

	@Override
	public int findTotalMessages() {
		int num = getJdbcTemplate().queryForInt("select count(*) from message_board_tbl");
		return num;
	}
	

	@Override
	public MessageBoardEntity findLatestMessage() {
		String query = "select * from message_board_tbl order by domessage desc LIMIT 1";
		MessageBoardEntity messageBoardEntity = (MessageBoardEntity) super.getJdbcTemplate()
				.queryForObject(query,new BeanPropertyRowMapper(MessageBoardEntity.class));
		
		messageBoardEntity.setTotalMessage(findTotalMessages());
		return messageBoardEntity;
	}

	@Override
	public MessageBoardEntity findMessageById(int mid) {
		String query = "select * from message_board_tbl where mid=" + mid;
		MessageBoardEntity messageBoardEntity = (MessageBoardEntity) super.getJdbcTemplate()
				.queryForObject(query,
						new BeanPropertyRowMapper(MessageBoardEntity.class));
		
		messageBoardEntity.setTotalMessage(findTotalMessages());
		return messageBoardEntity;
	}

	@Override
	public List<MessageBoardEntity> findAllMessageBoard() {
		String sql = "select * from message_board_tbl";
		List<MessageBoardEntity>messageBoardEntity = super.getJdbcTemplate().query(sql,new BeanPropertyRowMapper(MessageBoardEntity.class));
		return messageBoardEntity;
	}
}
