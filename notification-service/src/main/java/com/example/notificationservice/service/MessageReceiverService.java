package com.example.notificationservice.service;

import com.example.notificationservice.domain.Customer;

import common.data.AtmTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageReceiverService {

  @Autowired
  private LookAsideCachedCustomerService lookAsideCachedCustomerService;

  Logger logger = LoggerFactory.getLogger(MessageReceiverService.class);

  public MessageReceiverService() {
    logger.info("Creating consumer service");
  }

  public MessageReceiverService(LookAsideCachedCustomerService lookAsideCachedCustomerService) {
    this.lookAsideCachedCustomerService = lookAsideCachedCustomerService;
  }

  public void receiveMessage(AtmTransaction tx) {
    String account = tx.fromAcct1 + tx.fromAcct2;
    logger.info("Received transaction <" + tx.processId + "> of " + account);
    Optional<Customer> customer = lookAsideCachedCustomerService.findById(account);
    if (customer.isEmpty()) {
      logger.info("Customer " + account + " not found");
    }
    // TODO (Production): send this data down the Enterprise Message Hub / Persist / Do whatever
  }
}
