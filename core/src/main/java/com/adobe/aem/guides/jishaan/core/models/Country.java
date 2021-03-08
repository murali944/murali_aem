package com.adobe.aem.guides.jishaan.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = Resource.class)
public class Country {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	@Optional
	private String country;

	@Inject
	@Optional
	private List<Resource> states;

	@Optional
	private List<State> statesList = new ArrayList<>();

	@PostConstruct
	protected void init() {
		logger.debug("Inside init method of Country Model class");
		if (!states.isEmpty()) {
			for (Resource resource : states) {
				State state = resource.adaptTo(State.class);
				statesList.add(state);
			}
		}
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<Resource> getCountries() {
		return states;
	}

	public void setCountries(List<Resource> countries) {
		this.states = countries;
	}

	public List<State> getStatesList() {
		return statesList;
	}

	public void setStatesList(List<State> statesList) {
		this.statesList = statesList;
	}

	public Logger getLogger() {
		return logger;
	}

}
