package de.othr.sw.bank.service;

import de.othr.sw.bank.entity.TransferRequest;
import org.springframework.http.ResponseEntity;

public interface BankingServiceExternalIF {

    ResponseEntity<TransferRequest> transferMoney(TransferRequest transferRequest) throws AccountNotFoundException, InvalidTransferException, NotEnoughMoneyException;

    ResponseEntity<TransferRequest> mandateMoney(TransferRequest transferRequest) throws AccountNotFoundException, InvalidTransferException, NotEnoughMoneyException;


}
