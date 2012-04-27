package com.scirisk.riskanalyzer.model;

import java.io.OutputStream;

import com.scirisk.riskanalyzer.domain.Network;

public interface NetworkMarshaller {

  void marshall(Network network, OutputStream os);

}