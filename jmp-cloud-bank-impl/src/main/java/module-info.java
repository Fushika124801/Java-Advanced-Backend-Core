module jmp.bank.impl {
  requires jmp.dto;
  requires transitive jmp.bank.api;
  exports bank.impl;
  provides bank.Bank with bank.impl.BankImpl;
}
