package cn.xuetang.service.user;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.xuetang.modules.user.bean.User_address;
import cn.xuetang.service.BaseService;

@IocBean(fields = { "dao" })
public class UserAddressService extends BaseService<User_address> {

	
	public UserAddressService() {
	}
	
	public UserAddressService(Dao dao) {
		super(dao);
	}

	public boolean deleteByIds(String[] ids) {
		return dao().clear(getEntityClass(), Cnd.where("id", "in", ids)) > 0;
	}

	public String listPageJson(int curPage, int pageSize) {
		Criteria cri = Cnd.cri();
		cri.getOrderBy().desc("id");
		return super.listPageJson(curPage, pageSize, cri);
	}

}
