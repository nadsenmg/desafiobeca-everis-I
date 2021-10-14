//package br.com.nadsen.desafioeveris.config;
//
//import java.time.Duration;
//import java.util.Collections;
//import java.util.Properties;
//import java.util.concurrent.ExecutionException;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.springframework.stereotype.Service;
//
//import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
//
//import lombok.var;
//
//@Service
//public class KafkaControleSaques {
//
//	public void consumidor(String numero) throws ExecutionException, InterruptedException {
//		var consumer = new KafkaConsumer<String, String>(properties());
//		consumer.subscribe(Collections.singletonList("CONTROLE_SAQUES"));
//		var records = consumer.poll(Duration.ofMillis(100));
//		if(records.isEmpty()) {
//			System.out.println("Não encontrei registros");
//			return;
//		}
//			for(var record : records) {
//				System.out.println(record.key());
//				System.out.println(record.value());
//			}
//	}
//	
//	private static Properties properties() {
//		var properties = new Properties();
//		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
//		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, KafkaControleSaques.class.getName());
//
//		
//		return properties;
//	}
//}
