package com.ehr.upcsg.controller;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.amazonaws.http.HttpRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ehr.upcsg.service.RepositoryService;
import com.ehr.upcsg.service.UserService;

@Controller
@RequestMapping("/upload")
public class UploadController {
	
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/{username}", method = RequestMethod.GET)
	public String setupForm(Model model, @PathVariable("username") String username){
		model.addAttribute("loggedInUser",userService.findUserByUsername(username));
		return "upload/upload-file";
	}
	
	@RequestMapping(value="/{username}",method = RequestMethod.POST)
	public String handleUpload(
			@RequestParam("assetFile") MultipartFile assetFile,
			@PathVariable("username") String username,
			@RequestParam("policy") String policy) throws IOException{
		try {
System.out.println("here");
			if (assetFile != null) {		
				repositoryService.upload(
						username,
						assetFile,
						policy,
						"test-bucket-download");
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/home/"+username+"/test-bucket-download";
	}
}
