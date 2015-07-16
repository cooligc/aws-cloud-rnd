package org.skc.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.skc.util.S3Utils;

import com.amazonaws.services.s3.AmazonS3;

/**
 * Created with IntelliJ IDEA.
 * User: sitakant1991
 * Date: 7/16/15
 * Time: 1:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class App {
    public static void main(String... strings) throws IOException{
    	final String bucketName="sample-igc-docbase";
        AmazonS3 amazonS3 = S3Utils.createS3Client(null);
        S3Utils.createBucket(amazonS3,bucketName);
        File file = createFile();
        S3Utils.createFileOnS3(amazonS3, bucketName, file);
        S3Utils.downloadFileFromS3(amazonS3, bucketName, file.getName());
        S3Utils.deleteFileOnS3(amazonS3, bucketName, file);
        S3Utils.readMetaDataForS3(amazonS3);
        
    }

	private static File createFile() throws FileNotFoundException, UnsupportedEncodingException {
		String fileName="skc"+System.currentTimeMillis();
		PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		writer.println("The first line");
		writer.println("The second line");
		writer.close();
		return new File(fileName);
	}
}
