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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by runshu.lin on 16/11/29.
 */
@Controller
public class NovelController {

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
		List<Nclass> nclasses = ehcacheDB.getsNclassAll();
		model.addAttribute("classes",nclasses);
		return "index";
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String top(Model model){
		//默认查找看书中的热门书籍
		List<Nclass> nclasses = ehcacheDB.getsNclassAll();
		List<Tnovel> hotNovels = tnovelService.getHotNovelAllType(1018,nclasses);
		model.addAttribute("nclasses", nclasses);
		model.addAttribute("hotNovels", hotNovels);
		return "main";
	}

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
		List<Chapter> chapters = ehcacheDB.getsChapterById(tnovel);
		//增加浏览数
		Tnovel tnovel_1 = new Tnovel();
		tnovel_1.setnId(id);
		tnovel_1.setnBrowse(tnovel.getnBrowse()+1);
		tnovelService.updateBrowse(tnovel_1);

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
			e.printStackTrace();
			chapterDetail.setContent("哎哎哎,数据抓取出错了!");
		}
		model.addAttribute("chapterDetail", chapterDetail);
		model.addAttribute("novel", tnovel);
		return "chapterDetail";
	}
}
