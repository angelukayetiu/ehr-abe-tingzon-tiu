package com.ehr.upcsg.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.ehr.upcsg.model.User;
import com.ehr.upcsg.service.RepositoryService;
import com.ehr.upcsg.service.UserService;

@Controller
@RequestMapping("home")
public class AdminController {
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/{username}")
	public String home(@PathVariable("username") String username, Model model,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		setupHomePage(model, username);				
		return "home/homepage";
	}
	
	private void setupHomePage(Model model, String username) {
		//user
		User user = userService.findUserByUsername(username);
		model.addAttribute("loggedInUser", user);
		
		//files/folders in root
		List<Bucket> folderList = repositoryService.list();
		model.addAttribute("folderList", folderList);	
		
	}
	
	private void setupViewBucket(Model model, String username, String bucketname) {
		//user
		User user = userService.findUserByUsername(username);
		model.addAttribute("loggedInUser", user);
		
		//files/folders in root
		ObjectListing objectListing= repositoryService.list(bucketname);
		model.addAttribute("folderList", objectListing.getObjectSummaries());	
	}
	
	private void setupViewFolder(Model model, String username, String bucketname, String path) {
		//user
		User user = userService.findUserByUsername(username);
		model.addAttribute("loggedInUser", user);
		
		//files/folders in root
		ObjectListing objectListing= repositoryService.list(bucketname, path);
		model.addAttribute("folderList", objectListing.getObjectSummaries());	
	}
	
	@RequestMapping("/{username}/{bucketname}")
	public String viewBucket(@PathVariable("username") String username, Model model,
			@PathVariable("bucketname") String bucketname){
		setupViewBucket(model, username, bucketname);
		return "repository/view-bucket";
	}
	@RequestMapping("/{username}/search?searchName={searchname}")
	public String search(@PathVariable("username") String username, Model model,
			@PathVariable("searchname") String searchname) throws IOException {
		setupHomePage(model, username);
		System.out.println("here in search");
		if(searchname.length()>0)
			model.addAttribute("folderlist", repositoryService.search(searchname));
		
		return "repository/view-bucket";
	}
	

	@RequestMapping("/{username}/{bucketname}/{path}")
	public String viewFolder(@PathVariable("username") String username,
			@PathVariable("bucketname") String bucketname,
			@PathVariable("path") String path,
			Model model,
			@RequestParam("submit") String action){
		setupViewFolder(model, username, bucketname, action);
		return "repository/view-bucket";
	}

		
	@RequestMapping("/{username}/fileController")
	public void repositoryController(HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session, @PathVariable("username") String username,
			@RequestParam("submit") String action,
			@RequestParam("bucketName") String bucketName,			
			Model model){
		String[] files = request.getParameterValues("taggedFile");
		if(action.equals("DELETE"))
			delete(files,bucketName);
		else if(action.equals("DOWNLOAD")){
			try {
				for(String file: files){
					downloadFile(username, bucketName, file, request, response);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void delete(String[] files, String bucketName) {
		for(String file:files){
			if(file.contains(".cpabe"))
				repositoryService.delete(bucketName, file.replaceAll(".cpabe", ".cpaes"));
			repositoryService.delete(bucketName, file);
		}
	}
	
	private void downloadFile(String username, String bucketName, String filename, 
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		String mimetype = request.getSession().getServletContext().getMimeType(filename);
		S3Object obj= repositoryService.download(username, bucketName, filename);
		response.setContentType(mimetype);
		response.setContentLength((int) obj.getObjectMetadata().getContentLength());
		response.setHeader("Content-Disposition","attachment; filename=\"" + filename.replace(".cpabe", "") +"\"");
		FileCopyUtils.copy(obj.getObjectContent() , response.getOutputStream());				
	}
	
	@RequestMapping("/download/{username}/{filename}")
	public void download(@PathVariable("filename") String filename, 
			@PathVariable("username") String username, Model model,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		downloadFile(username, "test-download-bucket", filename, request, response);
		
	}

}
