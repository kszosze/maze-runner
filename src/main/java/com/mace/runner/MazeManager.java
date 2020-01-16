package com.mace.runner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.mace.runner.model.Maze;
import com.mace.runner.model.Position;

/**
 * The Class MazeManager.
 */
@ConfigurationProperties(prefix = "endpoints")
public class MazeManager implements IMazeManager {

	private static final Logger log = LoggerFactory.getLogger(MazeManager.class);

	private final RestTemplate restTemplate;

	private String moveUrl = "";
	
	private String isOutUrl = "";
	
	private String mapUrl = "";
	
	private String pathUrl = "";

	private ResponseEntity<String> response;

	/**
	 * Instantiates a new maze manager.
	 *
	 * @param resTemplate the res template
	 */
	public MazeManager(final RestTemplate resTemplate) {
		this.restTemplate = resTemplate;
	}

	/* (non-Javadoc)
	 * @see com.mace.runner.IMazeManager#getMap()
	 */
	@Override
	public Maze getMap() {
		log.info("Requesting a map to {}", mapUrl);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.parseMediaType("text/plain")));
		HttpEntity<Object> requestEntity = new HttpEntity<>(httpHeaders);
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		final ResponseEntity<String> rawMap = restTemplate.exchange(mapUrl, HttpMethod.GET, requestEntity, String.class);
		final List<String> rawMapList = Arrays.asList(rawMap.getBody().split("\n"));
		return new Maze(rawMapList);
	}

	/* (non-Javadoc)
	 * @see com.mace.runner.IMazeManager#move(com.mace.runner.model.Position)
	 */
	@Override
	public Boolean move(final Position position) {
		log.info("Requesting move ({}, {}) to {}", position.getX(), position.getY(), moveUrl);
		response = restTemplate.postForEntity(moveUrl, position, String.class);
		return response.getStatusCode().is2xxSuccessful();
	}

	/* (non-Javadoc)
	 * @see com.mace.runner.IMazeManager#isOut()
	 */
	@Override
	public Boolean isOut() {
		log.info("Requesting if runner is out");
		return restTemplate.getForEntity(isOutUrl, Boolean.class).getBody();
	}

	/* (non-Javadoc)
	 * @see com.mace.runner.IMazeManager#getPath()
	 */
	@Override
	public List<Position> getPath() {

		log.info("Request a path to {}", pathUrl);
		return Arrays.asList(restTemplate.getForObject(pathUrl, Position[].class));
	}

	/**
	 * Gets the move url.
	 *
	 * @return the move url
	 */
	public String getMoveUrl() {
		return moveUrl;
	}

	/**
	 * Sets the move url.
	 *
	 * @param moveUrl the new move url
	 */
	public void setMoveUrl(String moveUrl) {
		this.moveUrl = moveUrl;
	}

	/**
	 * Gets the checks if is out url.
	 *
	 * @return the checks if is out url
	 */
	public String getIsOutUrl() {
		return isOutUrl;
	}

	/**
	 * Sets the checks if is out url.
	 *
	 * @param isOutUrl the new checks if is out url
	 */
	public void setIsOutUrl(String isOutUrl) {
		this.isOutUrl = isOutUrl;
	}

	/**
	 * Gets the map url.
	 *
	 * @return the map url
	 */
	public String getMapUrl() {
		return mapUrl;
	}

	/**
	 * Sets the map url.
	 *
	 * @param mapUrl the new map url
	 */
	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}

	/**
	 * Gets the path url.
	 *
	 * @return the path url
	 */
	public String getPathUrl() {
		return pathUrl;
	}

	/**
	 * Sets the path url.
	 *
	 * @param pathUrl the new path url
	 */
	public void setPathUrl(String pathUrl) {
		this.pathUrl = pathUrl;
	}
}
