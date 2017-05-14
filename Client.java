import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
	InterfaceLoginServer lsi;
	Scanner in = new Scanner(System.in);
	
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
		try{
			System.out.print("Digite o nome de usuario e a senha, separados por ';'. Ex: foo;123: ");
			String[] data = in.next().split(";");
			if(lsi.verify(data[0], data[1])){
				System.out.println("Usuario ja cadastrado!!!");
				return;
			}
			if(lsi.search(data[0])){
				System.out.println("Nome de usuario ja esta sendo utilizado!!!");
				return;
			}
			lsi.adduser(data[0], data[1]);
			System.out.println("Usuario " + data[0] + " cadastrado!!!");
		}
		catch(Exception e){
			System.out.println("Erro ao adicionar usuário na base de dados: ");
			e.printStackTrace();
		}
	}
	
	public void listuser() throws RemoteException {
		System.out.println("Usuarios cadastrados: ");
		System.out.println(lsi.listuser());
		return;
	}
	
	public void deluser() throws RemoteException {
		System.out.print("Digite o nome e a senha do usuario que deve ser excluido, separados por ';'. Ex: foo;123:  ");
		String[] data = in.next().split(";");
		if(!lsi.verify(data[0], data[1])){
			System.out.println("Usuario nao existe!!!");
			return;
		}
		lsi.deluser(data[0], data[1]);
	}
	
	public void pass() throws RemoteException {
		System.out.print("Digite o nome de usuario e a senha, separados por ';'. Ex: foo;123: ");
		String[] data = in.next().split(";");
		if(!lsi.search(data[0])){
			System.out.println("Usuario nao encontrado na base de dados!!!");
			return;
		}
		lsi.pass(data[0], data[1]);
		System.out.println("Senha do usuario " + data[0] + " alterada!!");
	}
	
	public void help() throws RemoteException {
		System.out.println("Comandos validos(Case-sensitive): ");
		System.out.println(lsi.help());
		return;
	}
	
	public void verify() throws RemoteException {
		System.out.print("Digite o nome de usuario e a senha, separados por ';'. Ex: foo;123: ");
		String[] data = in.next().split(";");
		if(lsi.verify(data[0], data[1]))
			System.out.println("Combinacao de usuario e senha eh valida");
		else
			System.out.println("Usuario e senha nao encontrados");
	}
	 
	public void menu() throws RemoteException {
		String opt = "";
		
		while(!opt.contains("SAIR")){
			System.out.print(">>> ");
			opt = in.next();
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
				case "SAIR":
					in.close();
					System.exit(0);
				default: 
					System.out.println("Comando invalido. Tente novamente");
					break;
			}
		}
	}
	
	public static void main(String[] args) {
		Client c = new Client();
		try{
			c.menu();
		}
		catch(Exception e){
			System.out.println("Erro ao executar o cliente de login: ");
			e.printStackTrace();
		}
	}
}
