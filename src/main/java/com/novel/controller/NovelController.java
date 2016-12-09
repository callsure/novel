package com.novel.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.novel.beans.Chapter;
import com.novel.beans.ChapterDetail;
import com.novel.entitys.Nclass;
import com.novel.entitys.Tnovel;
import com.novel.service.NovelService;
import com.novel.service.TnovelService;
import com.novel.service.impl.EhcacheDB;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;


/**
 * Created by runshu.lin on 16/11/29.
 */
@Controller
public class NovelController {

	private static final Logger logger = LoggerFactory.getLogger(NovelController.class);

	private final Integer pageSize = 10;

	@Resource(name = "ehcacheDB")
	private EhcacheDB ehcacheDB;

	@Resource
	private NovelService novelService;

	@Resource
	private TnovelService tnovelService;

	/**
	 * 网站首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model){
		//默认查找看书中的热门书籍
		List<Nclass> nclasses = this.getsNclassAll();
		List<Tnovel> hotNovels = tnovelService.getHotNovelAllType(1018,nclasses);
		model.addAttribute("nclasses", nclasses);
		model.addAttribute("hotNovels", hotNovels);
		model.addAttribute("classes",nclasses);
		return "index";
	}

//	@RequestMapping(value = "/main", method = RequestMethod.GET)
//	public String top(Model model){
		//默认查找看书中的热门书籍
//		List<Nclass> nclasses = this.getsNclassAll();
//		List<Tnovel> hotNovels = tnovelService.getHotNovelAllType(1018,nclasses);
//		model.addAttribute("nclasses", nclasses);
//		model.addAttribute("hotNovels", hotNovels);
//		return "main";
//	}

	/**
	 * 获取类别小说列表
	 * @param id 类别id
	 * @param pageNum 页数
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/classify/{id}-{pageNum}")
	public String getsHotNovelByType(@PathVariable String id,@PathVariable Integer pageNum, Model model){
		pageNum = pageNum == null?1:pageNum;
		PageHelper.startPage(pageNum,pageSize);
		List<Tnovel> list = ehcacheDB.getsNovelByType(id, pageNum);
		PageInfo<Tnovel> page = new PageInfo<>(list);

		String typeNme  = "";
		for (Tnovel tnovel : list) {
			typeNme = typeNme.isEmpty()? tnovel.getnType() : typeNme;
			tnovel.setnDesc(StringUtils.abbreviate(tnovel.getnDesc(),35));
		}

		String path = "classify/" + id + "-";

		model.addAttribute("typeNme", typeNme);

		model.addAttribute("path", path);

		model.addAttribute("novels", list);
		//当前页数
		model.addAttribute("currPage",pageNum);
		//总数的数量
		model.addAttribute("total",page.getTotal());
		//第一页
		model.addAttribute("firstPage",page.getFirstPage());
		//最后一页
		model.addAttribute("lastPage",page.getLastPage());
		//总的页数
		model.addAttribute("totalPage",page.getPages());
		//是否有前一页
		model.addAttribute("hasFirstPage",(pageNum!=1)?true:false);
		//是否有后一页
		model.addAttribute("hasNextPage",(page.getLastPage()!=pageNum)?true:false);

		return "classify";
	}

	/**
	 * 小说章节列表
	 * @param id 小说id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/chapter/{id}")
	public String showChapterList(@PathVariable Integer id, Model model){
		Tnovel tnovel = tnovelService.getNovelById(id);
		List<Chapter> chapters = null;
		String res = "";
		try {
			chapters = ehcacheDB.getsChapterById(tnovel);
		} catch (Exception e) {
			logger.warn(e.toString());
			res = "哎哎哎,数据抓取出错了!";
		}

		//增加浏览数
		Tnovel tnovel_1 = new Tnovel();
		tnovel_1.setnId(id);
		tnovel_1.setnBrowse(tnovel.getnBrowse()+1);
		tnovelService.updateBrowse(tnovel_1);

		model.addAttribute("success", res);
		model.addAttribute("novel",tnovel);
		model.addAttribute("chapters", chapters);
		return "chapter";
	}

	/**
	 * 小说内容
	 * @param id
	 * @param chapterId
	 * @param url
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/chapterDetail/{id}/{chapterId}")
	public String shoeChapterDetail(@PathVariable Integer id, @PathVariable Integer chapterId, @RequestParam("url") String url, Model model){
		Tnovel tnovel = tnovelService.getNovelById(id);
		ChapterDetail chapterDetail = new ChapterDetail();
		try {
			chapterDetail = ehcacheDB.getChapterDetailByUrl(id, chapterId, url);
			chapterDetail.setCountIndex(chapterId);
		} catch (Exception e) {
			logger.warn(e.toString());
			chapterDetail.setContent("哎哎哎,数据抓取出错了!");
		}
		model.addAttribute("chapterDetail", chapterDetail);
		model.addAttribute("novel", tnovel);
		return "chapterDetail";
	}

	/**
	 * 搜索小说
	 * @param query 搜索词
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search")
	public String searchNovel(@RequestParam("q") String query, @RequestParam("p") Integer pageNum, Model model){
		try {
			query = URLDecoder.decode(query, "utf-8");

			pageNum = pageNum == null?1:pageNum;
			PageHelper.startPage(pageNum,pageSize);

			List<Tnovel> tnovels = tnovelService.searchNovel(query);
			PageInfo<Tnovel> page = new PageInfo<>(tnovels);

			for (Tnovel tnovel : tnovels) {
				tnovel.setnDesc(StringUtils.abbreviate(tnovel.getnDesc(),35));
			}

			List<Nclass> nclasses = this.getsNclassAll();

			//类别
			model.addAttribute("nclasses", nclasses);
			//查询词
			model.addAttribute("query", query);
			//搜索结果
			model.addAttribute("novels", tnovels);
			//当前页数
			model.addAttribute("currPage",pageNum);
			//总数的数量
			model.addAttribute("total",page.getTotal());
			//第一页
			model.addAttribute("firstPage",page.getFirstPage());
			//最后一页
			model.addAttribute("lastPage",page.getLastPage());
			//总的页数
			model.addAttribute("totalPage",page.getPages());
			//是否有前一页
			model.addAttribute("hasFirstPage",(pageNum!=1)?true:false);
			//是否有后一页
			model.addAttribute("hasNextPage",(page.getLastPage()!=pageNum)?true:false);

		} catch (UnsupportedEncodingException e) {
			logger.warn("query转码错误!");
		}
		return "search";
	}

	/**
	 * 获取全部的小说类别
	 * @return
	 */
	protected List<Nclass> getsNclassAll(){
		List<Nclass> nclasses = ehcacheDB.getsNclassAll();
		return nclasses;
	}
}
