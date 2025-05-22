package netzbegruenung.keycloak.app.messaging;

import ntfyJava.NtfyClient;
import ntfyJava.core.model.*;
import ntfyJava.core.publish.PubClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import netzbegruenung.keycloak.app.dto.ChallengeDto;
import org.jboss.logging.Logger;


import java.net.URI;
import java.util.Map;

public class FcmMessagingService implements MessagingService {

	private final Logger logger = Logger.getLogger(FcmMessagingService.class);

	private final ObjectMapper objectMapper = new ObjectMapper();

	public void send(String devicePushId, ChallengeDto challenge) {
		if (devicePushId == null) {
			logger.infof("Skip sending firebase notification: missing device push ID user [%s]", challenge.getUserName());
			return;
		}
		PubClient client = new NtfyClient(ClientType.PUB).getClient();
		NtfyRequest request = new NtfyRequest();
		request.setHost("ntfy.hackermb.com");
		request.setTopic(devicePushId);
		request.setTitle("Keycloak Authentication Notification");
		request.setMessage("New Login Request");
		try{
			client.sendNotification(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
