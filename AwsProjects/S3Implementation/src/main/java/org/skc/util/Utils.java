package org.skc.util;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility Layer for AWS
 * @author IgnatiusCipher
 */
public class Utils {
    private static Properties properties;

    //Reading properties files at class loading time and putting on a map
    static{
        ClassLoader classLoader = Utils.class.getClassLoader();
        InputStream resourceStream = classLoader.getResourceAsStream("AwsCredentials.properties");
        properties = new Properties();
        try {
            properties.load(resourceStream);
        }catch (IOException ioException){
            ioException.printStackTrace();
            properties = null;
            System.exit(1);
        }
        System.out.println(properties);
    }

    public static Properties getConfigProperties() {
        return properties;
    }

    public static AWSCredentials getCredential(){
       AWSCredentialsProvider credentialsProvider = new ClasspathPropertiesFileCredentialsProvider();
       return credentialsProvider.getCredentials();
    }

    public static void log(Object o){
        System.out.println(o);
    }
}
