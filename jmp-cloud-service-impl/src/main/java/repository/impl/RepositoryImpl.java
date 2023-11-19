package repository.impl;

import dto.Subscription;
import dto.User;
import repository.H2Util;
import repository.Repository;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositoryImpl implements Repository {

  private static final String QUERY_GET_ALL_USERS = "SELECT * FROM users";
  private static final String QUERY_GET_SUBSCRIPTION_BY_CARD_NUMBER = "SELECT * FROM subscription WHERE bankcard = ?";
  private static final String QUERY_INSERT_SUBSCRIPTION =
      "INSERT INTO subscription (bankcard,startDate) " + "VALUES (?,?)";
  private static final String QUERY_GET_ALL_QUERY_SUBSCRIPTIONS = "SELECT * FROM subscription";

  public RepositoryImpl() {
    var connection = H2Util.getConnection();
    var dropUserTable = """
        DROP TABLE IF EXISTS users
        """;
    var dropSubscriptionTable = """
        DROP TABLE IF EXISTS subscription
        """;
    var createUserTable = """
        CREATE TABLE users (id INTEGER PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100) NOT NULL, surname VARCHAR(100) NOT NULL, birthday DATE NOT NULL)
        """;
    var createSubscriptionTable = """
        CREATE TABLE subscription (bankcard VARCHAR(100) NOT NULL, startdate DATE NOT NULL)
        """;
    var insertInitUsers = """
        INSERT INTO users (name,surname,birthday)
        VALUES ('Oleg','Ivanich','1994-04-02'),
        ('Oleg','Stepanich','2005-04-02'),
        ('Ivan','Stepanich','2000-02-02'),
        ('Ivan','Ivanich','2010-03-12')
        """;
    try {
      var statement = connection.createStatement();
      statement.execute(dropUserTable);
      statement.execute(dropSubscriptionTable);
      statement.execute(createUserTable);
      statement.execute(insertInitUsers);
      statement.execute(createSubscriptionTable);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public List<User> getAllUsers() {
    var connection = H2Util.getConnection();
    try {
      var statement = connection.createStatement();
      var result = statement.executeQuery(QUERY_GET_ALL_USERS);

      List<User> users = new ArrayList<>();
      while (result.next()) {
        var name = result.getString("name");
        var surname = result.getString("surname");
        var birthday = result.getDate("birthday");

        var user = new User(name, surname, birthday.toLocalDate());
        users.add(user);
      }

      return users;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void addSubscription(Subscription subscription) {
    var connection = H2Util.getConnection();
    try {
      var statement = connection.prepareStatement(QUERY_INSERT_SUBSCRIPTION);
      statement.setString(1, subscription.getBankcard());
      statement.setDate(2, Date.valueOf(subscription.getStartDate()));
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }


  @Override
  public Optional<Subscription> getSubscriptionByCardNumber(String cardNumber) {
    var connection = H2Util.getConnection();
    try {
      var statement = connection.prepareStatement(QUERY_GET_SUBSCRIPTION_BY_CARD_NUMBER);
      statement.setString(1, cardNumber);
      var result = statement.executeQuery();
      if (result.next()) {
        var bankcard = result.getString("bankcard");
        var startDate = result.getDate("startDate");
        return Optional.of(new Subscription(bankcard, startDate.toLocalDate()));
      } else {
        return Optional.empty();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public List<Subscription> getAllSubscription() {
    var connection = H2Util.getConnection();
    try {
      var statement = connection.createStatement();
      var result = statement.executeQuery(QUERY_GET_ALL_QUERY_SUBSCRIPTIONS);

      List<Subscription> subscriptions = new ArrayList<>();
      while (result.next()) {
        var bankcard = result.getString("bankcard");
        var startDate = result.getDate("startDate");

        var subscription = new Subscription(bankcard, startDate.toLocalDate());
        subscriptions.add(subscription);
      }

      return subscriptions;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
