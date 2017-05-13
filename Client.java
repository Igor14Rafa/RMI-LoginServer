import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.Vector;


public class Client {
	InterfaceLoginServer lsi;
	
	public Client(){
		System.out.println("Iniciando o cliente...");
		
		try{
			Registry registry = LocateRegistry.getRegistry();
			lsi = (InterfaceLoginServer) registry.lookup("LoginServer_1");
		}
		catch(Exception e){
			System.out.println("Erro ao tentar se conectar com o servidor de login: " + e.toString());
		}
	}
	
	public void adduser() throws RemoteException {
		Scanner input = new Scanner(System.in);
		System.out.println("Digite o nome de usu�rio e a senha, separados por ; .Ex: 'foo;123'");
		String[] data = input.next().split(";");
		if(lsi.verify(data[0], data[1])){
			System.out.println("Usu�rio j� cadastrado!!!");
			input.close();
			return;
		}
		lsi.adduser(data[0], data[1]);
		input.close();
	}
	
	public void listuser() throws RemoteException {
		Vector<User> aux_list = lsi.listuser();
		for(User u : aux_list)
			System.out.println(u.getUsername());
	}
	
	public void deluser() throws RemoteException {
		Scanner input = new Scanner(System.in);
		System.out.println("Digite o nome de usu�rio que deve ser exclu�do: ");
		String[] data = input.next().split(";");
		if(lsi.verify(data[0], data[1])){
			System.out.println("Usu�rio n�o existe!!!");
			input.close();
			return;			
		}
		lsi.deluser(data[0], data[1]);
		input.close();		
	}
	
	public void pass() throws RemoteException {
		Scanner input = new Scanner(System.in);
		System.out.println("Digite o nome de usu�rio e a senha, separados por ; .Ex: 'foo;123'");
		String[] data = input.next().split(";");
		if(lsi.verify(data[0], data[1])){
			System.out.println("Usu�rio n�o existe!!!");
			input.close();
			return;			
		}
		lsi.pass(data[0], data[1]);
		input.close();				
	}
	
	public void help() throws RemoteException {
		System.out.println("Comandos v�lidos(Case-sensitive): ");
		System.out.println(lsi.help());
	}
	
	public void verify() throws RemoteException {
		Scanner input = new Scanner(System.in);
		System.out.println("Digite o nome de usu�rio e a senha, separados por ; .Ex: 'foo;123'");
		String[] data = input.next().split(";");
		if(lsi.verify(data[0], data[1]))
			System.out.println("Combina��o de usu�rio e senha � v�lida");
		else
			System.out.println("Usu�rio e senha n�o encontrados");
		
		input.close();				
	}
	 
	
	public void menu() throws RemoteException{
		Scanner input = new Scanner(System.in);
		String opt;
		
		System.out.println(">>> ");
		do{
			opt = input.next();
			switch(opt){
				case "ADDUSER": 
					this.adduser();
					break;
				case "LISTUSER": 
					this.listuser();
					break;
				case "HELP": 
					this.help();
					break;
				case "VERIFY": 
					this.verify();
					break;
				case "DELUSER": 
					this.deluser();
					break;
				case "PASS": 
					this.pass();
					break;
				default: 
					System.out.println("Comando inv�lido");
					break;
			}
		}while(opt != "SAIR");
		input.close();
	}
	
	public static void main(String[] args) {
		Client c = new Client();
		try{
			c.menu();
		}
		catch(Exception e){
			System.out.println("Erro ao executar o cliente de login: " + e.toString());
		}
	}
}
