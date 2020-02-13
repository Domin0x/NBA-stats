package com.domin0x.RESTCalling.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

    public void uploadFile(File uploadFile) {
        amazonS3Client.putObject(defaultBucketName, uploadFile.getName(), uploadFile);
    }

    public void uploadFile(String name, byte[] content)  {
        File file = new File("/tmp/"+name);
        file.canWrite();
        file.canRead();
        FileOutputStream iofs = null;
        try {
            iofs = new FileOutputStream(file);
            iofs.write(content);
            amazonS3Client.putObject(defaultBucketName, file.getName(), file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public byte[] getFile(String key) {
        S3Object obj = amazonS3Client.getObject(defaultBucketName, key);
        S3ObjectInputStream stream = obj.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(stream);
            obj.close();
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
