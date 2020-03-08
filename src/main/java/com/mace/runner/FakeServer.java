package com.mace.runner;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.mace.runner.util.ResourceFile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@Component
public class FakeServer {

	WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(8080)); //No-args constructor will start on port 8080, no HTTPS

	private WireMock wireMock;

	private ResourceFile getMap = new ResourceFile("mappings/getMap.json");
	private ResourceFile makeMove = new ResourceFile("mappings/makeMove.json");
	private ResourceFile isOut = new ResourceFile("mappings/isOut.json");

	@PostConstruct
	public void FakeServer() {

		wireMockServer.start();

		wireMock = new WireMock("localhost", 8080);

		wireMock.register(StubMapping.buildFrom(getMap.getContent()));
		wireMock.register(StubMapping.buildFrom(makeMove.getContent()));
		wireMock.register(StubMapping.buildFrom(isOut.getContent()));
	}

}
