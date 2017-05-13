import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;


public interface InterfaceLoginServer extends Remote{
	public void adduser(String username, String password) throws RemoteException;
	public String help() throws RemoteException;
	public Vector<User> listuser() throws RemoteException;
	public void deluser(String username) throws RemoteException;
	public void pass(String username, String password) throws RemoteException;
	public Boolean verify(String username, String password) throws RemoteException;
}
