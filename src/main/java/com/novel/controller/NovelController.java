package com.novel.controller;

import com.novel.service.NovelService;
import com.novel.service.impl.EhcacheDB;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;


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
		return "index";
	}

	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String top(Model model){
		return "top";
	}

	@RequestMapping(value = "/foot", method = RequestMethod.GET)
	public String foot(Model model){
		return "foot";
	}
}
