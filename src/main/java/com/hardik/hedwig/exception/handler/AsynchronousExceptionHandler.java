package com.hardik.hedwig.exception.handler;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class AsynchronousExceptionHandler extends AsyncConfigurerSupport {

	@Override
	public Executor getAsyncExecutor() {
		return new SimpleAsyncTaskExecutor();
	}

	@Override
	@Nullable
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return (throwable, method, parameters) -> {
			log.error("Exception occurred in Async function {}(), Thread {}, Parameters provided: {}", method.getName(),
					Thread.currentThread().getName(),
					Arrays.stream(parameters).map(parameter -> parameter.toString()).collect(Collectors.toList()));
			log.error(throwable.getMessage());
		};

	}

}