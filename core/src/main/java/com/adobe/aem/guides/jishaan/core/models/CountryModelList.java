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
public class CountryModelList {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	@Optional
	private List<Resource> countries;

	private List<Country> countriesList = new ArrayList<>();

	@PostConstruct
	protected void init() {
		logger.debug("Inside init() of CountryModelList class");
		if (!countries.isEmpty()) {
			for (Resource resource : countries) {
				Country country = resource.adaptTo(Country.class);
				countriesList.add(country);
			}
		}

	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public List<Resource> getCountries() {
		return countries;
	}

	public void setCountries(List<Resource> countries) {
		this.countries = countries;
	}

	public List<Country> getCountriesList() {
		return countriesList;
	}

	public void setCountriesList(List<Country> countriesList) {
		this.countriesList = countriesList;
	}

}
