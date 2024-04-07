package com.epam.gymcrm.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class LoginAttemptService {

	public static final int MAX_ATTEMPT = 3;
	private LoadingCache<String, Integer> attemptsCache;

	@Autowired
	private HttpServletRequest request;

	public LoginAttemptService() {
		super();
		attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build(new CacheLoader<>() {
			@Override
			public Integer load(final String key) {
				return 0;
			}
		});
	}

	public void loginFailed(final String key) {
		int attempts;
		try {
			attempts = attemptsCache.get(key);
		} catch (final ExecutionException e) {
			attempts = 0;
		}
		attempts++;
		attemptsCache.put(key, attempts);
	}

	public boolean isBlocked() {
		try {
			return attemptsCache.get(getClientIP()) >= MAX_ATTEMPT;
		} catch (final ExecutionException e) {
			return false;
		}
	}

	private String getClientIP() {
		String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.getRemoteAddr())) {
			return request.getRemoteAddr();
		}
		return xfHeader.split(",")[0];
	}
}