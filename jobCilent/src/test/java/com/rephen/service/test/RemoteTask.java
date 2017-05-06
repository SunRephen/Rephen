package com.rephen.service.test;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteTask extends Remote{
    
    public String test()throws RemoteException;

}
