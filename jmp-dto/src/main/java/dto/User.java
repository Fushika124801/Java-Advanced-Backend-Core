package dto;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class User {

  private final String name;
  private final String surname;
  private final LocalDate birthday;

  public User(String name, String surname, LocalDate birthday) {
    this.name = name;
    this.surname = surname;
    this.birthday = birthday;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public long getAge() {
    return ChronoUnit.YEARS.between(getBirthday(), LocalDate.now());
  }

  @Override
  public String toString() {
    return "User{" +
        "name='" + name + '\'' +
        ", surname='" + surname + '\'' +
        ", birthday=" + birthday +
        '}';
  }
}
