package jmp.reactive.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document
public class User {

  @Id
  private String id;
  private String login;

  public User(String login) {
    this.login = login;
  }
}
