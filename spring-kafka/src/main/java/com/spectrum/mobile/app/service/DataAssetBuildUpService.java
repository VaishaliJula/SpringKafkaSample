package com.spectrum.mobile.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spectrum.mobile.app.domain.Greeting;

@Service
public class DataAssetBuildUpService {

	@Autowired
	private KafkaTemplate<String, Greeting> kafkaTemplate;

	public void sendMessage() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Greeting gr = new Greeting();
			gr.setMsg("Hello");
			gr.setName("Spectrum");
			String message = mapper.writeValueAsString(gr);
			ListenableFuture<SendResult<String, Greeting>> send = kafkaTemplate.send("DataAssetBuld_Test", 0, "key",
					gr);
			send.addCallback(new ListenableFutureCallback<SendResult<String, Greeting>>() {
				@Override
				public void onSuccess(SendResult<String, Greeting> result) {
					System.out.println(
							"Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
				}
				@Override
				public void onFailure(Throwable ex) {
					System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
