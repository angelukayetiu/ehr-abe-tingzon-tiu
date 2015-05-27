package com.ehr.upcsg.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public interface RepositoryService {

	public void createBucket(String bucketName) throws AmazonServiceException,
			AmazonClientException;

	public List<Bucket> list() throws AmazonServiceException,
			AmazonClientException;

	public ObjectListing list(String bucketName) throws AmazonServiceException,
			AmazonClientException;

	public ObjectListing list(String bucketName, String path)
			throws AmazonServiceException, AmazonClientException;

	public List<S3ObjectSummary> search(String searchKey)
			throws AmazonServiceException, AmazonClientException;

	public S3Object download(String username, String bucketName, String key)
			throws AmazonServiceException, AmazonClientException;

	public void delete(String bucketName, String key)
			throws AmazonServiceException, AmazonClientException;

	public void delete(String bucketName) throws AmazonServiceException,
			AmazonClientException;

	public void upload(String username, MultipartFile assetFile, String policy,
			String bucketName)throws AmazonServiceException, AmazonClientException;

}