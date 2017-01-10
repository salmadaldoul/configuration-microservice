package com.madar.tracking.configuration.configuration;

import java.util.Arrays;
import java.util.List;

/**
 * @author Salma
 *
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;



@Configuration
@ComponentScan("com.madar.tracking.configuration.*")
@EnableRedisRepositories("com.madar.configuration.configuration.*")
public class RedisConfig {


	@Bean
	JedisConnectionFactory connectionFactory() {
		JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();


		jedisConFactory.setHostName("localhost");
		jedisConFactory.setPort(30002);
		return jedisConFactory;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(connectionFactory());
		return template;
	}

	

	
	

}
