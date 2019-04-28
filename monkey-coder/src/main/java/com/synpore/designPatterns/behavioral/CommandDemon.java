package com.synpore.designPatterns.behavioral;

//Encapsulate a request as an object,
// thereby letting you parameterize clients with different requests, queue or log requests, and support undoable operations.
public class CommandDemon {
    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        Command command = new ConcreteCommand(receiver);

        Invoker invoker = new Invoker(command);
        invoker.doInvokerAction();
    }


}

interface Command {
    public void execute();
}

class ConcreteCommand implements Command {
    // 持有接收者的引用
    private Receiver receiver;

    public ConcreteCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute()//将命令的执行委托给接收者
    {
        receiver.doAction();
    }

}

class Receiver {
    //接收者真正执行命令
    public void doAction() {
        System.out.println("doAction");
    }
}

class Invoker {
    //持有命令对象的引用，将动作委托给命令对象执行
    private Command command;

    public Invoker(Command command) {
        this.command = command;
    }

    public void doInvokerAction() {
        command.execute();
    }
}
