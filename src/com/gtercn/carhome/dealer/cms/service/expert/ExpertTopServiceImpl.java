package com.gtercn.carhome.dealer.cms.service.expert;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gtercn.carhome.dealer.cms.dao.APIUserMapper;
import com.gtercn.carhome.dealer.cms.dao.ExpertTopMapper;
import com.gtercn.carhome.dealer.cms.dao.QuestionArticleMapper;
import com.gtercn.carhome.dealer.cms.entity.APIUser;
import com.gtercn.carhome.dealer.cms.entity.ExpertTop;
import com.gtercn.carhome.dealer.cms.entity.write.WriteExpertTop;

@Service(value = "expertTopService")
public class ExpertTopServiceImpl implements ExpertTopService {
	@Autowired
	private ExpertTopMapper dao;
	@Autowired
	private APIUserMapper userDao;
	@Autowired
	private QuestionArticleMapper articleDao;

	@Override
	public List<ExpertTop> queryAllData(Map<String, Object> map) {
		return dao.queryAllData(map);
	}

	@Override
	public ExpertTop getDataById(String id) throws Exception {
		return dao.selectByPrimaryKey(id);
	}

	@Override
	public int updateData(WriteExpertTop o) throws Exception {
		if("1".equals(o.getDeleteFlag())){
			ExpertTop top =dao.selectByPrimaryKey(o.getId());
			articleDao.deleteByUserId(top.getUserId());
		}
		return dao.updateByPrimaryKey(o);
	}

	@Override
	public int getTotalCount(Map<String, Object> map) {
		return dao.getTotalCount(map);
	}

	@Override
	public void deleteData(String []ids) throws Exception {
		for (String id : ids) {
			ExpertTop top =dao.selectByPrimaryKey(id);
			articleDao.deleteByUserId(top.getUserId());
			dao.deleteByPrimaryKey(id);
		}
	}

	@Override
	public int getExcludeExpert(Map<String, Object> map) {
		return dao.getExcludeExpert(map);
	}

	@Override
	public List<ExpertTop> queryAllExpert(String cityCode) {
		return dao.queryAllExpert(cityCode);
	}

	@Override
	public int registerUserAndExpert(WriteExpertTop o, APIUser user,
			boolean isAdd){
		if(isAdd){
			userDao.insert(user);
		}
		return dao.insert(o);
	}
}
