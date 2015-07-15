package org.skc.app;

import com.amazonaws.services.s3.AmazonS3;
import org.skc.util.S3Utils;

/**
 * Created with IntelliJ IDEA.
 * User: sitakant1991
 * Date: 7/16/15
 * Time: 1:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class App {
    public static void main(String... strings){
        AmazonS3 amazonS3 = S3Utils.createS3Client(null);
        S3Utils.createBucket(amazonS3,"dummyBucket");
    }
}
