package com.novel.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.novel.beans.Chapter;
import com.novel.beans.ChapterDetail;
import com.novel.beans.JsonBean;
import com.novel.entitys.Nclass;
import com.novel.entitys.Tnovel;
import com.novel.service.DownLoadService;
import com.novel.service.TnovelService;
import com.novel.service.impl.EhcacheDB;
import com.novel.utils.RandomStringUtil;
import com.novel.utils.SiteUtil;
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
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.List;


/**
 * Created by runshu.lin on 16/11/29.
 */
@Controller
public class NovelController {

	private static final Logger logger = LoggerFactory.getLogger(NovelController.class);

	private static final Integer pageSize = 10;

	@Resource(name = "ehcacheDB")
	private EhcacheDB ehcacheDB;

	@Resource
	private TnovelService tnovelService;

	@Resource
	DownLoadService downLoadService;

	/**
	 * 网站首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model){
		//默认查找看书中的热门书籍
		List<Nclass> nclasses = this.getsNclassAll();
		List<Tnovel> hotNovels = tnovelService.getHotNovelAllType(null,nclasses);

		model.addAttribute("site", SiteUtil.getSite());
		model.addAttribute("email", SiteUtil.getEmail());
		model.addAttribute("nclasses", nclasses);
		model.addAttribute("hotNovels", hotNovels);
		model.addAttribute("nclasses",nclasses);
		return "index";
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
		Integer pagenumber = pageNum == null?1:pageNum;
		PageHelper.startPage(pagenumber,pageSize);
		List<Tnovel> list = ehcacheDB.getsNovelByType(id, pagenumber);
		PageInfo<Tnovel> page = new PageInfo<>(list);

		String typeNme  = "";
		for (Tnovel tnovel : list) {
			typeNme = typeNme.isEmpty()? tnovel.getnType() : typeNme;
			tnovel.setnDesc(StringUtils.abbreviate(tnovel.getnDesc(),35));
		}

		String path = "classify/" + id + "-";

		List<Nclass> nclasses = this.getsNclassAll();

		//小说类别导航
		model.addAttribute("nclasses", nclasses);
		//类别名
		model.addAttribute("typeNme", typeNme);
		//路径
		model.addAttribute("path", path);
		//小说列表数据
		model.addAttribute("novels", list);
		//当前页数
		model.addAttribute("currPage",pagenumber);
		//总数的数量
		model.addAttribute("total",page.getTotal());
		//第一页
		model.addAttribute("firstPage",page.getFirstPage());
		//最后一页
		model.addAttribute("lastPage",page.getLastPage());
		//总的页数
		model.addAttribute("totalPage",page.getPages());
		//是否有前一页
		model.addAttribute("hasFirstPage",(pagenumber!=1)?true:false);
		//是否有后一页
		model.addAttribute("hasNextPage",(page.getLastPage()!=pagenumber)?true:false);

		model.addAttribute("site", SiteUtil.getSite());
		model.addAttribute("email", SiteUtil.getEmail());

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
		chapters = ehcacheDB.getsChapterById(tnovel);
		if (chapters == null || chapters.isEmpty()){
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

		model.addAttribute("site", SiteUtil.getSite());
		model.addAttribute("email", SiteUtil.getEmail());
		return "chapter";
	}

	/**
	 * 最后阅读记录地址跳转
	 * @param id
	 * @param url
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/chapterDetail/{id}/last")
	public void getLastReadChapterDetail(@PathVariable Integer id, @RequestParam("url") String url, HttpServletResponse response) throws IOException {
		Tnovel tnovel = tnovelService.getNovelById(id);
		List<Chapter> chapters = null;
		chapters = ehcacheDB.getsChapterById(tnovel);
		try {
			Integer chapterId = null;
			if (chapters != null && !chapters.isEmpty()) {
				for (int i = 0, size = chapters.size(); i < size; i++) {
					if (url.equals(chapters.get(i).getUrl())) {
						chapterId = size - i;
						break;
					}
				}
				response.sendRedirect(chapterId + "?url=" + url);
			}
		} catch (IOException e) {
			logger.error(e.toString());
			response.setCharacterEncoding("utf-8");
			response.getWriter().println("数据抓取出错");
		}
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

		ChapterDetail chapterDetail = ehcacheDB.getChapterDetailByUrl(id, chapterId, url);

		if (chapterDetail == null){
			chapterDetail = new ChapterDetail();
			chapterDetail.setContent("哎哎哎,数据抓取出错了!");
		}

		chapterDetail.setCountIndex(chapterId);

		model.addAttribute("chapterDetail", chapterDetail);
		model.addAttribute("novel", tnovel);

		model.addAttribute("site", SiteUtil.getSite());
		model.addAttribute("email", SiteUtil.getEmail());
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
			String querys = URLDecoder.decode(query, "utf-8");

			Integer pagenumber = pageNum == null?1:pageNum;
			PageHelper.startPage(pagenumber,pageSize);

			List<Tnovel> tnovels = tnovelService.searchNovel(querys);
			PageInfo<Tnovel> page = new PageInfo<>(tnovels);

			for (Tnovel tnovel : tnovels) {
				tnovel.setnDesc(StringUtils.abbreviate(tnovel.getnDesc(),35));
			}

			List<Nclass> nclasses = this.getsNclassAll();

			//类别
			model.addAttribute("nclasses", nclasses);
			//查询词
			model.addAttribute("query", querys);
			//搜索结果
			model.addAttribute("novels", tnovels);
			//当前页数
			model.addAttribute("currPage",pagenumber);
			//总数的数量
			model.addAttribute("total",page.getTotal());
			//第一页
			model.addAttribute("firstPage",page.getFirstPage());
			//最后一页
			model.addAttribute("lastPage",page.getLastPage());
			//总的页数
			model.addAttribute("totalPage",page.getPages());
			//是否有前一页
			model.addAttribute("hasFirstPage",(pagenumber!=1)?true:false);
			//是否有后一页
			model.addAttribute("hasNextPage",(page.getLastPage()!=pagenumber)?true:false);

			model.addAttribute("site", SiteUtil.getSite());
			model.addAttribute("email", SiteUtil.getEmail());

		} catch (UnsupportedEncodingException e) {
			logger.warn("query转码错误!");
		}
		return "search";
	}

	/**
	 * 书架
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/bookshelf")
	public String showBookshelf(Model model){
		List<Nclass> nclasses = this.getsNclassAll();
		//类别
		model.addAttribute("nclasses", nclasses);
		model.addAttribute("site", SiteUtil.getSite());
		model.addAttribute("email", SiteUtil.getEmail());
		return "bookshelf";
	}

	/**
	 * 小说下载申请(只有申请下载系统才去抓取并且打包小说)
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/downBook/{id}")
	public void downBook(@PathVariable Integer id, HttpServletResponse response) throws IOException {
		String res = "";
		JsonBean jsonBean = downLoadService.downLoadNovel(id);
		if (jsonBean.isSuccess()){
			String key = RandomStringUtil.setToken(jsonBean);
			jsonBean.setMsg(key);
		}
		res = JSONObject.toJSONString(jsonBean);
		response.setCharacterEncoding("utf-8");
		response.getWriter().println(res);
	}

	/**
	 * token下载小说(系统下载完成后,会在用户再次请求时给予令牌,才能下载小说)
	 * @param token
	 * @param response
	 */
	@RequestMapping(value = "/download")
	public void down(@RequestParam("token") String token, HttpServletResponse response){
		JsonBean jsonBean = RandomStringUtil.getToken(token);
		if (jsonBean != null){
			if (jsonBean.isSuccess()){
				try {
					File file = new File(jsonBean.getSavePath());
					String filename = jsonBean.getNovelName();
					response.setContentType("multipart/form-data");
					response.setHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(filename, "UTF-8") + "\"");
					FileInputStream fin = new FileInputStream(file);
					OutputStream out = response.getOutputStream();
					byte b[] = new byte[1024];
					int len = 0;
					while ((len = fin.read(b)) > 0) {
						out.write(b, 0, len);
					}
					fin.close();
					out.flush();
					out.close();
				} catch (IOException e) {
					logger.error("文件下载出错!");
				}
			}
		}
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
