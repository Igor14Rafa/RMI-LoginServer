import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;


public class Server extends UnicastRemoteObject implements InterfaceLoginServer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Vector<User> users = new Vector<User>();
	
	public Server() throws RemoteException {
		readFile();
	}
	
	private void readFile(){
		try{
			BufferedReader buff = new BufferedReader(new FileReader("users_database.txt"));
			String linha = "";
			while((linha = buff.readLine()) != null){
				String[] user_data = linha.split(";");
				users.add(new User(user_data[0], user_data[1]));
			}
			buff.close();
		}
		catch(IOException e){
			System.out.println("Erro na leitura do arquivo de dados dos usu�rios: " + e.toString());
		}
	}
	
	private void writeFile(){
		try{
			PrintWriter writer = new PrintWriter(new FileWriter("users_database.txt"));
			for(User u : users)
				writer.println(u.getUsername() + ";" + u.getPassword());
			writer.flush();
			writer.close();
		}
		catch(IOException e){
			System.out.println("Erro na escrita do arquivo de dados dos usu�rios: " + e.toString());
		}
	}

	@Override
	public void adduser(String username, String password)
			throws RemoteException {
		// TODO Auto-generated method stub
		users.add(new User(username, password));
		writeFile();
	}

	@Override
	public String help() throws RemoteException {
		// TODO Auto-generated method stub
		return "ADDUSER\nLISTUSER\nDELUSER\nPASS\nVERIFY\nHELP\nSAIR\n";
	}

	@Override
	public Vector<User> listuser() throws RemoteException {
		// TODO Auto-generated method stub
//		for(User u : users)
//			System.out.println(u.getUsername());
		return this.users;
	}

	@Override
	public void deluser(String username, String password) throws RemoteException {
		// TODO Auto-generated method stub
		for(int i = 0; i < this.users.size(); i++)
			if(users.elementAt(i).getUsername() == username){
				users.remove(i);
				break;
			}
		writeFile();
	}

	@Override
	public void pass(String username, String password) throws RemoteException {
		// TODO Auto-generated method stub
		for(int i = 0; i < this.users.size(); i++){
			if(users.elementAt(i).getUsername() == username){
				users.elementAt(i).setPassword(password);
				break;
			}
		}
		writeFile();
	}

	@Override
	public Boolean verify(String username, String password) throws RemoteException {
		// TODO Auto-generated method stub
		for(int i = 0; i < this.users.size(); i++){
			if(users.elementAt(i).getUsername() == username
			   && users.elementAt(i).getPassword() == password){
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		try{
			System.out.println("Iniciando o servidor de login...");
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind("LoginServer_1", new Server());
		}
		catch(Exception e){
			System.out.println("Erro na inicializa��o do Server: " + e.toString());
		}
	}
}
