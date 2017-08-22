package com.mycompany.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JavadocCotroller {
	@RequestMapping("/docs")
	public String docs() {
		return "javadoc/index";
	}
	@RequestMapping("/overview-summary.html")
	public String overviewsummary() {
		return "javadoc/overview-summary";
	}
	@RequestMapping("/overview-frame.html")
	public String overviewframe() {
		return "javadoc/overview-frame";
	}
	@RequestMapping("/allclasses-frame.html")
	public String allclassesframe() {
		return "javadoc/allclasses-frame";
	}
	@RequestMapping("/allclasses-frame.html")
	public String allclassesframe() {
		return "javadoc/allclasses-frame";
	}
	@RequestMapping("javadoc/Motion/package-frame.html")
	public String packageframe() {
		return "javadoc/allclasses-frame";
	}
}
