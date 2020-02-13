package com.domin0x.RESTCalling.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Iterator;
import java.util.List;

@Service
public class AmazonService {
    @Autowired
    AmazonS3 amazonS3Client;

    @Value("${amazonS3.bucketName}")
    String defaultBucketName;

    public List<Bucket> getAllBuckets() {
        return amazonS3Client.listBuckets();
    }


    public void uploadFile(String name, byte[] content)  {
        InputStream is = new ByteArrayInputStream(content);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/png");
        amazonS3Client.putObject(defaultBucketName, name, is, metadata);
    }

    public String getObjectURL(String bucketName, String key){
        return amazonS3Client.getUrl(bucketName, key).toString();
    }

    public Object getObjectURL(String key) {
        return getObjectURL(defaultBucketName, key);
    }

    public boolean checkIfObjectExists(String key){
        return amazonS3Client.doesObjectExist(defaultBucketName, key);
    }

    public void deleteFile(String key) {
        amazonS3Client.deleteObject(defaultBucketName, key);
    }

    public void deleteAllInBucket(){
        deleteAllInBucket(defaultBucketName);
    }

    public void deleteAllInBucket(String bucketName){
        ObjectListing objectListing = amazonS3Client.listObjects(bucketName);
        while (true) {
            Iterator<S3ObjectSummary> objIter = objectListing.getObjectSummaries().iterator();
            while (objIter.hasNext()) {
                amazonS3Client.deleteObject(bucketName, objIter.next().getKey());
            }
            if (objectListing.isTruncated()) {
                objectListing = amazonS3Client.listNextBatchOfObjects(objectListing);
            } else {
                break;
            }
        }
    }
}
