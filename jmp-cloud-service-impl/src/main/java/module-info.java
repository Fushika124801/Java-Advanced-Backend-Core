module jmp.cloud.service.impl {
  requires jmp.dto;
  requires transitive jmp.service.api;
  exports service.impl;
  requires java.sql;
  provides service.Service with service.impl.ServiceImpl;
}
