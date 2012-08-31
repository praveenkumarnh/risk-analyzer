package com.scirisk.riskanalyzer.service;

import java.io.IOException;
import java.io.InputStream;

import com.scirisk.riskanalyzer.domain.DistributionNetwork;


public interface NetworkParser {

  public DistributionNetwork parse(InputStream is) throws IOException, NetworkValidationException;

}