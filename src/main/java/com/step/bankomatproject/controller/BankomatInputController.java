package com.step.bankomatproject.controller;

import com.step.bankomatproject.entity.Bankomat;
import com.step.bankomatproject.entity.CreditCard;
import com.step.bankomatproject.service.BankomatService;
import com.step.bankomatproject.service.CreditCardCService;

public class BankomatInputController {
    private Bankomat bankomat;
    private CreditCardCService cardService;
    private BankomatService bankomatService;

    public BankomatInputController() {}

    public BankomatInputController(Bankomat bankomat, CreditCardCService cardService, BankomatService bankomatService) {
        this.bankomat = bankomat;
        this.cardService = cardService;
        this.bankomatService = bankomatService;
    }

    public void start (){
        if (bankomatService.readData().isEmpty())
            bankomatService.recodData(bankomat.getCardsList()); // создание БД с данными
        else
            bankomat.setCardsList(bankomatService.readData()); // выгрузка данных их БД
        
        //Проверка на правильность ввода (символы и длина строки) и существование карты
        CreditCard card = cardService.validateCreditCard (bankomat.getCardsList()); 
        bankomatService.updateBlockedStatus (card); //проверяет, не прошло ли время блокировки
        bankomatService.updateData(bankomat.getCardsList());
        if (!card.isBlocked()) {       
            bankomatService.selectTransaction(card); 
        } 
    } 
}
