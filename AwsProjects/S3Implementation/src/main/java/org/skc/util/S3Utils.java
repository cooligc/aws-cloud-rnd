package org.skc.util;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;

import java.io.*;

/**
 * Utils for S3 App
 * @author IgnatiusCipher
 */
public class S3Utils {

    private static final String S3_OBJECT_KEY="myObject";


    public static AmazonS3 createS3Client(Region region){
        AmazonS3 amazonS3 = new AmazonS3Client(Utils.getCredential());
        Utils.log("AmazonS3 Client got created");
        if(null == region){
            region = Region.getRegion(Regions.US_WEST_2);
        }
        amazonS3.setRegion(region);
        Utils.log("Region "+region.getName()+" set for amazon client");
        return amazonS3;
    }


    public static void createBucket(AmazonS3 amazonS3,String bucketName){
        Utils.log("Going to create a bucket on s3 of name " + bucketName.toLowerCase());
        amazonS3.createBucket(bucketName.toLowerCase());
        Utils.log(bucketName.toLowerCase()+"got created on S3");
    }


    public static void createFileOnS3(AmazonS3 amazonS3,String bucketName,File file){
        Utils.log("Going to put a file of name "+file+" on bucket "+bucketName);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,file.getName(),file);
        amazonS3.putObject(putObjectRequest);
    }


    public static void deleteFileOnS3(AmazonS3 amazonS3,String bucketName,File file){
        Utils.log("Deleting an object of name "+file.getName());
        amazonS3.deleteObject(bucketName, file.getName());
    }

    public static void downloadFileFromS3(AmazonS3 amazonS3,String bucketName,String name)throws IOException{
        Utils.log("Downloading an object");
        S3Object object = amazonS3.getObject(new GetObjectRequest(bucketName, name));
        System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
        displayTextInputStream(object.getObjectContent());

    }


    public static void readMetaDataForS3(AmazonS3 amazonS3){
        Utils.log("===============================================================");
        Utils.log("Listing buckets");
        Utils.log("===============================================================");
        for (Bucket bucket : amazonS3.listBuckets()) {
            System.out.println(" - " + bucket.getName());
        }
        Utils.log("===============================================================");


        Utils.log("===============================================================");
        System.out.println("Listing objects");
        Utils.log("===============================================================");
        for (Bucket bucket : amazonS3.listBuckets()) {
            getObjects(amazonS3,bucket.getName());
        }
        Utils.log("\n");


    }

    private static void getObjects(AmazonS3 amazonS3,String bucketName) {
        ObjectListing objectListing = amazonS3.listObjects(new ListObjectsRequest()
                .withBucketName(bucketName));
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            Utils.log(" - " + objectSummary.getKey() + "  " +
                    "(size = " + objectSummary.getSize() + ")");
        }
    }

    private static void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println("    " + line);
        }
        System.out.println();
    }
}
