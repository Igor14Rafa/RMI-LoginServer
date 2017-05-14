import java.rmi.Remote;
import java.rmi.RemoteException;


public interface InterfaceLoginServer extends Remote{
	public void adduser(String username, String password) throws RemoteException;
	public String help() throws RemoteException;
	public String listuser() throws RemoteException;
	public void deluser(String username, String password) throws RemoteException;
	public void pass(String username, String password) throws RemoteException;
	public Boolean verify(String username, String password) throws RemoteException;
	public Boolean search(String username) throws RemoteException;
}
