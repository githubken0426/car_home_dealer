package com.gtercn.carhome.dealer.cms.dao;

import java.util.List;
import java.util.Map;

import com.gtercn.carhome.dealer.cms.entity.QuestionArticle;
import com.gtercn.carhome.dealer.cms.entity.write.WriteQuestionArticle;


public interface QuestionArticleMapper {
	/**
	 * 查询所有文章数据
	 * @param map
	 * @return
	 * 2017-2-21 下午02:48:14
	 */
	List<QuestionArticle> queryAllData(Map<String, Object> map);
	/**
	 * 查询所有问题数据
	 * @param map
	 * @return
	 * 2017-3-2 下午01:26:13
	 */
	List<QuestionArticle> queryAllQuestionData(Map<String, Object> map);
	/**
	 * 查询所有数据条数
	 * @param map
	 * @return
	 * 2017-2-21 下午02:48:14
	 */
	public int getTotalCount(Map<String, Object> map);
	
    int deleteByPrimaryKey(String id);
    /**
     * 通过用户id删除文章
     * @param userId
     * @return
     * 2017-3-23 下午03:44:31
     */
    int deleteByUserId(String userId);

    int insert(WriteQuestionArticle record);

    QuestionArticle selectByPrimaryKey(String id);
    /**
     * 通过id查询问题
     * @param id
     * @return
     * 2017-3-3 下午01:29:37
     */
    QuestionArticle selectQuestionByPrimaryKey(String id);
    /**
     * 修改文章
     * @param record
     * @return
     * 2017-3-1 上午11:14:59
     */
    int updateByPrimaryKey(WriteQuestionArticle record);
    
    /**
     * 修改问题
     * @param record
     * @return
     * 2017-3-6 上午11:34:12
     */
    int updateQuestionById(WriteQuestionArticle record);
}