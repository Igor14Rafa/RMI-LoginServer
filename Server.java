import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;


public class Server extends UnicastRemoteObject implements InterfaceLoginServer, Serializable{
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
			System.out.println("Erro na leitura do arquivo de dados dos usuarios: " + e.toString());
		}
	}
	
	private void writeFile(){
		try{
			PrintWriter writer = new PrintWriter(new FileWriter("users_database.txt",false));
			for(User u : users)
				writer.println(u.getUsername() + ";" + u.getPassword());
			writer.flush();
			writer.close();
		}
		catch(IOException e){
			System.out.println("Erro na escrita do arquivo de dados dos usuarios: " + e.toString());
		}
	}

	@Override
	public void adduser(String username, String password)
			throws RemoteException {
		// TODO Auto-generated method stub
		users.add(new User(username, password));
		writeFile();
		System.out.println("Usuario " + username + " adicionado!!!\nNumero de usuarios na base: " + users.size());
	}

	@Override
	public String help() throws RemoteException {
		// TODO Auto-generated method stub
		return "ADDUSER\nLISTUSER\nDELUSER\nPASS\nVERIFY\nHELP\nSAIR\n";
	}

	@Override
	public String listuser() throws RemoteException {
		// TODO Auto-generated method stub
		String aux = "#" + '\t' + "user\n";
		for(int i = 0; i < users.size(); i++)
			aux += i + "\t" + users.elementAt(i).getUsername() + "\n";
		System.out.println("Numero de usuarios cadastrados: " + users.size());
		return aux;
	}

	@Override
	public void deluser(String username, String password) throws RemoteException {
		// TODO Auto-generated method stub
		for(int i = 0; i < this.users.size(); i++)
			if(users.elementAt(i).getUsername().equalsIgnoreCase(username)
			   && users.elementAt(i).getPassword().equalsIgnoreCase(password)){
				users.removeElementAt(i);
			}
		writeFile();
		System.out.println("Usuario " + username + " deletado!!!\nNumero de usuarios na base: " + users.size());
	}

	@Override
	public void pass(String username, String password) throws RemoteException {
		// TODO Auto-generated method stub
		for(int i = 0; i < this.users.size(); i++){
			if(users.elementAt(i).getUsername().equalsIgnoreCase(username)){
				users.elementAt(i).setPassword(password);
			}
		}
		writeFile();
		System.out.println("Senha do usuario " + username + " alterada!!!");
	}

	@Override
	public Boolean verify(String username, String password) throws RemoteException {
		// TODO Auto-generated method stub
		for(int i = 0; i < this.users.size(); i++){
			if(users.elementAt(i).getUsername().equalsIgnoreCase(username) 
			   && users.elementAt(i).getPassword().equalsIgnoreCase(password)){
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean search(String username) throws RemoteException {
		// TODO Auto-generated method stub
		for(int i = 0; i < this.users.size(); i++){
			if(users.elementAt(i).getUsername().equalsIgnoreCase(username)){
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
			System.out.println("Erro na inicializacao do Server: " + e.toString());
		}
	}
}
