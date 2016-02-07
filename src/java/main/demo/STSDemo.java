/**
 * 
 */
package demo;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import com.amazonaws.services.securitytoken.model.GetSessionTokenResult;

/**
 * @author benxing
 *
 */
public class STSDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(getCredentials(7200));
	}

	public static Credentials getCredentials(int timeout) {
		Credentials sessionCredentials = null;

		try {
			AWSSecurityTokenServiceClient stsClient = new AWSSecurityTokenServiceClient(
					new ProfileCredentialsProvider());
			//
			// Manually start a session.
			GetSessionTokenRequest getSessionTokenRequest = new GetSessionTokenRequest();
			// Following duration can be set only if temporary credentials are
			// requested by an IAM user.
			getSessionTokenRequest.setDurationSeconds(timeout);

			GetSessionTokenResult sessionTokenResult = stsClient.getSessionToken(getSessionTokenRequest);
			sessionCredentials = sessionTokenResult.getCredentials();
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return sessionCredentials;
	}

}
