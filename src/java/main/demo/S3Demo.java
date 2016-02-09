/**
 * 
 */
package demo;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.fasterxml.jackson.databind.ObjectMapper;

import data.DVAAlert;
import data.Location;
import data.Severity;

/**
 * @author benxing
 *
 */
public class S3Demo {

	static ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		S3Sample(STSDemo.getCredentials(2400));
	}
	
	public static DVAAlert createAlert(String name) {
		DVAAlert alert = new DVAAlert();
		alert.setName(name);
		alert.setInstanceName(null);
		alert.setSeverity(Severity.Critical);
		alert.setDescription("testing alert");
		alert.setProviderId(UUID.randomUUID().toString());
		alert.setServiceId(UUID.randomUUID().toString());
		alert.setTimeout(3600);
		alert.setTimestamp(new Date().getTime());
		alert.setTrigger(null);
		
		Location location = new Location();
		location.setDatacenter("CHI");
		location.setSuperpod("SP1");
		location.setPod("AGG");
		location.setDevice("test.test.com");
		
		alert.setLocation(location);
		
		return alert;
	}
	
	public static void S3Sample(Credentials credentials) {
		BasicSessionCredentials bCredentials = new BasicSessionCredentials(credentials.getAccessKeyId(), credentials.getSecretAccessKey(), credentials.getSessionToken());

		AmazonS3 s3client = new AmazonS3Client(bCredentials);
		
		//bucket info
		String bucketName = "benxing-dav-alertings";		
		
		//String key
		
		try {
			if(s3client.doesBucketExist(bucketName)) {
				ObjectListing objectlisting = s3client.listObjects(bucketName);
				
				 for (S3ObjectSummary objectSummary : objectlisting.getObjectSummaries()) {
		                System.out.println(" - " + objectSummary.getKey() + "  " +
		                        "(size = " + objectSummary.getSize() + ")");
		                
		                s3client.deleteObject(bucketName, objectSummary.getKey());
		            }
				
				
				s3client.deleteBucket(bucketName);
			}

			s3client.createBucket(bucketName);
			
			DVAAlert alert = createAlert("testalert");
			
			InputStream stream = null;
			byte[] content = mapper.writeValueAsBytes(alert);
			stream = new ByteArrayInputStream(content);

			
			ObjectMetadata metadata=new ObjectMetadata();
			metadata.setContentLength(content.length);
			
			PutObjectRequest request = new PutObjectRequest(bucketName, "testalert", stream, metadata);
			s3client.putObject(request.withCannedAcl(CannedAccessControlList.PublicRead));
			
			
			S3Object object = s3client.getObject(
	                  new GetObjectRequest(bucketName, "testalert"));
			InputStream objectData = object.getObjectContent();
			
			System.out.println(read(objectData));
			// Process the objectData stream.
			objectData.close();
			
		} catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        } catch (Exception ex) {
        	System.out.println(ex);
        }		
		
	}
	
	public static String read(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }	

}
