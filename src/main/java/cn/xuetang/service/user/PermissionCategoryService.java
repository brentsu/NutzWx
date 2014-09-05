package cn.xuetang.service.user;

import java.util.ArrayList;
import java.util.List;

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
import cn.xuetang.modules.user.bean.PermissionCategory;

@IocBean(args = { "refer:dao" })
public class PermissionCategoryService extends IdEntityService<PermissionCategory> {

	public PermissionCategoryService() {
	}

	public PermissionCategoryService(Dao dao) {
		super(dao);
	}

	public List<PermissionCategory> list() {
		List<PermissionCategory> list = dao().fetch(query(Cnd.orderBy().asc("listIndex"), null));
		for (PermissionCategory permissionCategory : list) {
			initPermissionCategory(permissionCategory);
		}
		return list;
	}

	private void initPermissionCategory(PermissionCategory permissionCategory) {
		dao().fetchLinks(permissionCategory, null);
		boolean haveParent = StringUtils.isNotBlank(permissionCategory.getParentId());
		if (haveParent) {
			permissionCategory.setParent(dao().fetch(getEntityClass(), Cnd.where("id", "=", permissionCategory.getParentId())));
			boolean haveChilen = StringUtils.isNotBlank(permissionCategory.getTreePath());
			if (haveChilen) {
				final List<String> chilenIds = Json.fromJsonAsList(String.class, permissionCategory.getTreePath());
				permissionCategory.setChildren(new ArrayList<PermissionCategory>() {
					private static final long serialVersionUID = -7813279993889152029L;
					{
						for (String id : chilenIds) {
							PermissionCategory chilen = dao().fetch(getEntityClass(), Cnd.where("id", "=", id));
							add(chilen);
							initPermissionCategory(chilen);
						}
					}
				});
			}
			initPermissionCategory(permissionCategory.getParent());
		}

	}

	public void insert(PermissionCategory permission) {
		dao().insert(permission);
	}

	public PermissionCategory fetchByID(String id) {
		return dao().fetch(getEntityClass(), Cnd.where("id", "=", id));
	}

	public PermissionCategory view(String id) {
		return fetchByID(id);
	}

	public void update(PermissionCategory permission) {
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
		List<PermissionCategory> list = dao().query(PermissionCategory.class, null, pager);
		pager.setRecordCount(dao().count(PermissionCategory.class, null));
		return new Pagination(pageNumber, pageSize, pager.getRecordCount(), list);
	}
}
