package cn.xuetang.service.sys;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.xuetang.common.page.Pagination;
import cn.xuetang.modules.sys.bean.Article;
import cn.xuetang.service.BaseService;

@IocBean(fields = { "dao" })
public class ArticleService extends BaseService<Article> {
	public ArticleService() {
		super();
	}

	public ArticleService(Dao dao) {
		super(dao);
	}

	public void delete(String[] ids) {
		dao().clear(getEntityClass(), Cnd.where("id", "in", ids));
	}

	public List<Article> list() {
		return query(null, null);
	}

	public List<Article> getIndexNewList() {
		return dao().query(getEntityClass(), Cnd.NEW().limit(10).desc("id"));
	}

	public Article fetchByID(String id) {
		Article art = fetch(Cnd.where("id", "=", id));
		dao().fetchLinks(art, "articleCategory");
		return art;
	}

	public Pagination getArticleListByPager(Integer pageNumber, int pageSize, String articleCategoryId) {
		Pager pager = dao().createPager(pageNumber, pageSize);
		Cnd cnd = Cnd.where("articleCategoryId", "=", articleCategoryId);
		List<Article> list = dao().query(Article.class, StringUtils.isBlank(articleCategoryId) ? null : cnd, pager);
		for (Article atricle : list) {
			atricle = dao().fetchLinks(atricle, "articleCategory");
		}
		pager.setRecordCount(dao().count(Article.class, StringUtils.isBlank(articleCategoryId) ? null : cnd));
		return new Pagination(pageNumber, pageSize, pager.getRecordCount(), list);
	}

	public Pagination getObjListByPager(Integer pageNumber, String keyWorld) {
		Pager pager = dao().createPager(pageNumber, 20);
		Cnd cnd = Cnd.where("title", "like", "%" + keyWorld + "%");
		List<Article> list = dao().query(getEntityClass(), cnd, pager);
		pager.setRecordCount(dao().count(getEntityClass(), cnd));
		Pagination pagination = new Pagination(pageNumber, 20, pager.getRecordCount(), list);
		return pagination;
	}
}
