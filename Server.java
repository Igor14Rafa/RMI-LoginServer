import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Vector;


public class Server implements InterfaceLoginServer{
	Vector<User> users = new Vector<User>();
	
	public Server(){
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
			System.out.println("Erro na leitura do arquivo de dados dos usuários: " + e.toString());
		}
	}

	@Override
	public void adduser(String username, String password)
			throws RemoteException {
		// TODO Auto-generated method stub
		users.add(new User(username, password));
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
	public void deluser(String username) throws RemoteException {
		// TODO Auto-generated method stub
		for(int i = 0; i < this.users.size(); i++)
			if(users.elementAt(i).getUsername() == username){
				users.remove(i);
				break;
			}		
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
			System.out.println("Erro na inicialização do Server: " + e.toString());
		}
	}
}
