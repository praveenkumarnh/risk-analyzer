package com.scirisk.riskanalyzer.service;

import java.io.IOException;
import java.io.InputStream;

import com.scirisk.riskanalyzer.domain.Network;


public interface NetworkParser {

  public Network parse(InputStream is) throws IOException, NetworkValidationException;

}