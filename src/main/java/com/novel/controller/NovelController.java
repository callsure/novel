package com.novel.controller;

import com.novel.entitys.Tnovel;
import com.novel.service.NovelService;
import com.novel.service.impl.EhcacheDB;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by runshu.lin on 16/11/29.
 */
@Controller
public class NovelController {

	@Resource(name = "ehcacheDB")
	private EhcacheDB ehcacheDB;

	@Resource
	private NovelService novelService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model){
		ehcacheDB.getNovelRulesLists();
		return "index";
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String top(Model model){
		return "main";
	}

	@RequestMapping(value = "/classify/{id}")
	public void getsHotNovelByType(@PathVariable String id){
		List<Tnovel> list = ehcacheDB.getsNovelByType(id);
	}
}
