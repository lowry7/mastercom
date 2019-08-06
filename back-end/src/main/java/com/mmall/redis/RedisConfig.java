package com.mmall.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

@Component
@ImportResource(value="classpath:redis.properties")
public class RedisConfig {
	 @Value("${redis.host}")
	private String host;
	 @Value("${redis.port}")
	private int port;
	 @Value("${redis.timeout}")
	private int timeout;//秒
	 @Value("${redis.password}")
	private String password;
	 @Value("${redis.poolMaxTotal}")
	private int poolMaxTotal;
	 @Value("${redis.poolMaxIdle}")
	 private int poolMaxIdle;
	 @Value("${redis.poolMaxWait}")
	 private int poolMaxWait;//秒
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPoolMaxTotal() {
		return poolMaxTotal;
	}
	public void setPoolMaxTotal(int poolMaxTotal) {
		this.poolMaxTotal = poolMaxTotal;
	}
	public int getPoolMaxIdle() {
		return poolMaxIdle;
	}
	public void setPoolMaxIdle(int poolMaxIdle) {
		this.poolMaxIdle = poolMaxIdle;
	}
	public int getPoolMaxWait() {
		return poolMaxWait;
	}
	public void setPoolMaxWait(int poolMaxWait) {
		this.poolMaxWait = poolMaxWait;
	}
}
