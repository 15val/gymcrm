package com.epam.gymcrm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class TokenBlacklistService {

	private Set<String> blacklist = new HashSet<>();

	public void addToBlacklist(String token) {
		blacklist.add(token);
	}

	public boolean isBlacklisted(String token) {
		return blacklist.contains(token);
	}
}
