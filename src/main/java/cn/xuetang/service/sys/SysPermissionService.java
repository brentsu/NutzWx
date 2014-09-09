package cn.xuetang.service.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.xuetang.modules.sys.bean.Sys_permission;
import org.apache.commons.lang.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.service.IdEntityService;

import cn.xuetang.common.page.Pagination;

@IocBean(args = { "refer:dao" })
public class SysPermissionService extends IdEntityService<Sys_permission> {

	public SysPermissionService() {
	}

	public SysPermissionService(Dao dao) {
		super(dao);
	}

	
	public List<Sys_permission> list() {
		return query(null, null);
	}

	public Map<Long, String> map() {
		Map<Long, String> map = new HashMap<Long, String>();
		List<Sys_permission> syspermissions = query(null, null);
		for (Sys_permission syspermission : syspermissions) {
			map.put(syspermission.getId(), syspermission.getName());
		}
		return map;
	}

	public void insert(Sys_permission syspermission) {
		dao().insert(syspermission);
	}

	public Sys_permission view(Long id) {
		return fetch(id);
	}

	public void update(Sys_permission syspermission) {
		dao().update(syspermission);
	}

	protected int getPageNumber(Integer pageNumber) {
		return Lang.isEmpty(pageNumber) ? 1 : pageNumber;
	}

	public Pagination getPermissionListByPager(Integer pageNumber) {
		return getPermissionListByPager(pageNumber, null);
	}

	public Pagination getPermissionListByPager(Integer pageNumber, String permissionCategoryId) {
		int pageSize = 20;
		pageNumber = getPageNumber(pageNumber);
		Cnd cnd = Cnd.where("permissionCategoryId", "=", permissionCategoryId);
		Pager pager = dao().createPager(pageNumber, pageSize);
		List<Sys_permission> list = dao().query(Sys_permission.class, StringUtils.isBlank(permissionCategoryId) ? null : cnd, pager);
		pager.setRecordCount(dao().count(Sys_permission.class, StringUtils.isBlank(permissionCategoryId) ? null : cnd));
		return new Pagination(pageNumber, pageSize, pager.getRecordCount(), list);
	}
}
