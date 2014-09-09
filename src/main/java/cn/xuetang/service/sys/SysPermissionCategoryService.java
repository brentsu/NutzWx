package cn.xuetang.service.sys;

import java.util.ArrayList;
import java.util.List;

import cn.xuetang.modules.sys.bean.Sys_permissionCategory;
import org.apache.commons.lang.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.service.IdEntityService;

import cn.xuetang.common.page.Pagination;

@IocBean(args = { "refer:dao" })
public class SysPermissionCategoryService extends IdEntityService<Sys_permissionCategory> {

	public SysPermissionCategoryService() {
	}

	public SysPermissionCategoryService(Dao dao) {
		super(dao);
	}

	public List<Sys_permissionCategory> list() {
		List<Sys_permissionCategory> list = dao().query(getEntityClass(),Cnd.orderBy().asc("listIndex"),null);
		for (Sys_permissionCategory syspermissionCategory : list) {
			initPermissionCategory(syspermissionCategory);
		}
		return list;
	}

	private void initPermissionCategory(Sys_permissionCategory syspermissionCategory) {
		dao().fetchLinks(syspermissionCategory, null);
		boolean haveParent = StringUtils.isNotBlank(syspermissionCategory.getParentId());
		if (haveParent) {
			syspermissionCategory.setParent(dao().fetch(getEntityClass(), Cnd.where("id", "=", syspermissionCategory.getParentId())));
			boolean haveChilen = StringUtils.isNotBlank(syspermissionCategory.getTreePath());
			if (haveChilen) {
				final List<String> chilenIds = Json.fromJsonAsList(String.class, syspermissionCategory.getTreePath());
				syspermissionCategory.setChildren(new ArrayList<Sys_permissionCategory>() {
					private static final long serialVersionUID = -7813279993889152029L;
					{
						for (String id : chilenIds) {
							Sys_permissionCategory chilen = dao().fetch(getEntityClass(), Cnd.where("id", "=", id));
							add(chilen);
							initPermissionCategory(chilen);
						}
					}
				});
			}
			initPermissionCategory(syspermissionCategory.getParent());
		}

	}

	public void insert(Sys_permissionCategory permission) {
		dao().insert(permission);
	}

	public Sys_permissionCategory fetchByID(String id) {
		return dao().fetch(getEntityClass(), Cnd.where("id", "=", id));
	}

	public Sys_permissionCategory view(String id) {
		return fetchByID(id);
	}

	public void update(Sys_permissionCategory permission) {
		dao().update(permission);
	}

	public void remove(String id) {
		dao().execute(Sqls.createf("delete from permission_category where id = %s", id));
	}

	protected int getPageNumber(Integer pageNumber) {
		return Lang.isEmpty(pageNumber) ? 1 : pageNumber;
	}

	public Pagination getPermissionCategoryListByPager(Integer pageNumber) {
		int pageSize = 20;
		pageNumber = getPageNumber(pageNumber);
		Pager pager = dao().createPager(pageNumber, pageSize);
		List<Sys_permissionCategory> list = dao().query(Sys_permissionCategory.class, null, pager);
		pager.setRecordCount(dao().count(Sys_permissionCategory.class, null));
		return new Pagination(pageNumber, pageSize, pager.getRecordCount(), list);
	}
}
