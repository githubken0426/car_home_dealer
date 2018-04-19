package com.gtercn.carhome.dealer.cms.service.questionArticle;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gtercn.carhome.dealer.cms.dao.FavorMapper;
import com.gtercn.carhome.dealer.cms.dao.QuestionArticleMapper;
import com.gtercn.carhome.dealer.cms.entity.QuestionArticle;
import com.gtercn.carhome.dealer.cms.entity.write.WriteQuestionArticle;

@Service(value = "questionArticleService")
public class QuestionArticleServiceImpl implements QuestionArticleService {
	@Autowired
	private QuestionArticleMapper dao;
	@Autowired
	private FavorMapper favorDao;

	@Override
	public List<QuestionArticle> queryAllData(Map<String, Object> map) {
		return dao.queryAllData(map);
	}

	@Override
	public QuestionArticle selectByPrimaryKey(String id) throws Exception {
		return dao.selectByPrimaryKey(id);
	}

	

	@Override
	public int getTotalCount(Map<String, Object> map) {
		return dao.getTotalCount(map);
	}

	@Override
	public int insert(WriteQuestionArticle o) throws Exception {
		return dao.insert(o);		
	}

	@Override
	public void deleteData(String ids[]) throws Exception {
		for (String id : ids) {
			favorDao.deleteByCondition(id, "3");
			dao.deleteByPrimaryKey(id);
		}
	}

	@Override
	public int updateByPrimaryKey(WriteQuestionArticle record) {
		if(1==record.getDeleteFlag())
			favorDao.deleteByCondition(record.getId(),"3");
		return dao.updateByPrimaryKey(record);
	}

	@Override
	public List<QuestionArticle> queryAllQuestionData(Map<String, Object> map) {
		return dao.queryAllQuestionData(map);
	}

	@Override
	public QuestionArticle selectQuestionByPrimaryKey(String id) {
		return dao.selectQuestionByPrimaryKey(id);
	}

	@Override
	public int updateQuestionById(WriteQuestionArticle record) {
		return dao.updateQuestionById(record);
	}

}
