package net.mpqdata.app.mpqdataapi.web.api.rest.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Setter;
import net.mpqdata.app.mpqdataapi.web.api.rest.RestApiVersionHolder;

@RestController
public class HelloRestController {

	@Autowired
	@Setter
	private BuildProperties buildProperties;

	@RequestMapping("/api/rest/v{version}/hello")
	public Map<String, Object> hello(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();

		map.put("group", buildProperties.getGroup());
		map.put("artifact", buildProperties.getArtifact());
		map.put("version", buildProperties.getVersion());
		map.put("apiVersion", RestApiVersionHolder.getRestApiVersion());
		map.put("now", new Date());

		return map;
	}

}
