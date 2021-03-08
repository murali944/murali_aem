package com.adobe.aem.guides.jishaan.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = Resource.class)
public class State {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	@Optional
	private String stateName;

	@Inject
	@Optional
	private String stateCapitalName;

	@PostConstruct
	protected void init() {
		logger.debug("Inside init of State model");
		logger.debug("Printing stateName and StateCapitalName -->{},{}",stateName,stateCapitalName);
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateCapitalName() {
		return stateCapitalName;
	}

	public void setStateCapitalName(String stateCapitalName) {
		this.stateCapitalName = stateCapitalName;
	}

	
	@Override
	public String toString() {
		return "State [stateName=" + stateName + ", stateCapitalName=" + stateCapitalName + "]";
	}
	
	

}
