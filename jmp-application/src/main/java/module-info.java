import bank.Bank;
import service.Service;

module jmp.application {
  requires jmp.bank.impl;
  requires jmp.cloud.service.impl;
  requires jmp.dto;
  uses Service;
  uses Bank;
}
