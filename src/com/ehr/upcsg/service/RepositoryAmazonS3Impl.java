package com.ehr.upcsg.service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.ehr.upcsg.dao.KeyCommandDaoImpl;
import com.ehr.upcsg.dao.KeyDao;
import com.ehr.upcsg.exceptions.CommandFailedException;

import java.io.ByteArrayInputStream;

@Service("repositoryService")
public class RepositoryAmazonS3Impl implements RepositoryService{
		
	private AmazonS3 s3;

	private KeyDao keyDao;
	private int proxyEnabled = 0;
	
	public RepositoryAmazonS3Impl() {
		AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (~/.aws/credentials), and is in valid format.",
                    e);
        }
        if (proxyEnabled == 1) {
            ClientConfiguration clientConfig = new ClientConfiguration();
            clientConfig.setProtocol(Protocol.HTTPS);
            clientConfig.setProxyHost("proxy8.upd.edu.ph");
            clientConfig.setProxyPort(8080); 
            s3 = new AmazonS3Client(credentials, clientConfig);
        } else {    
        		s3 = new AmazonS3Client(credentials);
        }
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
        s3.setRegion(usWest2);              

        //for KeyManager
        keyDao = new KeyCommandDaoImpl(1);
	}

	/* (non-Javadoc)
	 * @see com.ehr.upcsg.service.RepositoryService#createBucket(java.lang.String)
	 */
	@Override
	public void createBucket(String bucketName)
			throws AmazonServiceException, AmazonClientException{
        System.out.println("Creating bucket " + bucketName + "\n");
        s3.createBucket(bucketName);		
	}
	
	/* (non-Javadoc)
	 * @see com.ehr.upcsg.service.RepositoryService#list()
	 */
	@Override
	public List<Bucket> list()
			throws AmazonServiceException, AmazonClientException{
        /*
         * List the buckets in your account
         */
        System.out.println("Listing buckets");
        for (Bucket bucket : s3.listBuckets()) {
            System.out.println(" - " + bucket.getName());
        }
        System.out.println();
        return s3.listBuckets();
	}
	
	/* (non-Javadoc)
	 * @see com.ehr.upcsg.service.RepositoryService#list(java.lang.String)
	 */
	@Override
	public ObjectListing list(String bucketName)
		throws AmazonServiceException, AmazonClientException{
        System.out.println("Listing objects");
        ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
                .withBucketName(bucketName));
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println(" - " + objectSummary.getKey() + "  " +
                               "(size = " + objectSummary.getSize() + ")");
        }
        System.out.println();
        return objectListing;
	}
	
	/* (non-Javadoc)
	 * @see com.ehr.upcsg.service.RepositoryService#list(java.lang.String, java.lang.String)
	 */
	@Override
	public ObjectListing list(String bucketName, String path)
			throws AmazonServiceException, AmazonClientException{
        System.out.println("Listing objects");
        ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
                .withBucketName(bucketName)
                .withPrefix(path));
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println(" - " + objectSummary.getKey() + "  " +
                               "(size = " + objectSummary.getSize() + ")");
        }
        System.out.println();
        return objectListing;
	}
		
	
	/* (non-Javadoc)
	 * @see com.ehr.upcsg.service.RepositoryService#upload(java.lang.String, java.lang.String, java.io.File)
	 */

	/* (non-Javadoc)
	 * @see com.ehr.upcsg.service.RepositoryService#download(java.lang.String, java.lang.String)
	 */
	@Override
	public S3Object download(String username, String bucketName, String key) 
			throws AmazonServiceException, AmazonClientException {		
        System.out.println("Downloading an object");
        S3Object object = s3.getObject(new GetObjectRequest(bucketName, key));
        System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());

		File file;
		try {
			file = File.createTempFile(key, ".cpabe");
			FileUtils.copyInputStreamToFile(object.getObjectContent(), file);
			keyDao.decrypt(username, "random", file.getAbsolutePath());
			
			System.out.println("decrypted file ");
			System.out.println(file.getCanonicalPath());
			File decryptedFile = new File(file.getCanonicalPath().substring(0, file.getCanonicalPath().length()-6));
	        if(decryptedFile.exists()){
	        		FileInputStream fileInputStream = new FileInputStream(decryptedFile);
	        		object.setObjectContent(fileInputStream);        	
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CommandFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}
	
	/* (non-Javadoc)
	 * @see com.ehr.upcsg.service.RepositoryService#delete(java.lang.String, java.lang.String)
	 */
	@Override
	public void delete(String bucketName, String key) 
			throws AmazonServiceException, AmazonClientException{
        /*
         * Delete an object - Unless versioning has been turned on for your bucket,
         * there is no way to undelete an object, so use caution when deleting objects.
         */
        System.out.println("Deleting an object\n");
        s3.deleteObject(bucketName, key);		
	}

	/* (non-Javadoc)
	 * @see com.ehr.upcsg.service.RepositoryService#delete(java.lang.String)
	 */
	@Override
	public void delete(String bucketName) 
			throws AmazonServiceException, AmazonClientException{

        /*
         * Delete a bucket - A bucket must be completely empty before it can be
         * deleted, so remember to delete any objects from your buckets before
         * you try to delete them.
         */
        System.out.println("Deleting bucket " + bucketName + "\n");
        s3.deleteBucket(bucketName);		
	}
		
    public static void main(String[] args) throws IOException {
        String bucketName = "my-first-s3-bucket-" + UUID.randomUUID();
        String key = "MyObjectKey";

    		RepositoryService repositoryService = new RepositoryAmazonS3Impl();
    		repositoryService.createBucket(bucketName);
    		repositoryService.list();
//    		repositoryService.upload(bucketName, key, createSampleFile());
    		repositoryService.list(bucketName);
    		repositoryService.delete(bucketName, key);
    		repositoryService.delete(bucketName);
    }

	@Override
	public void upload(String username, MultipartFile assetFile, String policy,
			String bucketName)
			throws AmazonServiceException, AmazonClientException {
		FileInputStream fileInputStream;
		try {
			
			File file = createTempFile(assetFile);
			keyDao.encrypt(file.getAbsolutePath(), policy);					
			File encryptedFile = new File(file.getCanonicalPath()+ ".cpabe");
	        if(encryptedFile.exists()){
	        		fileInputStream = new FileInputStream(encryptedFile);
			
	        		ObjectMetadata meta = new ObjectMetadata();
	        		meta.setContentLength(fileInputStream.available());
	            System.out.println("Uploading " + assetFile.getOriginalFilename() + "...");
	    			s3.putObject(new PutObjectRequest(bucketName, assetFile.getOriginalFilename()+".cpabe", fileInputStream, meta));
	            System.out.println(encryptedFile.getName() + " upload success.");
	            encryptedFile.delete();
	        }
		} catch (IOException e) {
			System.out.println("io error");
		} catch (CommandFailedException e) {
			System.out.println("can't decrypt");
		}
		
		
		
	}

	private File createTempFile(MultipartFile assetFile) throws IOException {
        File file = File.createTempFile(assetFile.getOriginalFilename(), ".temp");
        file.deleteOnExit();
        FileInputStream fileInputStream = (FileInputStream) assetFile.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buf)) > 0) {
                fileOutputStream.write(buf, 0, bytesRead);
            }
        } finally {
        		fileInputStream.close();
        		fileOutputStream.close();
        }
        return file;
	}

	@Override
	public List<S3ObjectSummary> search(String searchKey)
			throws AmazonServiceException, AmazonClientException {
        System.out.println("Listing objects");
        List<S3ObjectSummary> list = new ArrayList<S3ObjectSummary>();
		for(S3ObjectSummary objectSummary: 
			list("test-bucket-download").getObjectSummaries()){
			System.out.println("here");
			if (objectSummary.getKey().contains(searchKey))
				list.add(objectSummary);
		}

/*        for( Bucket bucket: list()){
        		for(S3ObjectSummary objectSummary: list(bucket.getName()).getObjectSummaries()){
        			if (objectSummary.getKey().contains(searchKey))
        				list.add(objectSummary);
        		}
        }
*/        System.out.println();
        return list;
    }
}
