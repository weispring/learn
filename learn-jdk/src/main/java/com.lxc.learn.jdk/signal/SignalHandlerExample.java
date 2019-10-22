package com.lxc.learn.jdk.signal;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * @Auther: lixianchun
 * @Date: 2018/10/10 10:45
 * @Description:
 */
@Slf4j
public class SignalHandlerExample implements SignalHandler {

    private boolean Running = true;

    public SignalHandlerExample() {
        this.setupHandler();
    }

    private void setupHandler() {
        this.handleSignal("INT");
        this.handleSignal("TERM");
        //this.handleSignal("HUP");
    }

    public void handleSignal(final String signalName) {
        try {
            Signal.handle(new Signal(signalName), this);
        } catch (IllegalArgumentException e) {
            // 可能这个信号,并不支持这个平台或JVM作为目前配置
            e.printStackTrace();
        }
    }

    /**
     * 捕获信号
     * @param signal The {@link Signal} that we received
     **/
    @Override
    public synchronized void handle(Signal signal) {
        // we are only interested in catching the "TERM" signal
        if ( signal.getName().equals("TERM") ) {
            log.info(" TERM received " + signal.getName() );
            log.info("threadName" + Thread.currentThread().getName());
            System.exit(-1);
        } else if ( signal.getName().equals("INT") ||signal.getName().equals("HUP") ) {
            setRunningStatus( false );
            notifyAll(); //TODO - clarify if notify OR notifyall
        }
        log.info( signal.getName() + " " + signal.getNumber() );
        log.info( getRunningStatus()+"" );
    }

    public void setRunningStatus( boolean Status ) {
        this.Running = Status;
    }

    public boolean getRunningStatus() {
        return Running;
    }

    /**
     * General "clean up" method which is called when we receive a TERM
     signal
     * This will likely be superseeded by specific cleanup code
     *
     **/
    public boolean cleanUp() {
        // TODO - we need to decide what we are going to do one we trap a
        //	 TERMinate signal.
        // We need to complete all our threads nicely.
        log.info("Cleaning up!");
        return true;
    }

    public synchronized void myWait() {
        try{
            wait();
        }catch (InterruptedException e){
            log.info( "myWait WOKEN UP" + e );
        }
    }

    public static void main(final String[] args) throws Exception {

        //PropertyConfigurator.configure("./src\\main\\resources\\config\\config-order.yml");

        SignalHandlerExample mySignalHandler = new SignalHandlerExample();

        while ( mySignalHandler.getRunningStatus() ) {
            log.info("waiting");
            mySignalHandler.myWait();
            log.info("woken up");
        }


    }
}

