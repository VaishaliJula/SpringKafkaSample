package com.spectrum.mobile.app.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spectrum.mobile.app.service.DataAssetBuildUpService;

@RestController
@RequestMapping("/api")
public class DataAssetBuildUpResource {

	@Autowired
	private DataAssetBuildUpService producer;
	
	@RequestMapping("/test")
	public void test() {
		producer.sendMessage();
	}
}
