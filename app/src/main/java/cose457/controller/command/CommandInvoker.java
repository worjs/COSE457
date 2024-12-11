package cose457.controller.command;

import java.util.Stack;

/*
 * CommandInvoker를 통해서 Command들을 "관리"할 수 있다.
 * Stack을 이용해서 history를 관리하고, Undo할 수 있기도 함.
 * 커맨드들을 추상화하고, 실행/Undo는 Invoker에 전부 다 던지는 느낌
 */
public class CommandInvoker {

  private static CommandInvoker instance;
  private Stack<Command> history = new Stack<>();

  public static CommandInvoker getInstance() {
    if (instance == null) {
      instance = new CommandInvoker();
    }
    return instance;
  }

  public void executeCommand(Command command) {
    command.execute();
    history.push(command);
  }

  public void undo() {
    if (!history.isEmpty()) {
      Command command = history.pop();
      if (command instanceof Undoable) {
        ((Undoable) command).undo();
      }
    }
  }

}