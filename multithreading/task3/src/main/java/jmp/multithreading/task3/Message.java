package jmp.multithreading.task3;

public class Message {

  private String topic;
  private String text;

  public Message(String topic, String text) {
    this.topic = topic;
    this.text = text;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

}
