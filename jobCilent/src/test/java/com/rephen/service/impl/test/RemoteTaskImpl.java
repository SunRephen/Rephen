package com.rephen.service.impl.test;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import com.rephen.service.test.RemoteTask;

public class RemoteTaskImpl extends UnicastRemoteObject implements RemoteTask {

    /**
     * 
     */
    private static final long serialVersionUID = -1666246831074012759L;

    protected RemoteTaskImpl() throws RemoteException {
    }

    @Override
    public String test() {
        return "fdasfdasfdsafas";
    }
    
    public static void main(String[] args) throws Exception {
        int port = 1099;
        String url = "rmi://localhost:1099/job.task1";
        LocateRegistry.createRegistry(port);
        Naming.rebind(url, new RemoteTaskImpl());
    }

}
